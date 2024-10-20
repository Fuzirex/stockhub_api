package stock.hub.api.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import stock.hub.api.model.entity.Dealer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${app.auth.token-secret}")
    private String tokenSecret;

    @Value("${app.auth.issuer}")
    private String tokenIssuer;

    public String generateToken(Dealer dealer) {
        try {
            return JWT.create()
                    .withIssuer(tokenIssuer)
                    .withSubject(dealer.getCnpj())
                    .withClaim("email", dealer.getEmail())
                    .withExpiresAt(createExpirationDate())
                    .sign(Algorithm.HMAC256(tokenSecret));
        } catch (JWTCreationException e) {
            throw new RuntimeException("Could not generate token", e);
        }
    }

    public String validateAndGetSubject(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(tokenSecret))
                    .withIssuer(tokenIssuer)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException e) {
            throw new RuntimeException("JWT Token invalid or expired", e);
        }
    }

    private Instant createExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
