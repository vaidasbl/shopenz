package lt.shopenz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegisterDto
{
    private String email;
    private String password;
    private String repeatedPassword;

    public boolean isPasswordsMatching()
    {
        return password.equals(repeatedPassword);
    }
}
