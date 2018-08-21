package com.example.csvJsonParser;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class ParserFactory {

    @Autowired
    CSVParser csvParser;
    @Autowired
    JSONParser jsonParser;

    public Parser getParser(File file) {
        int index = file.getName().lastIndexOf('.');
        String fileExtension = file.getName().substring(index + 1);
        switch (fileExtension) {
            case "csv": return csvParser;
            case "json": return jsonParser;
            default: {
                log.warn("{} {}", fileExtension, " is not supported");
                throw new IllegalArgumentException();
            }
        }
    }
}
