package userservice.userservice.Dtos;

import java.util.List;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;
import userservice.userservice.models.Role;
import userservice.userservice.models.User;

@Getter
@Setter
public class UserDto {
    private String name;
    private String email;
    //private String hashpassword;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private boolean is_email_verified;

    public static UserDto from(User user){
        if (user==null){
            return null;
        }
        UserDto userdto= new UserDto();
        userdto.email=user.getEmail();
        userdto.name=user.getName();
        userdto.roles=user.getRoles();
        userdto.is_email_verified=user.is_email_verified();
        return userdto;
    }
}
