package stock.hub.api.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;
import stock.hub.api.model.dto.report.BaseReportDTO;
import stock.hub.api.model.dto.request.ReportStockRequestDTO;
import stock.hub.api.util.WorkbookProcessorUtils;
import stock.hub.api.util.WorkbookTemplateUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final StockService stockService;
    private final WorkbookTemplateUtils workbookTemplateUtils;
    private final WorkbookProcessorUtils workbookProcessorUtils;

    public byte[] exportStockReport(@Valid ReportStockRequestDTO dto) {
        List<BaseReportDTO> reportList = stockService.findStockReport(dto);

        SXSSFWorkbook workbook = workbookTemplateUtils.getStockReportWorkbook();
        workbookProcessorUtils.populateStockWorkbook(workbook, reportList);

        return workbookTemplateUtils.getReportByteArray(workbook);
    }
}
