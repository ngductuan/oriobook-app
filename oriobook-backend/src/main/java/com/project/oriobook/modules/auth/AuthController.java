package com.project.oriobook.modules.auth;

import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.modules.auth.dto.LoginDTO;
import com.project.oriobook.modules.auth.dto.SignUpDTO;
import com.project.oriobook.modules.auth.responses.LoginResponse;
import com.project.oriobook.modules.auth.services.AuthService;
import com.project.oriobook.modules.auth.services.IAuthService;
import com.project.oriobook.modules.user.entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "auth")
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean register(@Valid @RequestBody SignUpDTO signUpDTO , BindingResult result) throws Exception{
        if(result.hasErrors()){
            throw new ValidationException(result);
        }

        User user = authService.signUp(signUpDTO);
        return user != null;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@Valid @RequestBody LoginDTO loginDTO, BindingResult result) throws Exception {
        if(result.hasErrors()){
            throw new ValidationException(result);
        }

        return authService.login(loginDTO);
    }
}
