package com.example.csvJsonParser.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {
    private Long orderId;
    private Integer amount;
    private Currency currency;
    private String comment;

    //@JsonCreator
    public Order(Long orderId, Integer amount, Currency currency, String comment) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.comment = comment;
    }
}
