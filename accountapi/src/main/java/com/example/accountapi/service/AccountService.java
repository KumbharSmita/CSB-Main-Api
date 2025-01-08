package com.example.accountapi.service;

import com.example.accountapi.model.Account;
import com.example.accountapi.repository.AccountRepository;
import com.example.accountapi.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private final byte[] sessionKey;

    public AccountService(@Value("${encryption.session-key}") String sessionKey) {
        this.sessionKey = sessionKey.getBytes(); // Convert session key to byte array
    }

    public Account createAccount(Account account) {
        try {
            // Encrypt sensitive fields
            account.setUsername(EncryptionUtil.encrypt(account.getUsername(), sessionKey));
            account.setPassword(EncryptionUtil.encrypt(account.getPassword(), sessionKey));
            account.setGrantType(EncryptionUtil.encrypt(account.getGrantType(), sessionKey));
            account.setFirstName(EncryptionUtil.encrypt(account.getFirstName(), sessionKey));
            account.setLastName(EncryptionUtil.encrypt(account.getLastName(), sessionKey));
            account.setEmail(EncryptionUtil.encrypt(account.getEmail(), sessionKey));
            account.setMobile(EncryptionUtil.encrypt(account.getMobile(), sessionKey));

            return accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Encryption failed while creating account!", e);
        }
    }

    public Account findAccountById(Long id) {
        return accountRepository.findById(id)
                .map(this::decryptAccount)
                .orElse(null); // Return null if not found
    }

    public Account findAccountByUsername(String username) {
        Account account = accountRepository.findByUsername(username);
        return account != null ? decryptAccount(account) : null;
    }

    private Account decryptAccount(Account account) {
        try {
            // Decrypt sensitive fields
            account.setUsername(EncryptionUtil.decrypt(account.getUsername(), sessionKey));
            account.setPassword(EncryptionUtil.decrypt(account.getPassword(), sessionKey));
            account.setGrantType(EncryptionUtil.decrypt(account.getGrantType(), sessionKey));
            account.setFirstName(EncryptionUtil.decrypt(account.getFirstName(), sessionKey));
            account.setLastName(EncryptionUtil.decrypt(account.getLastName(), sessionKey));
            account.setEmail(EncryptionUtil.decrypt(account.getEmail(), sessionKey));
            account.setMobile(EncryptionUtil.decrypt(account.getMobile(), sessionKey));
            return account;
        } catch (Exception e) {
            throw new RuntimeException("Decryption failed while fetching account!", e);
        }
    }
}

