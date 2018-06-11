package com.onegini.repository;

import org.javamoney.moneta.Money;
import com.onegini.domain.Transaction;

import java.util.ArrayList;

public interface AcmeBankRepository {
    Money getUserBalance(Long userId);
    ArrayList<Transaction> getTransactionsHistory(Long userId);
    void increaseBalance(Long userId, Money value);
    boolean decreaseBalance(Long userId, Money value);
    String generateAndSetToken(Long userId);
    boolean verifyToken(Long userId, String token);
    boolean checkIfUserExists(Long userId);
    void markTokenAsUsed(Long userId);
}
