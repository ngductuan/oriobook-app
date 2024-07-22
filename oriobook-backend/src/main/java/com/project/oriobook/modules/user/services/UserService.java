package com.project.oriobook.modules.user.services;

import com.project.oriobook.common.exceptions.UserException;
import com.project.oriobook.core.pagination.base.PageResponse;
import com.project.oriobook.modules.product.dto.FindAllProductQueryDTO;
import com.project.oriobook.modules.product.entities.Product;
import com.project.oriobook.modules.product.responses.ProductResponse;
import com.project.oriobook.modules.user.dto.UpdateUserProfileDTO;
import com.project.oriobook.modules.user.entities.User;
import com.project.oriobook.modules.user.repository.UserRepository;
import com.project.oriobook.modules.user.responses.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public User getUserById(String id) throws Exception{
        User user = userRepository.findById(id)
                .orElseThrow(UserException.NotFound::new);
        return user;
    }

    @Override
    public User updateUserProfile(String userId, UpdateUserProfileDTO dto) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(UserException.NotFound::new);

        modelMapper.typeMap(UpdateUserProfileDTO.class, User.class);
        modelMapper.map(dto, user);

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(String userId) throws Exception {
        User user = userRepository.findById(userId)
                .orElseThrow(UserException.NotFound::new);
        userRepository.delete(user);
    }
}
