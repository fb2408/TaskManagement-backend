package com.example.mobileappserver.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Note {
    private String subject;
    private String content;
    private Map<String, String> data;
    private String image;
}
