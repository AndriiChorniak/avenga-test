package com.chorniak.api.model;

import lombok.Data;

@Data
public class Author {
    private int id;
    private int idBook;
    private String firstName;
    private String lastName;
} 