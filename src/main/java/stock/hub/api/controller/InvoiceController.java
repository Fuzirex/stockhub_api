package stock.hub.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import stock.hub.api.configuration.logs.LogExecutionTime;
import stock.hub.api.model.dto.request.InvoiceEntryRequestDTO;
import stock.hub.api.model.dto.request.InvoiceHistoryRequestDTO;
import stock.hub.api.model.dto.request.UndoInvoiceRequestDTO;
import stock.hub.api.model.dto.response.InvoiceHistoryResponseDTO;
import stock.hub.api.model.dto.response.InvoiceOperationTypeResponseDTO;
import stock.hub.api.service.InvoiceEntryService;
import stock.hub.api.service.InvoiceService;
import stock.hub.api.service.UndoInvoiceService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/invoice")
public class InvoiceController extends BaseController {

    private final InvoiceService invoiceService;
    private final InvoiceEntryService invoiceEntryService;
    private final UndoInvoiceService undoInvoiceService;

    @LogExecutionTime
    @PostMapping("/entry")
    public ResponseEntity postInvoiceEntry(@RequestBody @Valid InvoiceEntryRequestDTO dto) {
        invoiceEntryService.processInvoiceEntry(dto);
        return ok();
    }

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

    @LogExecutionTime
    @PostMapping("/undo")
    public ResponseEntity undoOperation(@RequestBody @Valid UndoInvoiceRequestDTO dto) {
        undoInvoiceService.undoInvoiceOperation(dto);
        return ok();
    }

}
