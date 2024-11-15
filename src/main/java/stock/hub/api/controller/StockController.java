package stock.hub.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stock.hub.api.configuration.logs.LogExecutionTime;
import stock.hub.api.model.dto.request.StockRequestDTO;
import stock.hub.api.model.dto.response.StockResponseDTO;
import stock.hub.api.service.StockService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stock")
public class StockController extends BaseController {

    private final StockService stockService;

    @LogExecutionTime
    @PostMapping
    public ResponseEntity<Page<StockResponseDTO>> getDealerStock(@RequestBody @Valid StockRequestDTO dto) {
        return ok(stockService.findDealerStock(dto));
    }

}
