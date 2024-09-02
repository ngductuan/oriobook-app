package com.project.oriobook.modules.user.dto;

import lombok.Data;

@Data
public class UpdateUserProfileDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String image;
    private String role;
    private String googleAccountID;
    private String facebookAccountID;
    private long createdAt;
    private long updatedAt;
}
