package dev.rakesh.userservice.repositories;

import dev.rakesh.userservice.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session,Long> {
     Session findByTokenAndUser_Id(String token,Long Id);
}
