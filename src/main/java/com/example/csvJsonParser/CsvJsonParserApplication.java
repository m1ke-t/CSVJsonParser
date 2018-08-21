package com.example.csvJsonParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CsvJsonParserApplication implements CommandLineRunner {

    @Autowired
    ParserService parserService;

	public static void main(String[] args) {
		SpringApplication.run(CsvJsonParserApplication.class, args);
	}
    @Override
    public void run(String... args) {

        parserService.parseFiles(Arrays.asList(args));
    }
}
