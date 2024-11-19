package stock.hub.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.hub.api.configuration.logs.LogExecutionTime;
import stock.hub.api.model.dto.response.ProductTypeResponseDTO;
import stock.hub.api.service.ProductTypeService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product-type")
public class ProductTypeController extends BaseController {

    private final ProductTypeService productTypeService;

    @LogExecutionTime
    @GetMapping
    public ResponseEntity<List<ProductTypeResponseDTO>> getAllProductType() {
        return ok(productTypeService.findAllProductTypes());
    }

}
