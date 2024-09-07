package com.project.oriobook.modules.upload_file;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.constants.RoleConst;
import com.project.oriobook.common.exceptions.UploadFileException;
import com.project.oriobook.common.utils.CommonUtil;
import com.project.oriobook.modules.upload_file.entities.CloudinaryEntity;
import com.project.oriobook.modules.upload_file.services.UploadFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "upload-file")
@RequestMapping("${api.prefix}/upload")
@RequiredArgsConstructor
public class UploadFileController {
    private final UploadFileService uploadFileService;

    @PostMapping(value = "/image", consumes = "multipart/form-data")
    @Operation(summary = RoleConst.OP_ADMIN_USER)
    @SecurityRequirement(name = CommonConst.BEARER_KEY)
    @PreAuthorize(RoleConst.ROLE_ADMIN_USER)
    @ResponseStatus(HttpStatus.OK)
    public CloudinaryEntity uploadImage(@RequestParam("image") MultipartFile file) throws Exception{
        if(file == null){
            throw new UploadFileException.NotEmpty();
        }

        String fileExtension = CommonUtil.getFileExtension(file);

        if(fileExtension.isEmpty() || !CommonConst.ALLOWED_IMAGE_EXTENSIONS.contains(fileExtension)){
            throw new UploadFileException.OnlyUploadImage();
        }

        CloudinaryEntity uploadResult = uploadFileService.upload(file);
        return uploadResult;
    }
}
