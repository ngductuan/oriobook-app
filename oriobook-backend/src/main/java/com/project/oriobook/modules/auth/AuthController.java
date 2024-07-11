package com.project.oriobook.modules.auth;

import com.project.oriobook.modules.auth.dto.LoginDTO;
import com.project.oriobook.modules.auth.dto.SignUpDTO;
import com.project.oriobook.modules.auth.responses.LoginResponse;
import com.project.oriobook.modules.auth.services.AuthService;
import com.project.oriobook.modules.user.entities.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "auth")
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean register(@RequestBody SignUpDTO signUpDTO) {
        User user = authService.signUp(signUpDTO);
        return user != null;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse login(@RequestBody LoginDTO loginDTO) throws Exception {
        return authService.login(loginDTO);
    }
}
