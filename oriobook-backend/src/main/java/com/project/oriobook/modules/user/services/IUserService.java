package com.project.oriobook.modules.user.services;

import com.project.oriobook.modules.user.dto.UpdateUserProfileDTO;
import com.project.oriobook.modules.user.entities.User;

public interface IUserService {
    User getUserById(String id) throws Exception;
    User updateUserProfile(String userId, UpdateUserProfileDTO dto) throws Exception;
    void deleteUser(String userId) throws Exception;

}
