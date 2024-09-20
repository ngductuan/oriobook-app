package com.project.oriobook.modules.upload_file.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.modules.upload_file.entities.CloudinaryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UploadFileService implements IUploadFileService {
    private final Cloudinary cloudinary;

    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public CloudinaryEntity upload(MultipartFile file) throws Exception {
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
            "folder", CommonConst.CLOUDINARY_PRODUCTS_FOLDER
        ));

        CloudinaryEntity cloudinaryObject = modelMapper.map(uploadResult, CloudinaryEntity.class);
        cloudinaryObject.setPublicId((String) uploadResult.get("public_id"));
        cloudinaryObject.setSecureUrl((String) uploadResult.get("secure_url"));
        cloudinaryObject.setResourceType((String) uploadResult.get("resource_type"));

        return cloudinaryObject;
    }
}
