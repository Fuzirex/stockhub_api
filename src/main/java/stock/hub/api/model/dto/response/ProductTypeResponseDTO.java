package stock.hub.api.model.dto.response;

import lombok.*;
import org.springframework.context.i18n.LocaleContextHolder;
import stock.hub.api.model.entity.ProductType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductTypeResponseDTO {

    private String id;
    private String description;

    public ProductTypeResponseDTO(ProductType productType) {
        this.id = productType.getType();
        String lang = LocaleContextHolder.getLocale().getLanguage();

        switch (lang) {
            case "pt" -> this.description = productType.getTranslationPT();
            case "es" -> this.description = productType.getTranslationES();
            default -> this.description = productType.getTranslationEN();
        };
    }
}
