package com.project.oriobook.modules.user;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.constants.RoleConst;
import com.project.oriobook.common.exceptions.ValidationException;
import com.project.oriobook.modules.elastic.services.ElasticService;
import com.project.oriobook.modules.user.dto.UpdateUserProfileDTO;
import com.project.oriobook.modules.user.entities.User;
import com.project.oriobook.modules.user.responses.UserResponse;
import com.project.oriobook.modules.user.services.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "users")
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    private final ElasticService elasticService;
    private final ModelMapper modelMapper;

    @GetMapping("/profile")
    @Operation(summary = RoleConst.OP_ADMIN_USER)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN_USER)
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getMyProfile(@AuthenticationPrincipal User userDetails) throws Exception {
        User user = userService.getUserById(userDetails.getId());
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);

        return userResponse;
    }

    @PutMapping("/profile")
    @Operation(summary = RoleConst.OP_ADMIN_USER)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN_USER)
    @ResponseStatus(HttpStatus.OK)
    public Boolean updateProduct(@Valid @RequestBody UpdateUserProfileDTO dto,  BindingResult result,
                                 @AuthenticationPrincipal User userDetails)
            throws Exception {
        if(result.hasErrors()) {
            throw new ValidationException(result);
        }

        User updatedUser = userService.updateUserProfile(userDetails.getId(), dto);
        return updatedUser != null;
    }

    @DeleteMapping("/profile")
    @Operation(summary = RoleConst.OP_ADMIN_USER)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN_USER)
    @ResponseStatus(HttpStatus.OK)
    public Boolean deleteProduct(@AuthenticationPrincipal User userDetails) throws Exception {
        userService.deleteUser(userDetails.getId());
        return true;
    }
}
