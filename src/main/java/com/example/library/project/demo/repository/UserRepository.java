package com.example.library.project.demo.repository;
import com.example.library.project.demo.entity.Role;
import com.example.library.project.demo.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    Iterable<User> findAllByRole(Role role);

    @Query(value = "SELECT * FROM User u WHERE u.username = ?1", nativeQuery = true)
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
