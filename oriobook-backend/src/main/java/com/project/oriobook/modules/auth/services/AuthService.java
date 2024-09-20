package com.project.oriobook.modules.auth.services;

import com.project.oriobook.common.components.helpers.JwtTokenHelper;
import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.exceptions.AuthException;
import com.project.oriobook.modules.auth.dto.LoginDTO;
import com.project.oriobook.modules.auth.dto.SignUpDTO;
import com.project.oriobook.modules.auth.responses.LoginResponse;
import com.project.oriobook.modules.user.entities.User;
import com.project.oriobook.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final JwtTokenHelper jwtTokenHelper;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public User signUp(SignUpDTO userDTO) throws Exception{
        // Check user exists
        Optional<User> existingUser = userRepository.findByEmail(userDTO.getEmail());

        if(existingUser.isPresent()){
            throw new AuthException.EmailExist();
        }

        User user = new User();

        modelMapper.typeMap(SignUpDTO.class, User.class);
        modelMapper.map(userDTO, user);

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encodedPassword);

        // Create cart for user

        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginDTO loginDTO) throws Exception{
        // Check user exists
        User existingUser = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(AuthException.WrongCredentials::new);

        // Check password
        if(!passwordEncoder.matches(loginDTO.getPassword(), existingUser.getPassword())){
            log.warn("User {} entered wrong password", existingUser.getEmail());
            throw new AuthException.WrongCredentials();
        }

        // Create authentication account with Spring Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(), loginDTO.getPassword()
        );
        authenticationManager.authenticate(authenticationToken);

        // Response for client
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAccessToken(jwtTokenHelper.generateToken(existingUser, CommonConst.ACCESS));
        loginResponse.setRefreshToken(jwtTokenHelper.generateToken(existingUser, CommonConst.REFRESH));

        LocalDateTime expiredTime = jwtTokenHelper.getExpirationFromToken(loginResponse.getAccessToken());
        loginResponse.setExpiredAt(expiredTime);

        log.info("User {} logged in", existingUser.getEmail());

        return loginResponse;
    }
}
