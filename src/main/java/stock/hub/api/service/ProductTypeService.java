package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import stock.hub.api.model.dto.response.ProductTypeResponseDTO;
import stock.hub.api.model.entity.ProductType;
import stock.hub.api.repository.ProductTypeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTypeService {

    private final ProductTypeRepository productTypeRepository;

    public List<ProductTypeResponseDTO> findAllProductTypes() {
        List<ProductType> all = productTypeRepository.findAll();

        List<ProductTypeResponseDTO> returnList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(all))
            returnList.addAll(all.stream().map(ProductTypeResponseDTO::new).toList());

        return returnList;
    }

}
