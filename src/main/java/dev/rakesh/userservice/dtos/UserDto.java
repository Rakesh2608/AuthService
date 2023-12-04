package dev.rakesh.userservice.dtos;

import dev.rakesh.userservice.models.Role;
import dev.rakesh.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String email;
    private List<Role> roles;

    public static UserDto from(User user) {
        UserDto userDto=new UserDto();
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());

        return userDto;
    }

}
