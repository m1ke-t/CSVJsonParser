package com.example.csvJsonParser;

import com.example.csvJsonParser.model.Currency;
import com.example.csvJsonParser.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CSVParser implements Parser {

    @Autowired
    Validator validator;

    public Order parseLine(String csvLine) throws ParseException {
        List<String> result = new ArrayList<>();

        if (csvLine != null && !csvLine.isEmpty()) {

            StringBuffer str = new StringBuffer();
            boolean inQuotes = false;
            boolean writeWordInQuotes = false;

            char[] chars = csvLine.toCharArray();

            for (char ch : chars) {

                if (inQuotes) {
                    writeWordInQuotes = true;
                    if (ch == '\"') {
                        inQuotes = false;
                    } else {
                        str.append(ch);
                    }
                } else {
                    if (ch == '\"') {
                        inQuotes = true;
                        if (chars[0] != '"') {
                            str.append('"');
                        }
                        if (writeWordInQuotes) {
                            str.append('"');
                        }
                    } else if (ch == ',') {
                        result.add(str.toString());
                        str = new StringBuffer();
                        writeWordInQuotes = false;
                    } else if ((ch == '\r') || (ch == '\n')) {
                        break;
                    } else {
                        str.append(ch);
                    }
                }
            }
            result.add(str.toString());
        }

        try {
            Order order = new Order(
                    Long.parseLong(result.get(0)),
                    Integer.parseInt(result.get(1)),
                    Currency.valueOf(result.get(2)),
                    result.get(3)
            );
            validator.isValid(order);
            return order;
        } catch (IllegalArgumentException e) {
            throw new ParseException("CSV parse exception: Incorrect field value", e);
        } catch (IndexOutOfBoundsException e) {
            throw new ParseException("CSV parse exception: Incorrect number of fields", e);
        }
    }
}