package com.example.courseregistratioonbackend.parsing;

public interface Parser<T> {
    T parse(String str);
}