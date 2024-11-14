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
import stock.hub.api.configuration.logs.LogExecutionTime;
import stock.hub.api.model.dto.request.LoginRequestDTO;
import stock.hub.api.model.dto.response.LoginResponseDTO;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.model.exception.StockHubException;
import stock.hub.api.model.type.ExceptionType;
import stock.hub.api.service.DealerService;
import stock.hub.api.service.TokenService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authentication")
public class AuthenticationController extends BaseController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final DealerService dealerService;

    @LogExecutionTime
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        if (!dealerService.existsByCNPJ(dto.getCnpj()))
            throw new StockHubException(ExceptionType.INVALID_USER_OR_PASSWORD);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getCnpj(), dto.getPassword()));

        String tokenJwt = tokenService.generateToken((Dealer) authentication.getPrincipal());

        return ok(new LoginResponseDTO(tokenJwt));
    }

}
