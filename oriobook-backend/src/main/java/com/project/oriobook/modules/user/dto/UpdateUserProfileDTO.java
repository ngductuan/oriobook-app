package com.project.oriobook.modules.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserProfileDTO {
    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    @NotNull
    @NotBlank
    private String phone;

    @NotNull
    @NotBlank
    private String address;

    @NotNull
    @NotBlank
    private String image;
}
