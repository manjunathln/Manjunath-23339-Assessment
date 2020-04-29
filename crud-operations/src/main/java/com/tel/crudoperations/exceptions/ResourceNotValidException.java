package com.tel.crudoperations.exceptions;


public class ResourceNotValidException extends Exception {
    private static final long serialVersionUID = 1L;
    public ResourceNotValidException(String message){
        super(message);
    }
}
