package com.project.oriobook.modules.user.dto;

import lombok.Data;

@Data
public class UpdateUserProfileDTO {
    private String firstName;

    private String lastName;

    private String phone;

    private String address;

    private String image;
}
