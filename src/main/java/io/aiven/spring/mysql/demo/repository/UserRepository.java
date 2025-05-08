package io.aiven.spring.mysql.demo.repository;

import io.aiven.spring.mysql.demo.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
    Optional<User> findByUsername(String username);
}


/*
Summary of the UserRepository
The UserRepository is a Spring Data JPA repository interface for managing User entities in a MySQL database. It:

Extends JpaRepository<User, Long> to inherit basic CRUD and pagination methods.

Defines a custom method findByNameContainingIgnoreCase() to search for users by name in a case-insensitive and paginated way.

Is annotated with @Repository so that Spring can manage it as a database access component (DAO).


 Why is this class an interface instead of a class?
Spring Data JPA generates the implementation automatically.

You only define the method signatures (like findByNameContainingIgnoreCase), and Spring will create the actual implementation behind the scenes.

This saves time—you don’t have to write SQL or method logic manually.

Inheritance from JpaRepository is interface-based.

JpaRepository itself is an interface that defines many ready-to-use methods.

By extending it, your interface inherits those methods.

Decouples logic from implementation.

Encourages clean architecture: the interface defines "what" operations are available, and Spring provides "how" to execute them.
 */