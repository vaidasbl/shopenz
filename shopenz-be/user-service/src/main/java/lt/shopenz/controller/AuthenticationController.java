package lt.shopenz.controller;

import static lt.shopenz.JwtUtils.generateJwt;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lt.shopenz.dto.UserLoginDto;
import lt.shopenz.dto.UserRegisterDto;
import lt.shopenz.model.User;
import lt.shopenz.service.UserService;

@RestController
@RequestMapping("/api/users")
public class AuthenticationController
{
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationController(final UserService userService, final BCryptPasswordEncoder passwordEncoder)
    {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto)
    {
        Optional<User> optionalUser = userService.getUserByEmail(userLoginDto.getEmail());

        if (optionalUser.isPresent() && passwordEncoder.matches(userLoginDto.getPassword(), optionalUser.get().getPassword()))
        {
            User user = optionalUser.get();
            String token = generateJwt(user.getEmail(), user.getRole().name(), user.getId());

            return ResponseEntity.ok(new JwtResponse(token));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid email or password");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDto userRegisterDto)
    {
        try
        {
            userService.registerUser(userRegisterDto);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch (IllegalStateException e)
        {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Getter
    @AllArgsConstructor
    private static class JwtResponse
    {
        private String token;
    }

}


