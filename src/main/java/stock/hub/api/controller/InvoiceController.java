package stock.hub.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.hub.api.configuration.logs.LogExecutionTime;
import stock.hub.api.model.dto.request.InvoiceHistoryRequestDTO;
import stock.hub.api.model.dto.response.InvoiceHistoryResponseDTO;
import stock.hub.api.service.InvoiceService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/invoice")
public class InvoiceController extends BaseController {

    private final InvoiceService invoiceService;

    @LogExecutionTime
    @PostMapping("/history")
    public ResponseEntity<Page<InvoiceHistoryResponseDTO>> getInvoiceHistory(@RequestBody @Valid InvoiceHistoryRequestDTO dto) {
        return ok(invoiceService.findInvoiceHistory(dto));
    }

}
