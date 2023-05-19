package com.example.mobileappserver.exceptions;

public class NoSuchFoundElementException extends RuntimeException{

    public NoSuchFoundElementException(String messagge) {
        super(messagge);
    }
}
