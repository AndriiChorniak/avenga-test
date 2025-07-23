package com.chorniak.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private int id;
    private int idBook;
    private String firstName;
    private String lastName;
} 