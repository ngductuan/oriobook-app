package com.project.oriobook.modules.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.entity.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity implements UserDetails {
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

    @Enumerated(EnumType.STRING)
    CommonEnum.RoleEnum role = CommonEnum.RoleEnum.USER;

    @Column(name = "google_account_id")
    String googleAccountId;

    @Column(name = "facebook_account_id")
    String facebookAccountId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority("ROLE_"+ getRole().toString().toUpperCase()));
        //authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return authorityList;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
