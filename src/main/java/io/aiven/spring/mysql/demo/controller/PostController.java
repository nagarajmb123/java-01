package io.aiven.spring.mysql.demo.controller;

import io.aiven.spring.mysql.demo.dto.PostDTO;
import io.aiven.spring.mysql.demo.dto.PostResponseDTO;
import io.aiven.spring.mysql.demo.exception.ApiResponse;
import io.aiven.spring.mysql.demo.model.Post;
import io.aiven.spring.mysql.demo.service.PostService;
import io.aiven.spring.mysql.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE)
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponseDTO>>> getPosts() {
        List<PostResponseDTO> posts = postService.getAllPosts();
        return ResponseEntity.ok(ApiResponse.success("Posts retrieved successfully", posts));
    }



    @GetMapping("/user")
    public ResponseEntity<ApiResponse<List<Post>>> getUserPosts(HttpServletRequest request) {
        String username = jwtUtil.extractUsernameFromRequest(request);
        List<Post> posts = postService.getUserPostsByUsername(username);
        return ResponseEntity.ok(ApiResponse.success("User posts retrieved successfully", posts));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<PostResponseDTO>> createPost(
            @RequestParam("image") MultipartFile image,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            HttpServletRequest request) throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(title);
        postDTO.setDescription(description);
        postDTO.setImage(image);

        String username = jwtUtil.extractUsernameFromRequest(request);
        PostResponseDTO response = postService.createPost(postDTO, username);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Post created successfully", response));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponseDTO>> updatePost(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            HttpServletRequest request) throws Exception {
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(title);
        postDTO.setDescription(description);
        postDTO.setImage(image);

        String username = jwtUtil.extractUsernameFromRequest(request);
        PostResponseDTO updatedPost = postService.updatePost(id, postDTO, username);
        return ResponseEntity.ok(ApiResponse.success("Post updated successfully", updatedPost));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id, HttpServletRequest request) {
        String username = jwtUtil.extractUsernameFromRequest(request);
        postService.deletePost(id, username);
        return ResponseEntity.ok(ApiResponse.success("Post deleted successfully", null));
    }

}