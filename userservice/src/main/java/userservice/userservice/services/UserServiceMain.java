package userservice.userservice.services;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import userservice.userservice.models.Token;
import userservice.userservice.models.User;
import userservice.userservice.repositories.TokenRepository;
import userservice.userservice.repositories.UserRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service("UserServiceMain")
public class UserServiceMain implements UserService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceMain(UserRepository userRepository,TokenRepository tokenRepository
    ,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.tokenRepository=tokenRepository;
        this.userRepository=userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Override
    public User signup(String fullname,String email, String password){
        Optional<User> prevUser = userRepository.findByemail(email);
        if (prevUser.isEmpty()){
            User user=new User();
            user.setEmail(email);
            user.setName(fullname);
            user.setHashpassword(password);
            User u = userRepository.save(user);
            return u;
        }
        return null;
    }

    public Token login(String email, String password){
        Optional<User> prevUser = userRepository.findByemail(email);
        if (prevUser.isEmpty()){

            return null;
        }
        User user = prevUser.get();
        if (!bCryptPasswordEncoder.matches(password,user.getHashpassword())){
            return null;
        }
        Token token = getToken(user);
        Token savedToken = tokenRepository.save(token);
        return savedToken;
    }

    public void logout(String token){
        Optional<Token> tokens = tokenRepository.findByValueAndDeletedEquals(token, false);
        if(tokens.isEmpty()){
            return;
        }
        Token tk=tokens.get();
        tk.setDeleted(true);
        tokenRepository.save(tk);
        return;
    }
    private static Token getToken(User user){
        LocalDate today = LocalDate.now();
        LocalDate ThirtydaysLater = today.plus(30,ChronoUnit.DAYS);
        Date expireAt=Date.from(
            ThirtydaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Token token = new Token();
        token.setExpireAt(expireAt);
        token.setUser(user);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));
        return token;
    }

    public User validateToken(String token){
        Optional<Token> tokens = tokenRepository.findByValueAndDeletedAndExpireAtGreaterThan(token, false, new Date());
        if (tokens.isEmpty()){
            return null;
        }
        return tokens.get().getUser();
    }
}
