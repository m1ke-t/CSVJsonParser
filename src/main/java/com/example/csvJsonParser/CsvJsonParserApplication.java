package com.example.csvJsonParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paranamer.ParanamerModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class CsvJsonParserApplication implements CommandLineRunner {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ParanamerModule());
        return mapper;
    }

    @Autowired
    ParserService parserService;

    public static void main(String[] args) {
        SpringApplication.run(CsvJsonParserApplication.class, args);
    }

    @Override
    public void run(String... args) {
        parserService.parseFiles(Arrays.asList(args)).forEach(System.out::println);
    }
}
