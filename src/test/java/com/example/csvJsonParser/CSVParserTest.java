package com.example.csvJsonParser;

import com.example.csvJsonParser.model.Currency;
import com.example.csvJsonParser.model.Order;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.statements.ExpectException;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CSVParserTest {

    @Autowired
    CSVParser CSVParser;

    @Test
    public void test_no_quote() throws ParseException {

        String line = "10,100500,USD,comment";
        Order result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getOrderId(), (Long) 10L);
        Assert.assertEquals(result.getAmount(), (Integer) 100500);
        Assert.assertEquals(result.getCurrency(), Currency.USD);
        Assert.assertEquals(result.getComment(), "comment");
    }

    @Test
    public void test_no_quote_but_double_quotes_in_column() throws Exception {

        String line = "10,123,RUB,Com\"\"ment";

        Order result = CSVParser.parseLine(line);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getCurrency(), Currency.RUB);
        Assert.assertEquals(result.getComment(), "Com\"ment");
    }

    @Test
    public void test_double_quotes() throws ParseException {

        String line = "\"10\",\"5000\",\"EUR\",\"comment\"";
        Order result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getOrderId(), (Long) 10L);
        Assert.assertEquals(result.getAmount(), (Integer) 5000);
        Assert.assertEquals(result.getCurrency(), Currency.EUR);
        Assert.assertEquals(result.getComment(), "comment");

    }

    @Test
    public void test_double_quotes_but_double_quotes_in_column() throws ParseException {

        String line = "\"25\",\"100\",\"RUB\",\"Co\"\"mment\"";
        Order result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getOrderId(), (Long) 25L);
        Assert.assertEquals(result.getAmount(), (Integer) 100);
        Assert.assertEquals(result.getCurrency(), Currency.RUB);
        Assert.assertEquals(result.getComment(), "Co\"mment");
    }

    @Test
    public void test_double_quotes_but_comma_in_column() throws ParseException {

        String line = "\"35\",\"2500\",\"EUR\",\"Comm,entary\"";
        Order result = CSVParser.parseLine(line);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getOrderId(), (Long) 35L);
        Assert.assertEquals(result.getAmount(), (Integer) 2500);
        Assert.assertEquals(result.getCurrency(), Currency.EUR);
        Assert.assertEquals(result.getComment(), "Comm,entary");
    }

    @Test (expected = ParseException.class)
    public void test_string_with_incorrect_fields() throws ParseException {
        String line = "234,,USD,comment";
        CSVParser.parseLine(line);
    }

    @Test (expected = ParseException.class)
    public void test_string_with_incorrect_number_of_fields() throws ParseException {
        String line = "123,577,comment";
        CSVParser.parseLine(line);
    }
}
