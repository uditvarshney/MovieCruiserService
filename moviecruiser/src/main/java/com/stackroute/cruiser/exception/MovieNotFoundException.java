package com.stackroute.cruiser.exception;

public class MovieNotFoundException  extends Exception {

    String message;

    public MovieNotFoundException(String message){
        super(message);
        this.message=message;
    }
}
