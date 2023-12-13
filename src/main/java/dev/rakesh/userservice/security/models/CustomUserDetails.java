package dev.rakesh.userservice.security.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.rakesh.userservice.models.Role;
import dev.rakesh.userservice.models.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@JsonDeserialize
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private Long id;
    //private User user;
    private List<GrantedAuthority> authorities;
    private String password;
    private String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public CustomUserDetails(User user) {
        //this.user = user;
        authorities=new ArrayList<>();

        for(Role role: user.getRoles()){
            authorities.add(new CustomGrantedAuthority(role));
        }
        this.password=user.getPassword();
        this.username=user.getEmail();
        this.accountNonExpired=true;
        this.accountNonLocked=true;
        this.credentialsNonExpired=true;
        this.enabled=true;
        this.id= user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<CustomGrantedAuthority> customGrantedAuthorities=new ArrayList<>();
//
//        for(Role role: user.getRoles()){
//            customGrantedAuthorities.add(new CustomGrantedAuthority(role));
//        }
//        return customGrantedAuthorities;
        return this.authorities;

    }

    @Override
    public String getPassword() {

        return this.password;
    }

    @Override
    public String getUsername()
    {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {

        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Long getId(){
        return this.id;
    }
}
