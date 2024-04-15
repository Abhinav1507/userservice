package userservice.userservice.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseModel {
    private String name;
    private String email;
    private String hashpassword;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private boolean is_email_verified;
}
