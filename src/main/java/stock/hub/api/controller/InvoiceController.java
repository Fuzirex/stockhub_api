package stock.hub.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stock.hub.api.configuration.logs.LogExecutionTime;
import stock.hub.api.model.dto.request.InvoiceHistoryRequestDTO;
import stock.hub.api.model.dto.response.InvoiceHistoryResponseDTO;
import stock.hub.api.model.dto.response.InvoiceOperationTypeResponseDTO;
import stock.hub.api.service.InvoiceService;

import java.util.List;

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

    @LogExecutionTime
    @GetMapping("/operations")
    public ResponseEntity<List<InvoiceOperationTypeResponseDTO>> getInvoiceOperationTypes() {
        return ok(invoiceService.getInvoiceOperationTypes());
    }

}
