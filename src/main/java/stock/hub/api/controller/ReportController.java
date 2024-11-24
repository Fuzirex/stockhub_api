package stock.hub.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.hub.api.configuration.logs.LogExecutionTime;
import stock.hub.api.model.dto.request.ReportInvoiceHistoryRequestDTO;
import stock.hub.api.model.dto.request.ReportStockRequestDTO;
import stock.hub.api.service.ContextService;
import stock.hub.api.service.ReportService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@RestController
@RequestMapping("/report")
public class ReportController extends BaseController {

    private final ContextService contextService;
    private final ReportService reportService;

    @LogExecutionTime
    @PostMapping("/stock")
    public ResponseEntity<byte[]> exportStockReport(@RequestBody @Valid ReportStockRequestDTO dto) {
        String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = contextService.getMessage("report.file-name.stock");

        return okReport(reportService.exportStockReport(dto), String.format("%s_%s.xlsx", fileName, dateNow));
    }

    @LogExecutionTime
    @PostMapping("/invoice-history")
    public ResponseEntity<byte[]> exportInvoiceHistoryReport(@RequestBody @Valid ReportInvoiceHistoryRequestDTO dto) {
        String dateNow = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String fileName = contextService.getMessage("report.file-name.invoice-history");

        return okReport(reportService.exportInvoiceHistoryReport(dto), String.format("%s_%s.xlsx", fileName, dateNow));
    }

}
