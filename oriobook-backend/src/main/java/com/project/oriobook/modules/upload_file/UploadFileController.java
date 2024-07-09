package com.project.oriobook.modules.upload_file;

import com.project.oriobook.modules.upload_file.entities.CloudinaryEntity;
import com.project.oriobook.modules.upload_file.services.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/upload")
@RequiredArgsConstructor
public class UploadFileController {
    private final UploadFileService uploadFileService;

    @PostMapping("/image")
    @ResponseStatus(HttpStatus.OK)
    public CloudinaryEntity uploadImage(@RequestParam("file") MultipartFile file) throws Exception{
        CloudinaryEntity uploadResult = uploadFileService.upload(file);
        return uploadResult;
    }
}
