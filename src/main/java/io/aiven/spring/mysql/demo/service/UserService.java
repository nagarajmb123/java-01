package io.aiven.spring.mysql.demo.service;

import io.aiven.spring.mysql.demo.dto.UserDTO;
import io.aiven.spring.mysql.demo.exception.UserNotFoundException;
import io.aiven.spring.mysql.demo.model.User;
import io.aiven.spring.mysql.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Page<User> getAllUsers(String search, Pageable pageable) {
        return (search != null && !search.isEmpty())
                ? userRepository.findByNameContainingIgnoreCase(search, pageable)
                : userRepository.findAll(pageable);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found: " + id));
    }

    public User createUser(UserDTO userDTO) {
        return userRepository.save(new User(userDTO.getName(), userDTO.getEmail()));
    }

    public User updateUser(Long id, UserDTO userDTO) {
        User user = getUser(id);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) throw new UserNotFoundException("User not found: " + id);
        userRepository.deleteById(id);
    }
}