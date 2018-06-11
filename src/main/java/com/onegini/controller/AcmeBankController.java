package com.onegini.controller;

import com.onegini.exception.BusinessException;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.onegini.services.*;
import com.onegini.domain.Transaction;

import java.util.ArrayList;
import java.util.Collections;

@RestController
@RequestMapping("/acmebank")
public class AcmeBankController {

    private static final String CURRENCY = "USD";

    @Autowired
    AcmeBankService service;

    @RequestMapping(value="/balance/user/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUserBalance (@PathVariable("id") Long id){
        try {
            Money balance = service.getUserBalance(id);
            return new ResponseEntity(Collections.singletonMap("value", balance.getNumber()), HttpStatus.OK);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/history/user/{id}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getTransactionsHistory (@PathVariable("id") Long id){
        try {
            ArrayList<Transaction> transactions = service.getTransactionsHistory(id);
            return new ResponseEntity(Collections.singletonMap("transactions", transactions), HttpStatus.OK);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/balance/user/{id}/increase", method=RequestMethod.POST)
    public ResponseEntity increaseBalance(@PathVariable("id") Long id, @RequestParam(value = "value") Number value){
        try {
            service.increaseBalance(id, Money.of(value, CURRENCY));
            return new ResponseEntity(HttpStatus.OK);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/balance/user/{id}/decrease", method=RequestMethod.POST)
    public ResponseEntity decreaseBalance(@PathVariable("id") Long id, @RequestParam(value = "value") Number value, @RequestParam(value = "token") String token){
        try {
            if(service.decreaseBalance(id, Money.of(value, CURRENCY), token)){
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/tokens/user/{id}", method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity generateToken(@PathVariable("id") Long id){
        try {
            String token = service.generateToken(id);
            return new ResponseEntity(Collections.singletonMap("token", token), HttpStatus.CREATED);
        } catch (BusinessException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}