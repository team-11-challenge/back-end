package com.example.courseregistratioonbackend.global.parsing.utils;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ReadLineContext {

    public List<String[]> readByLine(String filename) throws IOException, CsvValidationException {
        List<String[]> result = new ArrayList<>();
        CSVReader csvReader = new CSVReader(new FileReader(filename));
        String[] nextLine;
        while((nextLine = csvReader.readNext()) != null){
            try{
                result.add(nextLine);
            }catch (Exception e){
                log.info(e.getMessage());
            }

        }
        csvReader.close();
        return result;
    }
}
