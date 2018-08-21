package com.example.csvJsonParser;

import com.example.csvJsonParser.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Validator {
    public void isValid(Order order) throws ParseException {
            if (order.getAmount() == null &&
                order.getComment() == null &&
                order.getCurrency() == null &&
                order.getOrderId() == null)
                throw new ParseException("Parse exception: Order has null fields: ");
    }
}
