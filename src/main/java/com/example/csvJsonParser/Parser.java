package com.example.csvJsonParser;

import com.example.csvJsonParser.model.Order;

public interface Parser {
    Order parseLine(String s) throws ParseException;
}
