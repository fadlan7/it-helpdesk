package com.project.helpdesk.service;

import com.project.helpdesk.entity.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image create(MultipartFile multipartFile, String imageId);
    Resource getById(String id);
    void deleteById(String id);
}
