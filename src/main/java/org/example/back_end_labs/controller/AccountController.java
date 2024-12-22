package org.example.back_end_labs.controller;

import org.example.back_end_labs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/add-funds")
    public ResponseEntity<String> addFunds(@RequestParam Double amount, @AuthenticationPrincipal String userId) {
        accountService.addFunds(Long.parseLong(userId), amount);
        return ResponseEntity.ok("Your account has been successfully topped up with " + amount);
    }

    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(@AuthenticationPrincipal String userId) {
        return ResponseEntity.ok("Your balance: " + accountService.getBalance(Long.parseLong(userId)));
    }

}