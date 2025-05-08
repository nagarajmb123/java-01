package io.aiven.spring.mysql.demo.service;

import io.aiven.spring.mysql.demo.dto.PostDTO;
import io.aiven.spring.mysql.demo.dto.PostResponseDTO;
import io.aiven.spring.mysql.demo.model.Post;
import io.aiven.spring.mysql.demo.model.User;
import io.aiven.spring.mysql.demo.repository.PostRepository;
import io.aiven.spring.mysql.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    public PostResponseDTO createPost(PostDTO postDTO, String username) throws Exception {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        String imageUrl = cloudinaryService.uploadImage(postDTO.getImage());

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());
        post.setImageUrl(imageUrl);
        post.setUser(user);

        Post savedPost = postRepository.save(post);
        return new PostResponseDTO(savedPost);
    }

    public List<PostResponseDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
    }


    public List<Post> getUserPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return postRepository.findByUser(user);
    }

    public PostResponseDTO updatePost(Long id, PostDTO postDTO, String username) throws Exception {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }

        post.setTitle(postDTO.getTitle());
        post.setDescription(postDTO.getDescription());

        if (!postDTO.getImage().isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(postDTO.getImage());
            post.setImageUrl(imageUrl);
        }
        Post updatedPost = postRepository.save(post);

        return new PostResponseDTO(updatedPost);

    }

    public void deletePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Access denied");
        }

        postRepository.delete(post);
    }
}