package com.onegini.repository;

import com.onegini.domain.BankData;
import com.onegini.domain.Token;
import lombok.Synchronized;
import org.javamoney.moneta.Money;
import com.onegini.domain.Transaction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class AcmeBankRepositoryImpl implements AcmeBankRepository {

    private HashMap<Long, BankData> bankDataCollection;

    public AcmeBankRepositoryImpl(){
        fillStartData();
    }

    @Override
    public Money getUserBalance(Long userId) {
        return bankDataCollection.get(userId).getBalance();
    }

    @Override
    public ArrayList<Transaction> getTransactionsHistory(Long userId) {
        return bankDataCollection.get(userId).getTransactionsHistory();
    }

    @Override
    @Synchronized
    public void increaseBalance(Long userId, Money value) {
        bankDataCollection.get(userId).increaseBalance(value);
    }

    @Override
    @Synchronized
    public boolean decreaseBalance(Long userId, Money value) {
        return bankDataCollection.get(userId).decreaseBalance(value);
    }

    @Override
    public String generateAndSetToken(Long userId) {
        Token token = new Token().generateToken();
        bankDataCollection.get(userId).setToken(token);
        return token.getValue();
    }

    @Override
    public boolean verifyToken(Long userId, String token){
        Token validToken = bankDataCollection.get(userId).getToken();
        return validToken.verifyToken(token);
    }

    @Override
    public boolean checkIfUserExists(Long userId) {
        return bankDataCollection.containsKey(userId);
    }

    @Override
    public void markTokenAsUsed(Long userId) {
        bankDataCollection.get(userId).getToken().markTokenAsUsed();
    }

    private void fillStartData(){
        bankDataCollection = new HashMap<>();

        BankData firstUser = BankData.builder().userName("Bilbo").userSurname("Baggins")
                .balance(Money.of(1000,"USD")).transactionsHistory(new ArrayList<>()).build();
        bankDataCollection.put(1001L, firstUser);

        BankData secondUser = BankData.builder().userName("Geralt").userSurname("of Rivia")
                .balance(Money.of(300,"USD")).transactionsHistory(new ArrayList<>()).build();
        bankDataCollection.put(1002L, secondUser);

        BankData thirdUser = BankData.builder().userName("Hermiona").userSurname("Granger")
                .balance(Money.of(100,"USD")).transactionsHistory(new ArrayList<>()).build();
        bankDataCollection.put(1003L, thirdUser);

        BankData fourthUser = BankData.builder().userName("Arlen").userSurname("Bales")
                .balance(Money.of(600,"USD")).transactionsHistory(new ArrayList<>()).build();
        bankDataCollection.put(1004L, fourthUser);

        BankData fifthUser = BankData.builder().userName("Stanisława").userSurname("Kruszewska")
                .balance(Money.of(800,"USD")).transactionsHistory(new ArrayList<>()).build();
        bankDataCollection.put(1005L, fifthUser);

        BankData sixthUser = BankData.builder().userName("John").userSurname("Yossarian")
                .balance(Money.of(400,"USD")).transactionsHistory(new ArrayList<>()).build();
        bankDataCollection.put(1006L, sixthUser);
    }
}
