package com.onlinejudge.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "problemset")
public class Problem {
    @Id
    private String id;
    private String title;
    private String description;
    private String inputFormat;
    private String outputFormat;
    private String sampleInput;
    private String sampleOutput;
    private String constraints;
    private int languageId;

    // Constructors, Getters, and Setters
}
