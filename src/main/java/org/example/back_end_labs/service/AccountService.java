package org.example.back_end_labs.service;

import org.example.back_end_labs.model.Account;
import org.example.back_end_labs.model.User;
import org.example.back_end_labs.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public Account createAccount(Long userId, Double initialBalance) {
        User user = userService.getUserById(userId);
        accountRepository.findByUserId(userId)
                .ifPresent(existingAccount -> {
                    throw new IllegalArgumentException("Account for user with ID " + userId + " already exists.");
                });

        Account account = new Account(user, initialBalance);
        return accountRepository.save(account);
    }

    public Account addFunds(Long userId, Double amount) {
        Account account = getAccountByUserId(userId);
        account.addFunds(amount);
        return accountRepository.save(account);
    }

    public Double getBalance(Long userId) {
        Account account = getAccountByUserId(userId);
        return account.getBalance();
    }

    public Account getAccountByUserId(Long userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Account for user with ID " + userId + " does not exist."));
    }

    public void deleteAccount(Long userId) {
        Account account = getAccountByUserId(userId);
        accountRepository.delete(account);
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            throw new IllegalStateException("No users found.");
        }
        return accounts;
    }
}
