package stock.hub.api.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.message.StringMapMessage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import stock.hub.api.service.ContextService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Log4j2
@Component
@RequiredArgsConstructor
public class WorkbookTemplateUtils {

    private final ContextService contextService;

    public byte[] getReportByteArray(SXSSFWorkbook workbook) {
        try {
            if (workbook != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                workbook.write(stream);
                workbook.close();
                return stream.toByteArray();
            }
        } catch (IOException ex) {
            log.error(new StringMapMessage()
                    .with("message", "Error on writing report workbook")
                    .with("error_message", ex.getMessage())
                    .with("error_cause", ex.getCause()), ex);
        }

        return null;
    }

    public SXSSFWorkbook getStockReportWorkbook() {
        String templateFilePath = "reports/stock/StockReport.xlsx";
        return getWorkbook(templateFilePath);
    }

    public SXSSFWorkbook getInvoiceHistoryReportWorkbook() {
        String templateFilePath = "reports/invoice/InvoiceHistoryReport.xlsx";
        return getWorkbook(templateFilePath);
    }

    private SXSSFWorkbook getWorkbook(String templateFilePath) {
        try {
            InputStream fileInputStream = Objects.requireNonNull(this.getClass().getClassLoader().getResource(templateFilePath)).openStream();
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
            translateWorkbookHeaders(xssfWorkbook);

            return new SXSSFWorkbook(xssfWorkbook);
        } catch (IOException | NullPointerException ex) {
            log.error(new StringMapMessage()
                    .with("message", String.format("Error on mounting workbook from %s", templateFilePath))
                    .with("error_message", ex.getMessage())
                    .with("error_cause", ex.getCause()), ex);

            return null;
        }
    }

    // Need to translate before creating the SXSSFWorkbook, because SXSSFWorkbook is a streaming version of XSSFWorkbook, and "lazy load" the headers
    private void translateWorkbookHeaders(XSSFWorkbook workbook) {
        Sheet sheet = workbook.getSheetAt(0);
        List<Row> headers = Arrays.asList(sheet.getRow(0), sheet.getRow(1));

        headers.forEach(row -> {
            row.forEach(cell -> {
                String headerValue = cell.getStringCellValue();

                if (StringUtils.isNotBlank(headerValue))
                    cell.setCellValue(contextService.getMessage(headerValue));
            });
        });
    }

}
