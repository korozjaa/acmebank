package com.onegini.services;

import com.onegini.domain.Transaction;
import com.onegini.exception.BusinessException;
import org.javamoney.moneta.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.onegini.repository.AcmeBankRepositoryImpl;

import java.util.ArrayList;

public class AcmeBankServiceTest {

    private AcmeBankServiceImpl serviceUnderTests = new AcmeBankServiceImpl();
    private Long testedUserId = 1004L;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp(){
        serviceUnderTests.repository = new AcmeBankRepositoryImpl();
    }

    @Test
    public void shouldReturnUserBalance() throws BusinessException {
        Money expectedBalance = Money.of(600,"USD");

        Money recivedBalance = serviceUnderTests.getUserBalance(testedUserId);

        Assert.assertTrue(expectedBalance.isEqualTo(recivedBalance));
    }

    @Test
    public void shouldThrowBusinessException() throws BusinessException {
        thrown.expect(BusinessException.class);
        serviceUnderTests.getUserBalance(999L);
    }

    @Test
    public void shouldIncreaseBalance() throws BusinessException {
        Money transactionValue = Money.of(60,"USD");
        serviceUnderTests.increaseBalance(testedUserId, transactionValue);

        Money expectedBalance = Money.of(660,"USD");

        Money recivedBalance = serviceUnderTests.getUserBalance(testedUserId);

        Assert.assertTrue(expectedBalance.isEqualTo(recivedBalance));
    }

    @Test
    public void shouldDecreaseBalance() throws BusinessException {
        Money transactionValue = Money.of(20,"USD");
        String token = serviceUnderTests.generateToken(testedUserId);
        boolean response = serviceUnderTests.decreaseBalance(testedUserId, transactionValue, token);

        Assert.assertTrue(response);
    }

    @Test
    public void shouldReturnTransactionsHistory() throws BusinessException {
        Money transactionValue = Money.of(60,"USD");
        serviceUnderTests.increaseBalance(testedUserId, transactionValue);

        ArrayList<Transaction> response = serviceUnderTests.getTransactionsHistory(testedUserId);

        Assert.assertFalse(response.isEmpty());
    }

    @Test
    public void shouldGenerateToken() throws BusinessException {
        String response = serviceUnderTests.generateToken(testedUserId);

        Assert.assertEquals(response.length(), 6);
    }
}
