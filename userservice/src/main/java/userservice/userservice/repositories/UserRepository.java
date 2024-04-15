package userservice.userservice.repositories;

import java.util.Optional;
//import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import userservice.userservice.models.User;


@Repository 
public interface UserRepository extends JpaRepository<User,Long> {
    
    User save(User user);

    Optional<User> findByemail(String Email);
}
