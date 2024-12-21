package lt.shopenz;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Collection;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter
{
    private static final String SECRET_KEY = "ilikevoyageilikevoyagewetraveltogethermeandmysackvoyagesohightothemountainsohsohightothemountainsahahahahahaha123";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        String token = getJwtFromRequest(request);

        if (token != null && validateJwtToken(token))
        {
            UsernamePasswordAuthenticationToken authentication = getAuthenticationFromToken(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpServletRequestWrapper modifiedRequest = new HttpServletRequestWrapper(request)
            {
                @Override
                public String getHeader(String name)
                {
                    if (HttpHeaders.AUTHORIZATION.equals(name))
                    {
                        return "Bearer " + token;
                    }
                    return super.getHeader(name);
                }
            };

            filterChain.doFilter(modifiedRequest, response);
        }
        else
        {
            filterChain.doFilter(request, response);
        }
    }

    private String getJwtFromRequest(HttpServletRequest request)
    {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer "))
        {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean validateJwtToken(String authToken)
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

    private UsernamePasswordAuthenticationToken getAuthenticationFromToken(String token)
    {
        Key key = new SecretKeySpec(SECRET_KEY.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String email = claims.getSubject();
        Collection<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority((String) claims.get("role")));
        return new UsernamePasswordAuthenticationToken(email, null, authorities);
    }

}
