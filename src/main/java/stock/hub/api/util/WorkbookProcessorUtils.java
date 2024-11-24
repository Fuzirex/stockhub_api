package stock.hub.api.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;
import stock.hub.api.model.dto.report.BaseReportDTO;
import stock.hub.api.model.type.CurrencyType;
import stock.hub.api.service.ContextService;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Log4j2
@Component
@RequiredArgsConstructor
public class WorkbookProcessorUtils {

    public static final Integer DEFAULT_ROW_INDEX = 2;

    private final ContextService contextService;

    public void populateStockWorkbook(SXSSFWorkbook workbook, List<BaseReportDTO> stockReportList) {
        if (CollectionUtils.isNotEmpty(stockReportList)) {
            int rowIndex = DEFAULT_ROW_INDEX;
            Sheet sheet = getFirstSheet(workbook);
            CellStyle cellStyle = cellWithThinBorder(workbook);

            for (BaseReportDTO dto : stockReportList) {
                Row row = sheet.createRow(rowIndex);
                int columnIndex = 0;

                applyCellValue(row, columnIndex++, dto.getDealerCNPJ(), cellStyle);
                applyCellValue(row, columnIndex++, dto.getDealerName(), cellStyle);
                applyCellValue(row, columnIndex++, dto.getDealerCountry(), cellStyle);
                applyCellValue(row, columnIndex++, dto.getDealerState(), cellStyle);
                applyCellValue(row, columnIndex++, dto.getDealerCity(), cellStyle);
                applyCellValue(row, columnIndex++, dto.getProductType(), cellStyle);
                applyCellValue(row, columnIndex++, dto.getProductChassisNumber(), cellStyle);
                applyCellValue(row, columnIndex++, dto.getProductCommercialSeries(), cellStyle);
                applyCellValue(row, columnIndex++, dto.getProductModel(), cellStyle);
                applyCellValue(row, columnIndex++, dto.getProductItemCode(), cellStyle);

                rowIndex++;
            }
        }
    }

    /*public void populateInvoiceHistoryWorkbook(SXSSFWorkbook workbook, List<BaseReportDTO> invoiceHistoryReportList) {
        if (CollectionUtils.isNotEmpty(invoiceHistoryReportList)) {
            int rowIndex = DEFAULT_ROW_INDEX;
            Sheet sheet = getFirstSheet(workbook);
            CellStyle cellStyle = cellWithThinBorder(workbook);

            for (BaseReportDTO dto : invoiceHistoryReportList) {
                Row row = sheet.createRow(rowIndex);
                int columnIndex = 0;

                applyCellValue(row, columnIndex++, dto.getDealerLegalNumber(), cellStyle);

                rowIndex++;
            }
        }
    }*/

    private Sheet getFirstSheet(SXSSFWorkbook workbook) {
        return workbook.getSheetAt(0);
    }

    private CellStyle cellWithThinBorder(SXSSFWorkbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }

    private void applyCellValue(Row row, int columnIndex, String value, CellStyle style) {
        Cell cell = row.createCell(columnIndex);
        if (StringUtils.isNotBlank(value)) cell.setCellValue(value);
        if (style != null) cell.setCellStyle(style);
    }

    private String formatDate(LocalDateTime date) {
        return Objects.nonNull(date) ? date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) : "";
    }

    private String formatCurrency(BigDecimal value, CurrencyType currency) {
        if (Objects.nonNull(value) && Objects.nonNull(currency))
            return new DecimalFormat(currency.name() + " #,##0.00").format(value);

        return "";
    }
}
