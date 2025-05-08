package io.aiven.spring.mysql.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    private String username;
    private String email;
    private String createdBy;

    public User() {}

    public User(String username, String email, String createdBy) {
        this.username = username;
        this.email = email;
        this.createdBy = createdBy;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getCreatedBy() { return createdBy; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
}

