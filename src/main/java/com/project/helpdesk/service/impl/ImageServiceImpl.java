package com.project.helpdesk.service.impl;


import com.project.helpdesk.constant.ResponseMessage;
import com.project.helpdesk.entity.Image;
import com.project.helpdesk.repository.ImageRepository;
import com.project.helpdesk.service.ImageService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    private final Path directoryPath;
    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(
            @Value("${it_helpdesk.multipart.path-location}") String directoryPath,
            ImageRepository imageRepository) {
        this.directoryPath = Paths.get(directoryPath);
        this.imageRepository = imageRepository;
    }

    @PostConstruct
    public void initDirectory() {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }


    @Override
    public Image create(MultipartFile multipartFile, String imageId) {
        try {
            if (!List.of("image/jpeg", "image/png", "image/jpg").contains(multipartFile.getContentType()))
                throw new ConstraintViolationException(ResponseMessage.ERROR_INVALID_CONTENT_TYPE, null);

//            UUID generatedId = UUID.randomUUID();
            String uniqueFileName = System.currentTimeMillis() + "-" + multipartFile.getOriginalFilename();

            Path filePath = directoryPath.resolve(uniqueFileName);
            Files.copy(multipartFile.getInputStream(), filePath);

            Image image = Image.builder()
                    .name(uniqueFileName)
                    .contentType(multipartFile.getContentType())
                    .size(multipartFile.getSize())
                    .path(filePath.toString())
                    .build();

//            imageRepository.saveAndFlush(image);
            imageRepository.createImage(imageId, uniqueFileName, filePath.toString(), multipartFile.getSize(), multipartFile.getContentType());

            return image;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Resource getById(String id) {
        try {
            Image image = imageRepository.findImageById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
            Path filePath = Paths.get(image.getPath());

            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @Override
    public void deleteById(String id) {
        try {
            Image image = imageRepository.findImageById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND));
            Path filePath = Paths.get(image.getPath());

            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);

            Files.delete(filePath);
            imageRepository.deleteImageById(image.getId());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
