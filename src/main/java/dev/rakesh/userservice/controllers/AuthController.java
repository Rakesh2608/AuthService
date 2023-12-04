package dev.rakesh.userservice.controllers;

import dev.rakesh.userservice.dtos.*;
import dev.rakesh.userservice.exceptions.PasswordNotMatchException;
import dev.rakesh.userservice.exceptions.UserAlreadyPresentException;
import dev.rakesh.userservice.exceptions.UserNotFoundException;
import dev.rakesh.userservice.models.SessionStatus;
import dev.rakesh.userservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    
    @PostMapping ("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginDto loginDto) throws UserNotFoundException, PasswordNotMatchException {
        String email=loginDto.getEmail();
        String password=loginDto.getPassword();
        ResponseEntity<UserDto> responseResult=authService.login(email,password);
        return responseResult;
    }
    
    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponseDto> validate(@RequestBody ValidateTokenRequestDto validateTokenRequestDto){
        Optional<UserDto> userDto=authService.validate(validateTokenRequestDto.getUserId(),validateTokenRequestDto.getToken());

        if(userDto.isEmpty()){
            ValidateTokenResponseDto validateTokenResponseDto=new ValidateTokenResponseDto();
            validateTokenResponseDto.setSessionStatus(SessionStatus.INVALID);
            return new ResponseEntity<>(validateTokenResponseDto,HttpStatus.OK);
        }

        ValidateTokenResponseDto validateTokenResponseDto=new ValidateTokenResponseDto();
        validateTokenResponseDto.setUserDto(userDto.get());
        validateTokenResponseDto.setSessionStatus(SessionStatus.ACTIVE);
        return new ResponseEntity<>(validateTokenResponseDto,HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signup(@RequestBody SignupDto signupDto) throws UserAlreadyPresentException {
        UserDto userDto=authService.signup(signupDto);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }
}
