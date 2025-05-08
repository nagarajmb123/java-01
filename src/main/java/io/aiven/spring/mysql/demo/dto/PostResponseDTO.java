package io.aiven.spring.mysql.demo.dto;

import io.aiven.spring.mysql.demo.model.Post;

public class PostResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String username;  // Add user information you want to include
    private Long userId;

    // Constructor
    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.imageUrl = post.getImageUrl();
        if (post.getUser() != null) {
            this.username = post.getUser().getUsername();
            this.userId = post.getUser().getId();
        }
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getUsername() { return username; }
    public Long getUserId() { return userId; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setUsername(String username) { this.username = username; }
    public void setUserId(Long userId) { this.userId = userId; }
}