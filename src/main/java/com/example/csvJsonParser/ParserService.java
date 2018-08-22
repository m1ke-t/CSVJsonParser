package com.example.csvJsonParser;

import com.example.csvJsonParser.model.OrderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParserService {

    @Autowired
    ParserFactory parserFactory;

    private Parser parser;
    private ObjectMapper mapper = new ObjectMapper();

    @AllArgsConstructor
    @Getter
    private class RawString {
        private String data;
        private int line;
    }

    public ArrayList<String> parseFiles(List<String> filenames) {
        ArrayList<String> result = new ArrayList<>();
        ArrayList<File> files = new ArrayList<>();
        for (String filename : filenames) {
            files.add(new File(filename));
        }

        for (File f : files) {
            parser = parserFactory.getParser(f);
            AtomicInteger i = new AtomicInteger();

            try {
                result.addAll(
                Files.lines(f.toPath(), Charset.forName("UTF-8"))
                        .map(s -> toRawString(s, i.getAndIncrement()))
                        .parallel()
                        .map(o -> toOrderResponse(o, f.getName()))
                        .map(this::toJSONString)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return result;
    }

    private RawString toRawString(String data, int line) {
        return new RawString(data, line);
    }

    private OrderResponse toOrderResponse(RawString s, String filename) {
        OrderResponse response;
        try {
            response = new OrderResponse(parser.parseLine(s.data));
            response.setResult("OK");
        } catch (ParseException e) {
            if (e.getCause() != null)
                log.warn("{} line: {} :: {} :: {}", filename, s.line, e.getMessage(), e.getCause().toString());
            else log.warn("{} line: {} :: {}", filename, s.line, e.getMessage());

            response = new OrderResponse();
            response.setResult("FAIL: " + e.getMessage());
        }
        response.setLine(s.line);
        response.setFilename(filename);
        return response;
    }

    private String toJSONString(OrderResponse response) {
        try {
            return mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            log.error("{} line: {} :: {} :: {}", response.getFilename(), response.getLine(), "Object to JSON string mapping error", e.getMessage());
        }
        return null;
    }
}