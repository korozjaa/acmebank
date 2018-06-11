package com.onegini.services;

import com.onegini.domain.Transaction;
import com.onegini.exception.BusinessException;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.onegini.repository.AcmeBankRepository;

import java.util.ArrayList;

@Service
public class AcmeBankServiceImpl implements AcmeBankService{

    private static final String USER_NOT_FOUND_STATEMENT = "User not found.";

    @Autowired
    AcmeBankRepository repository;

    @Override
    public Money getUserBalance(Long userId) throws BusinessException{
        if(!repository.checkIfUserExists(userId)){
            throw new BusinessException(USER_NOT_FOUND_STATEMENT);
        }
        return repository.getUserBalance(userId);
    }

    @Override
    public ArrayList<Transaction> getTransactionsHistory(Long userId) throws BusinessException{
        if(!repository.checkIfUserExists(userId)){
            throw new BusinessException(USER_NOT_FOUND_STATEMENT);
        }
        return repository.getTransactionsHistory(userId);
    }

    @Override
    public void increaseBalance(Long userId, Money value) throws BusinessException{
        if(!repository.checkIfUserExists(userId)){
            throw new BusinessException(USER_NOT_FOUND_STATEMENT);
        }
        repository.increaseBalance(userId, value);
    }

    @Override
    public boolean decreaseBalance(Long userId, Money value, String token) throws BusinessException{
        if(!repository.checkIfUserExists(userId)){
            throw new BusinessException(USER_NOT_FOUND_STATEMENT);
        }
        if(repository.verifyToken(userId, token)){
            boolean operationSuccesed = repository.decreaseBalance(userId, value);
            if(operationSuccesed) repository.markTokenAsUsed(userId);
            return operationSuccesed;
        }
        return false;
    }

    @Override
    public String generateToken(Long userId) throws BusinessException {
        if(!repository.checkIfUserExists(userId)){
            throw new BusinessException(USER_NOT_FOUND_STATEMENT);
        }
        return repository.generateAndSetToken(userId);
    }
}
