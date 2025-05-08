package io.aiven.spring.mysql.demo.service;

import io.aiven.spring.mysql.demo.dto.UserDTO;
import io.aiven.spring.mysql.demo.exception.UserNotFoundException;
import io.aiven.spring.mysql.demo.model.User;
import io.aiven.spring.mysql.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<User> getAllUsers(String search, Pageable pageable) {
        return (search != null && !search.isEmpty())
                ? userRepository.findByUsernameContainingIgnoreCase(search, pageable)
                : userRepository.findAll(pageable);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

//    public User createUser(UserDTO userDTO) {
//        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        return userRepository.save(new User(userDTO.getUsername(), userDTO.getEmail(), currentUsername));
//    }

    public User updateUser(Long id, UserDTO userDTO) {
        User user = getUser(id);
        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!user.getCreatedBy().equals(currentUsername)) {
            throw new RuntimeException("Unauthorized to update this user");
        }

        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUser(id);
        String currentUsername = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!user.getCreatedBy().equals(currentUsername)) {
            throw new RuntimeException("Unauthorized to delete this user");
        }

        userRepository.deleteById(id);
    }
}
