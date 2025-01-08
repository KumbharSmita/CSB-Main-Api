package com.example.accountapi.controller;

import com.example.accountapi.model.Account;
import com.example.accountapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account")
@Validated
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@Valid @RequestBody Account account) {
        if (accountService.findAccountByUsername(account.getUsername()) != null) {
            return ResponseEntity.status(400).body("Account already exists!");
        }

        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.status(201).body("Account created successfully with ID: " + createdAccount.getId());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Long id) {
        Account account = accountService.findAccountById(id);
        if (account == null) {
            return ResponseEntity.status(404).body("Account not found!");
        }
        return ResponseEntity.ok(account);
    }
}
