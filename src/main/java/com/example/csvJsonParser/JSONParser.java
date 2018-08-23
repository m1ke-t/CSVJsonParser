package com.example.csvJsonParser;

import com.example.csvJsonParser.model.Order;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component("json")
public class JSONParser implements Parser {

    @Autowired
    ObjectMapper mapper;

    @Override
    public Order parseLine(String s) throws ParseException {
        try {
            return mapper.readValue(s, Order.class);
        } catch (IOException e) {
            throw new ParseException("JSON parse exception", e);
        }
    }
}