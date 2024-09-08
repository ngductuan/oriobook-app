package com.project.oriobook.modules.upload_file.services;

import com.project.oriobook.modules.upload_file.entities.CloudinaryEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {
    public CloudinaryEntity upload(MultipartFile file) throws Exception;
}
