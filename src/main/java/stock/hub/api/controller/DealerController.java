package stock.hub.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.hub.api.configuration.logs.LogExecutionTime;
import stock.hub.api.model.dto.response.DealerResponseDTO;
import stock.hub.api.model.dto.response.LoginResponseDTO;
import stock.hub.api.model.exception.StockHubException;
import stock.hub.api.model.type.ExceptionType;
import stock.hub.api.service.DealerService;
import stock.hub.api.service.TokenService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dealer")
public class DealerController extends BaseController {

    private final DealerService dealerService;

    @LogExecutionTime
    @GetMapping("/{cnpj}")
    public ResponseEntity<DealerResponseDTO> getDealerByCNPJ(@PathVariable String cnpj) {
        return ok(dealerService.getDealerByCNPJ(cnpj));
    }

    @LogExecutionTime
    @GetMapping("/to-transfer")
    public ResponseEntity<List<DealerResponseDTO>> getDealersToTransfer() {
        return ok(dealerService.getDealersToTransfer());
    }

}
