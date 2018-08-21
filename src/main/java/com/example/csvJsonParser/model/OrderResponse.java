package com.example.csvJsonParser.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@JsonInclude(Include.NON_NULL)
public class OrderResponse {
    private Long id;
    private Integer amount;
    private Currency currency;
    private String comment;
    private String filename;
    private Integer line;
    private String result;

    public OrderResponse(Order order, String filename, Integer line, String result) {
        this.id = order.getOrderId();
        this.amount = order.getAmount();
        this.currency = order.getCurrency();
        this.comment = order.getComment();
        this.filename = filename;
        this.line = line;
        this.result = result;
    }

    public OrderResponse(Order order) {
        this.id = order.getOrderId();
        this.amount = order.getAmount();
        this.currency = order.getCurrency();
        this.comment = order.getComment();
    }
}
