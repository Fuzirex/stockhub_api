package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.hub.api.model.dto.request.StockRequestDTO;
import stock.hub.api.model.dto.response.StockResponseDTO;
import stock.hub.api.repository.StockRepository;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public Page<StockResponseDTO> findDealerStock(StockRequestDTO dto) {
        return stockRepository.findDealerStockByFilter(
                dto.getDealerCNPJ(),
                dto.getProductType(),
                dto.getModel(),
                dto.getItemCode(),
                dto.getCommercialSeries(),
                dto.getChassisNumber(),
                PageRequest.of(dto.getPage(), dto.getSize(), Sort.by("chassisNumber")));
    }
}
