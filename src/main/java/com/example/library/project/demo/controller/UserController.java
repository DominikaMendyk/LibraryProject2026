package com.example.library.project.demo.controller;

import com.example.library.project.demo.entity.DTO.LoanHistoryDTO;
import com.example.library.project.demo.entity.Loan;
import com.example.library.project.demo.entity.Role;
import com.example.library.project.demo.entity.User;
import com.example.library.project.demo.security.JwtTokenService;
import com.example.library.project.demo.service.LoanService;
import com.example.library.project.demo.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final LoanService loanService;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, LoanService loanService,JwtTokenService jwtTokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.loanService = loanService;
        this.jwtTokenService = jwtTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public User addUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.addUser(user);
    }

    @DeleteMapping("/remove/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
    }

    @GetMapping("/getAll")
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/getAllRole/{role}")
    public Iterable<User> getAllUsersRole(@PathVariable Role role) {
        return userService.getAllUsersByRole(role);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }

    @Transactional
    @PostMapping("/{userId}/repay-credit")
    public User repayCredit(@PathVariable Integer userId,
                            @RequestParam Integer pay) {
        return userService.repayCredit(userId, pay);
    }

    @Transactional
    @PostMapping("/{userId}/add-credit")
    public User addCredit(@PathVariable Integer userId,
                            @RequestParam Integer credit) {
        return userService.addCredit(userId, credit);
    }

    @GetMapping("/my-currently-borrowed")
    public List<LoanHistoryDTO> getMyCurrentlyBorrowed(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Integer userId = jwtTokenService.extractUserId(token);
        return loanService.getCurrentlyBorrowedBooks(userId);
    }

    @GetMapping("/my-loan-history")
    public List<LoanHistoryDTO> getMyLoanHistory(
            @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        Integer userId = jwtTokenService.extractUserId(token);
        return loanService.getPreviouslyBorrowedBooks(userId);
    }

    @PostMapping("/borrow/{isbn}")
    public Loan borrowBook(
            @RequestParam String isbn, @RequestParam("Authorization") String authHeader){
        String token = authHeader.substring(7);
        Integer userId = jwtTokenService.extractUserId(token);
        return loanService.borrowBook(userId, isbn);
    }

    @PostMapping("/return/{isbn}")
    public Loan returnBook(
            @RequestParam String isbn, @RequestParam("Authorization") String authHeader){
        String token = authHeader.substring(7);
        Integer userId = jwtTokenService.extractUserId(token);
        return loanService.returnBook(userId, isbn);
    }

    @GetMapping("who-am-i")
    public String whoAmI(Authentication authentication){
        return "Username: " + authentication.getName() +", Role: " + authentication.getAuthorities();
    }
}