package com.onegini.domain;

import lombok.Getter;
import org.apache.commons.text.RandomStringGenerator;

import java.time.LocalDateTime;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@Getter
public class Token {

    private static final Long EXPIRY_TIME_IN_MUNUTES = 5L;

    private String value;
    private LocalDateTime timeCreate;
    private boolean isTokenUsed;

    public Token generateToken(){
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('0', 'z').filteredBy(LETTERS, DIGITS).build();
        value = generator.generate(6);
        timeCreate = LocalDateTime.now();
        isTokenUsed = false;
        return this;
    }

    public boolean verifyToken(String tokenValueFromUser){
        return value.equals(tokenValueFromUser) &&
                timeCreate.plusMinutes(EXPIRY_TIME_IN_MUNUTES).isAfter(LocalDateTime.now()) &&
                !isTokenUsed;
    }

    public void markTokenAsUsed(){
        isTokenUsed = true;
    }
}
