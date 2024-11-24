package stock.hub.api.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.hub.api.model.dto.report.BaseReportDTO;
import stock.hub.api.model.dto.request.ReportStockRequestDTO;
import stock.hub.api.model.dto.request.StockRequestDTO;
import stock.hub.api.model.dto.response.StockResponseDTO;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.model.entity.Stock;
import stock.hub.api.repository.StockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public Page<StockResponseDTO> findDealerStock(StockRequestDTO dto) {
        return stockRepository.findDealerStockByFilter(
                dto.getDealerCNPJ(),
                StringUtils.defaultIfBlank(dto.getProductType(), null),
                StringUtils.defaultIfBlank(dto.getModel(), null),
                StringUtils.defaultIfBlank(dto.getItemCode(), null),
                StringUtils.defaultIfBlank(dto.getCommercialSeries(), null),
                StringUtils.defaultIfBlank(dto.getChassisNumber(), null),
                PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("chassisNumber")));
    }

    public Stock findByChassisNumberAndDealerCNPJ(String chassisNumber, String dealerCNPJ) {
        return stockRepository.findOne(Example.of(
                Stock.builder()
                        .chassisNumber(chassisNumber)
                        .dealer(Dealer.builder().cnpj(dealerCNPJ).build())
                        .build()
        )).orElse(null);
    }

    public List<Stock> saveStockList(List<Stock> stockList) {
        return stockRepository.saveAll(stockList);
    }

    public List<BaseReportDTO> findStockReport(ReportStockRequestDTO dto) {
        return stockRepository.findStockReport(dto.getDealerCNPJ(), dto.getProductType(), dto.getModel(), dto.getItemCode(), dto.getCommercialSeries(), dto.getChassisNumber());
    }
}
