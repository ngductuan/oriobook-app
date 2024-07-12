package com.project.oriobook.modules.auth.dto;

import com.project.oriobook.common.annotations.EmailValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
    @NotNull
    @EmailValid
    private String email;

    @NotNull
    @NotBlank
    private String password;
}
