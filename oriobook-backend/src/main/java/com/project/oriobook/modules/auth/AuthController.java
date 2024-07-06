package com.project.oriobook.modules.auth;

import com.project.oriobook.modules.auth.dto.LoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // @PostMapping("/login")
    // public String login(@RequestBody LoginDTO loginDTO) {
    //     return authService.login(loginDTO);
    // }

    // @PostMapping("/sign-up")
    // public String register(@RequestBody RegisterDTO registerDTO) {
    //     return authService.register(registerDTO);
    // }
}
