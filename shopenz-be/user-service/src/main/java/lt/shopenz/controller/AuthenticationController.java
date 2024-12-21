package lt.shopenz.controller;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lt.shopenz.dto.UserLoginDto;
import lt.shopenz.dto.UserRegisterDto;
import lt.shopenz.model.User;
import lt.shopenz.service.UserService;

@RestController
@RequestMapping("/api/user")
public class AuthenticationController
{
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final String SECRET_KEY = "ilikevoyageilikevoyagewetraveltogethermeandmysackvoyagesohightothemountainsohsohightothemountainsahahahahahaha123";

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
            String token = generateJwtToken(optionalUser.get());

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

    private String generateJwtToken(User user)
    {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 300000)) // 5 min expiration
                .signWith(key)
                .compact();
    }

    @Getter
    @AllArgsConstructor
    private static class JwtResponse
    {
        private String token;
    }

}


