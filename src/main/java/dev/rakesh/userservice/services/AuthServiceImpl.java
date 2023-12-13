package dev.rakesh.userservice.services;

import dev.rakesh.userservice.dtos.SignupDto;
import dev.rakesh.userservice.dtos.UserDto;
import dev.rakesh.userservice.exceptions.PasswordNotMatchException;
import dev.rakesh.userservice.exceptions.UserAlreadyPresentException;
import dev.rakesh.userservice.exceptions.UserNotFoundException;
import dev.rakesh.userservice.models.Session;
import dev.rakesh.userservice.models.SessionStatus;
import dev.rakesh.userservice.models.User;
import dev.rakesh.userservice.repositories.SessionRepository;
import dev.rakesh.userservice.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseEntity<UserDto> login(String email, String password) throws UserNotFoundException, PasswordNotMatchException {
/*
        String alphaNumeric="QWERTYUIOPASDFGHJKLZXCVBNM"+"qwertyuiopasdfghjklzxcvbnm"+"1234567890";

        StringBuilder sb=new StringBuilder();

        for(int i=0;i<20;i++){
            int index=(int)(alphaNumeric.length()*Math.random());
            sb.append(alphaNumeric.charAt(index));
        }
        //saving to session DB.
        String token=sb.toString();
        Session session=new Session();
        session.setToken(token);
        sessionRepository.save(session);
*/

        //Implementing using RandomStringUtils as token

        Optional<User> userOptional=userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User is not present with "+email+" Enter valid email Id");
        }

        if(!bCryptPasswordEncoder.matches(password,userOptional.get().getPassword())){
            throw new PasswordNotMatchException("Password doesn't match");
        }

        User user=userOptional.get();



        //String token= RandomStringUtils.randomAscii(20);
        String secretString="123456789sqwertyuiasdfghjklzxcvbnmqwertyuiopasdfghjk";
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretString));
        Map<String,Object> claims=new HashMap<>();
        claims.put("email",user.getEmail());
        claims.put("fullName",user.getFullName());
        claims.put("roles",user.getRoles());
        String token= Jwts.builder().claims(claims).signWith(key).compact();


        MultiValueMapAdapter<String,String> headers=new MultiValueMapAdapter<>(new HashMap<>());
        headers.add("AUTH_TOKEN",token);
        Session session=new Session();
        session.setUser(user);
        session.setToken(token);
        session.setSessionStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        UserDto userDto=UserDto.from(user);
        return new ResponseEntity<>(
                userDto,headers,HttpStatus.OK
        );

    }

    //saving new user
    @Override
    public UserDto signup(SignupDto signupDto) throws UserAlreadyPresentException {
        String email=signupDto.getEmail();
        Optional<User> findUser= userRepository.findByEmail(email);

        if(findUser.isPresent()){
            throw new UserAlreadyPresentException("User with "+email+"Already present Try with new Email");
        }
        User user=new User();
        user.setEmail(email);

        user.setPassword(bCryptPasswordEncoder.encode(signupDto.getPassword()));
        user.setFullName(signupDto.getFullName());
        User savedUser=userRepository.save(user);

        return UserDto.from(savedUser);
    }

    @Override
    public Optional<UserDto> validate(Long Id, String token) {
        Optional<Session> sessionOptional= Optional.ofNullable((sessionRepository.findByTokenAndUser_Id(token, Id)));
        if(sessionOptional.isEmpty()){
           return Optional.empty();
        }
        Session session=sessionOptional.get();
        if(!session.getSessionStatus().equals(SessionStatus.ACTIVE)){
            return Optional.empty();
        }
//        if(!(session.getExpiredAt().toInstant().isAfter(new Date().toInstant()))){
//            return SessionStatus.EXPIRED;
//        }

        User user=userRepository.findById(Id).get();
        UserDto userDto=UserDto.from(user);

        return Optional.of(userDto);
    }

}
