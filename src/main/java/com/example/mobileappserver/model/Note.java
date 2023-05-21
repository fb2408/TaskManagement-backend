package com.example.mobileappserver.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    private String subject;
    private String content;
    private Map<String, String> data;
    private String image;
}
