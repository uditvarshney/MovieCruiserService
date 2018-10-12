package com.stackroute.cruiser.exception;

public class MovieAlreadyExistsException extends Exception {

    String message;
    public MovieAlreadyExistsException(String message){
        super(message);
        this.message=message;
    }
}
