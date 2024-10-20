package stock.hub.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.hub.api.model.dto.LoginDTO;
import stock.hub.api.model.dto.TokenJwtDTO;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.service.TokenService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenJwtDTO> login(@RequestBody @Valid LoginDTO dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword()));

        String tokenJwt = tokenService.generateToken((Dealer) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJwtDTO(tokenJwt));
    }

}
