package com.onegini.domain;

import com.onegini.domain.enums.TransactionType;
import lombok.Builder;
import lombok.Getter;
import org.javamoney.moneta.Money;

import java.util.ArrayList;

@Getter
@Builder
public class BankData {
    private String userName;
    private String userSurname;
    private Money balance;
    private ArrayList<Transaction> transactionsHistory;
    private Token token;

    public void increaseBalance(Money value){
        balance = balance.add(value);
        transactionsHistory.add(new Transaction(TransactionType.INCREASE, value.getNumber()));
    }

    public boolean decreaseBalance(Money value){
        if(balance.isGreaterThan(value)){
            balance = balance.subtract(value);
            transactionsHistory.add(new Transaction(TransactionType.DECREASE, value.getNumber()));
            return true;
        }
        return false;
    }

    public void setToken(Token token){
        this.token = token;
    }
}
