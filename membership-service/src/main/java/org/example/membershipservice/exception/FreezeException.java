package org.example.membershipservice.exception;

public class FreezeException extends RuntimeException{
    public FreezeException(String message){
        super(message);
    }
}
