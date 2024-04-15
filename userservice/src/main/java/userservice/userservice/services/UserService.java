package userservice.userservice.services;

import userservice.userservice.models.Token;
import userservice.userservice.models.User;

public interface UserService {
    public User signup(String fullname,String email,String password);

    public Token login(String Email,String password);
    
    public void logout(String token);

    public User validateToken(String token);

}
