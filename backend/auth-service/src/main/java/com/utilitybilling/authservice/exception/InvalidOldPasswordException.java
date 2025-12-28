package com.utilitybilling.authservice.exception;

public class InvalidOldPasswordException extends RuntimeException{
    public InvalidOldPasswordException(String message){
        super(message);
    }
}
