package com.example.courseregistratioonbackend.parsing;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ReadLineContext<T> {
    Parser<T> parser;

    public ReadLineContext(Parser<T> parser){
        this.parser = parser;
    }

    public List<T> readByLine(String filename) throws IOException{
        List<T> result = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new FileReader(filename)
        );
        String str;
        while((str = reader.readLine()) != null){
            try{
                result.add(parser.parse(str));
            }catch (Exception e){
                log.info("파싱 중 문제가 생겨 이 라인은 넘어갑니다. 순번: " + str.split(",")[0]);
            }
            if(str.split(",")[0].equals("163")) break; // 여기서 부터 시간표 데이터가 이상함
        }
        reader.close();
        return result;
    }
}
