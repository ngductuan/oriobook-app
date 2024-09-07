package com.project.oriobook.modules.auth.services;

import com.project.oriobook.modules.auth.dto.LoginDTO;
import com.project.oriobook.modules.auth.dto.SignUpDTO;
import com.project.oriobook.modules.auth.responses.LoginResponse;
import com.project.oriobook.modules.user.entities.User;

public interface IAuthService {
    User signUp(SignUpDTO userDTO) throws Exception;
    LoginResponse login(LoginDTO loginDTO) throws Exception;
    // void logout(String token);
}
