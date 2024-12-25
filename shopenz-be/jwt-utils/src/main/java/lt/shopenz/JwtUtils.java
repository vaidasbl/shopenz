package lt.shopenz;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

public class JwtUtils
{
    protected static final String SECRET_KEY = "ilikevoyageilikevoyagewetraveltogethermeandmysackvoyagesohightothemountainsohsohightothemountainsahahahahahaha123";

    public static String generateJwt(String email, String role, Long userId)
    {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .claim("id", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 300000 * 10)) // 50 min expiration
                .signWith(key)
                .compact();
    }

    public static String getJwtFromRequest(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }

        return null;
    }

    protected static boolean validateJwtToken(String authToken)
    {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

        try
        {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authToken);

            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public static Long getUserIdFromRequest(HttpServletRequest request)
    {
        String token = getJwtFromRequest(request);

        return getUserIdFromJwt(token);
    }

    private static Long getUserIdFromJwt(String token)
    {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("id", Long.class);
    }

}