package dev.rakesh.userservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorAdviser {

    @ExceptionHandler(UserAlreadyPresentException.class)
    public ResponseEntity<ErrorMessage> AlreadyPresentException(Exception exception){
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundException(Exception exception){
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ErrorMessage> PasswordNotMatchException(Exception exception){
        ErrorMessage errorMessage=new ErrorMessage();
        errorMessage.setMessage(exception.getMessage());
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
    }
}
