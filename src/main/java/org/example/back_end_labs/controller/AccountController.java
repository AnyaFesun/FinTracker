package org.example.back_end_labs.controller;

import org.example.back_end_labs.model.Account;
import org.example.back_end_labs.model.User;
import org.example.back_end_labs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestParam Long userId, @RequestParam Double initialBalance) {
        try {
            Account account = accountService.createAccount(userId, initialBalance);
            return ResponseEntity.status(HttpStatus.CREATED).body(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/add-funds")
    public ResponseEntity<?> addFunds(@RequestParam Long userId, @RequestParam Double amount) {
        try {
            Account account = accountService.addFunds(userId, amount);
            return ResponseEntity.ok(account);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<?> getBalance(@PathVariable Long userId) {
        try {
            Double balance = accountService.getBalance(userId);
            return ResponseEntity.ok(balance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long userId) {
        try {
            accountService.deleteAccount(userId);
            return ResponseEntity.ok("Account for user with ID " + userId + " has been successfully deleted.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllAccounts() {
        try {
            List<Account> accounts = accountService.getAllAccounts();
            return ResponseEntity.ok(accounts);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
