package org.example.back_end_labs.controller;

import org.example.back_end_labs.model.Account;
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
    public ResponseEntity<Account> createAccount(@RequestParam Long userId, @RequestParam Double initialBalance) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(userId, initialBalance));
    }

    @PostMapping("/add-funds")
    public ResponseEntity<Account> addFunds(@RequestParam Long userId, @RequestParam Double amount) {
        return ResponseEntity.ok(accountService.addFunds(userId, amount));
    }

    @GetMapping("/balance/{userId}")
    public ResponseEntity<Double> getBalance(@PathVariable Long userId) {
        return ResponseEntity.ok(accountService.getBalance(userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long userId) {
        accountService.deleteAccount(userId);
        return ResponseEntity.ok("Account for user with ID " + userId + " has been successfully deleted.");
    }

    @GetMapping("/all")
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
}
