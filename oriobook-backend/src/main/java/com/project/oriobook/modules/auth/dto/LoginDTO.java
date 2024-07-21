package com.project.oriobook.modules.auth.dto;

import com.project.oriobook.common.annotations.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginDTO {
    @NotNull
    @ValidEmail
    private String email;

    @NotNull
    @NotBlank
    private String password;
}
