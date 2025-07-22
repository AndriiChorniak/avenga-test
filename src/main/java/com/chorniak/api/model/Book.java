package com.chorniak.api.model;

import lombok.Data;

@Data
public class Book {
    private int id;
    private String title;
    private Author author;
} 