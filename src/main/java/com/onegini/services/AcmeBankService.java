package com.onegini.services;

import com.onegini.domain.Transaction;
import com.onegini.exception.BusinessException;
import org.javamoney.moneta.Money;

import java.util.ArrayList;

public interface AcmeBankService {
    Money getUserBalance(Long userId) throws BusinessException;
    ArrayList<Transaction> getTransactionsHistory(Long userId) throws BusinessException;
    void increaseBalance(Long userId, Money value) throws BusinessException;
    boolean decreaseBalance(Long userId, Money value, String token) throws BusinessException;
    String generateToken(Long userId) throws BusinessException;
}