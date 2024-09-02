package com.project.oriobook.modules.user.services;

import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.user.dto.UpdateUserProfileDTO;
import com.project.oriobook.modules.user.entities.User;
import org.springframework.data.domain.Page;

public interface IUserService {
    User getUserById(String id) throws Exception;
    Page<User> getAllUsersToSync();
    User updateUserProfile(String userId, UpdateUserProfileDTO dto) throws Exception;
    void deleteUser(String userId) throws Exception;

}
