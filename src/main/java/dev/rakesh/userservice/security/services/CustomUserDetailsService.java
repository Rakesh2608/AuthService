package dev.rakesh.userservice.security.services;

import dev.rakesh.userservice.models.User;
import dev.rakesh.userservice.repositories.UserRepository;
import dev.rakesh.userservice.security.models.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByEmail(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException(username+ " Doesn't exist");
        }

        return new CustomUserDetails(user.get());
    }
}
