package userservice.userservice.repositories;
import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import userservice.userservice.models.Token;
import userservice.userservice.models.User;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {
    Token save(Token token);

    Optional<Token> findByValueAndDeletedEquals(String value,boolean deleted);

    Optional<Token> findByValueAndDeletedAndExpireAtGreaterThan(String value, boolean deleted , Date ExpireAt);
    
}
