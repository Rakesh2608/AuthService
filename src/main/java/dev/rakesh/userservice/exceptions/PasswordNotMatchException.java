package dev.rakesh.userservice.exceptions;

public class PasswordNotMatchException extends Exception{
    public PasswordNotMatchException(String message){
        super(message);
    }
}
