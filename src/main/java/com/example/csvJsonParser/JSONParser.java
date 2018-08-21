package com.example.csvJsonParser;

import com.example.csvJsonParser.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JSONParser implements Parser {

    @Autowired
    Validator validator;

    @Override
    public Order parseLine(String s) throws ParseException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Order order = mapper.readValue(s, Order.class);
            validator.isValid(order);
            return order;
        } catch (IOException e) {
            throw new ParseException("JSON parse exception", e);
        }
    }
}