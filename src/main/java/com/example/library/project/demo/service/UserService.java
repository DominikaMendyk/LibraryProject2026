package com.example.library.project.demo.service;

import com.example.library.project.demo.entity.User;
import com.example.library.project.demo.exception.UserException;
import com.example.library.project.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User addUser(User user) {
        try {
            // Set initial credit to 0 if not provided
            if (user.getCredit() == null) {
                user.setCredit(0);
            }
            return userRepository.save(user);
        } catch (Exception e){
            throw UserException.create("Failed to add user: " + e.getMessage());
        }
    }

    @Transactional
    public void deleteUser(Integer userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserException.create("Cannot delete user: User not found"));
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            throw UserException.create("Failed to delete user: " + e.getMessage());
        }
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> UserException.create("User not found"));
    }

    @Transactional
    public User repayCredit(Integer userId, Integer amountPaid) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserException.create("User not found"));
        if (amountPaid == null || amountPaid <= 0){
            throw UserException.create("Invalid payment amount");
        }
        int payment = (-1) * amountPaid;
        user.updateCredit(payment);
        return userRepository.save(user);
    }

    @Transactional
    public User addCredit(Integer userId, Integer creditToAdd) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> UserException.create("User not found"));
        if (creditToAdd == null || creditToAdd <= 0){
            throw UserException.create("Invalid credit amount");
        }
        user.updateCredit(creditToAdd);
        return userRepository.save(user);
    }
}