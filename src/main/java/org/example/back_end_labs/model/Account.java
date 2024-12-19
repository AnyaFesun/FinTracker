package org.example.back_end_labs.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private Double balance;

    public Account(User user, Double initialBalance) {
        this.user = user;
        this.balance = initialBalance;
    }

    public void addFunds(Double amount) {
        if (amount == null || amount <= 0) {
            throw new IllegalArgumentException("Amount to add must be a positive value.");
        }
        this.balance += amount;
    }

    public boolean canWithdraw(Double amount) {
        return this.balance >= amount;
    }

    public void withdraw(Double amount) {
        if (!canWithdraw(amount)) {
            throw new IllegalArgumentException("Insufficient funds in account.");
        }
        this.balance -= amount;
    }

}
