package stock.hub.api.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import stock.hub.api.repository.DealerRepository;
import stock.hub.api.service.TokenService;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class SecurityFilterConfiguration extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final DealerRepository dealerRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenJwt = extractToken(request);

        if (StringUtils.isNotBlank(tokenJwt)) {
            String subject = tokenService.validateAndGetSubject(tokenJwt);
            UserDetails user = dealerRepository.findById(subject).orElse(null);

            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNotBlank(authorization))
            return StringUtils.replace(authorization, "Bearer ", "");

        return null;
    }

}
