package dev.rakesh.userservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Session extends BaseModel{

    private String token;
    @ManyToOne
    private User user;
    private Date expiredAt;
    private SessionStatus sessionStatus;
}
