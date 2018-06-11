package com.onegini.domain;

import com.onegini.domain.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Transaction {
    private TransactionType type;
    private Number value;
}
