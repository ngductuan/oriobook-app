package com.project.oriobook.modules.upload_file;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.exceptions.UploadFileException;
import com.project.oriobook.modules.upload_file.entities.CloudinaryEntity;
import com.project.oriobook.modules.upload_file.services.UploadFileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@Tag(name = "upload-file")
@RequestMapping("${api.prefix}/upload")
@RequiredArgsConstructor
public class UploadFileController {
    private final UploadFileService uploadFileService;

    @PostMapping(value = "/image", consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.OK)
    public CloudinaryEntity uploadImage(@RequestParam("file") MultipartFile file) throws Exception{
        if(file == null){
            throw new UploadFileException.NotEmpty();
        }

        if(CommonConst.ALLOWED_IMAGE_EXTENSIONS.contains(file.getContentType())){
            throw new UploadFileException.OnlyUploadImage();
        }

        CloudinaryEntity uploadResult = uploadFileService.upload(file);
        return uploadResult;
    }
}
