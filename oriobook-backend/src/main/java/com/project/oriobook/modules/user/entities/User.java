package com.project.oriobook.modules.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.oriobook.core.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @NotNull
    @Column(unique = true)
    String email;

    @JsonIgnore
    String password;

    String phone;

    String address;

    String image;

    @Column(name = "is_admin")
    Boolean isAdmin;

    @Column(name = "google_account_id")
    String googleAccountId;

    @Column(name = "facebook_account_id")
    String facebookAccountId;

}
