package userservice.userservice.controllers;

import org.springframework.web.bind.annotation.RestController;
import userservice.userservice.Dtos.LoginRequestDto;
import userservice.userservice.Dtos.LogoutRequestDto;
import userservice.userservice.Dtos.SignUpRequest;
import userservice.userservice.Dtos.UserDto;
import userservice.userservice.models.Token;
import userservice.userservice.models.User;
import userservice.userservice.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
//import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userservice;
    
    
    public UserController( UserService userService){
        this.userservice=userService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto request){
        return userservice.login(request.getEmail(), request.getPassword());
    }

    @PostMapping("/signup")
    public UserDto signup(@RequestBody SignUpRequest signUpRequest){
        User u= userservice.signup(signUpRequest.getName(), signUpRequest.getEmail(), signUpRequest.getPassword());
        return UserDto.from(u);
    }

    @PostMapping("/validateToken/{token}")
    public UserDto validateToken(@PathVariable("token") @NonNull String token){
        return UserDto.from(userservice.validateToken(token));
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request){
        userservice.logout(request.getToken());
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
