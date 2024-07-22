package com.project.oriobook.modules.user.responses;

import lombok.Data;

@Data
public class UserResponse {
    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String address;

    private String image;
}
