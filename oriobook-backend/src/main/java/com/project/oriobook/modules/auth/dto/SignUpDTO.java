package com.project.oriobook.modules.auth.dto;

import com.project.oriobook.common.annotations.EmailValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpDTO {
    @NotNull
    @NotBlank
    String firstName;

    @NotNull
    @NotBlank
    String lastName;

    @NotNull
    @EmailValid
    String email;

    @NotNull
    @NotBlank
    String password;

    @NotNull
    @NotBlank
    String phone;

    @NotNull
    @NotBlank
    String address;
}
