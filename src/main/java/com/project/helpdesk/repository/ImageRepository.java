package com.project.helpdesk.repository;

import com.project.helpdesk.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO m_image(id, name, path, size, content_type) VALUES (:id, :name, :path, :size, :contentType)", nativeQuery = true)
    void createImage(String id, String name, String path, Long size, String contentType);

    @Query(value = "SELECT * FROM m_image WHERE id = :id", nativeQuery = true)
    Optional<Image> findImageById(String id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM m_image WHERE id = :id", nativeQuery = true)
    void deleteImageById(String id);
}

