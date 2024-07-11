package com.project.oriobook.modules.auth.services;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.exceptions.AuthException;
import com.project.oriobook.common.helpers.JwtTokenHelper;
import com.project.oriobook.modules.auth.dto.LoginDTO;
import com.project.oriobook.modules.auth.dto.SignUpDTO;
import com.project.oriobook.modules.auth.responses.LoginResponse;
import com.project.oriobook.modules.user.entities.User;
import com.project.oriobook.modules.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

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

        return userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginDTO loginDTO) throws Exception{
        // Check user exists
        User existingUser = userRepository.findByEmail(loginDTO.getEmail())
                .orElseThrow(AuthException.UnAuthorize::new);

        // Check password
        if(!passwordEncoder.matches(loginDTO.getPassword(), existingUser.getPassword())){
            throw new AuthException.UnAuthorize();
        }

        // Authenticate with Spring Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getEmail(), loginDTO.getPassword()
        );
        authenticationManager.authenticate(authenticationToken);

        // Response for client
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(existingUser.getId());
        loginResponse.setAccessToken(jwtTokenHelper.generateToken(existingUser, CommonConst.ACCESS));
        loginResponse.setRefreshToken(jwtTokenHelper.generateToken(existingUser, CommonConst.REFRESH));

        LocalDateTime expiredTime = jwtTokenHelper.getExpirationFromToken(loginResponse.getAccessToken());
        System.out.println("expiredTime: " + expiredTime);
        loginResponse.setExpiredAt(expiredTime);

        return loginResponse;
    }
}
