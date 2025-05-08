package io.aiven.spring.mysql.demo.dto;

import org.springframework.web.multipart.MultipartFile;

public class PostDTO {
    private String title;
    private String description;
    private MultipartFile image;

    public PostDTO() {}
    public PostDTO(String title, String description, MultipartFile image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public MultipartFile getImage() { return image; }
    public void setImage(MultipartFile image) { this.image = image; }
    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}