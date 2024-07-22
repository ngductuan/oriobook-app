package com.project.oriobook.modules.user.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdatePasswordDTO {
    @NotNull
    @NotBlank
    @Min(value = 6, message = "Password must be at least 6 characters")
    private String oldPassword;

    @NotNull
    @NotBlank
    @Min(value = 6, message = "Password must be at least 6 characters")
    private String newPassword;

    @NotNull
    @NotBlank
    @Min(value = 6, message = "Password must be at least 6 characters")
    private String confirmPassword;
}
