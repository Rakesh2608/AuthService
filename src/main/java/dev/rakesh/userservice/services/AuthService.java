package dev.rakesh.userservice.services;

import dev.rakesh.userservice.dtos.SignupDto;
import dev.rakesh.userservice.dtos.UserDto;
import dev.rakesh.userservice.exceptions.PasswordNotMatchException;
import dev.rakesh.userservice.exceptions.UserAlreadyPresentException;
import dev.rakesh.userservice.exceptions.UserNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface AuthService {
    ResponseEntity<UserDto> login(String email, String password) throws UserNotFoundException, PasswordNotMatchException;
    UserDto signup(SignupDto signupDto) throws UserAlreadyPresentException;
    Optional<UserDto> validate(Long Id, String token);
}
