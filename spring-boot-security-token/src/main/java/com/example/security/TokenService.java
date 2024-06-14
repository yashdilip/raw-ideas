import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Autowired
    private UserDetailsService userDetailsService;

    private final Map<String, String> tokenStore = new HashMap<>();
    private final SecureRandom secureRandom = new SecureRandom();

    public String generateToken(String username) {
        String token = String.format("%08d", secureRandom.nextInt(100000000));
        tokenStore.put(token, username);
        return token;
    }

    public boolean validateToken(String token) {
        return tokenStore.containsKey(token);
    }

    public Authentication getAuthentication(String token) {
        String username = tokenStore.get(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
