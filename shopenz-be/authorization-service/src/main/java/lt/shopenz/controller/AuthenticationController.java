package lt.shopenz.controller;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RestController
public class AuthenticationController
{
    private static final String SECRET_KEY = "kjashdkjasdbnkqwmbewjklhdaklsdhaskldhjwkehjkalshdkjasbdnwqkhjleajskldbasbmndwqkehjasldbnasjadkalksjdklajsdkljasdjnajhfghfhrtytrksdnjaksdnaksdasndamsnd";

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
    {
        if ("admin".equals(loginRequest.getEmail()) && "admin".equals(loginRequest.getPassword()))
        {
            String token = generateJwtToken(loginRequest.getEmail());
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
    }

    private String generateJwtToken(String email)
    {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(email)
                .claim("role", "ADMIN")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(key)
                .compact();
    }

    @Getter
    @Setter
    private static class LoginRequest
    {
        private String email;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    private static class JwtResponse
    {
        private String token;

    }

}


