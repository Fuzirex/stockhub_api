package stock.hub.api.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductTypeUtils {

    public static String getProductTypeTranslation(String productTypeTranslationPT, String productTypeTranslationEN, String productTypeTranslationES) {
        String lang = LocaleContextHolder.getLocale().getLanguage();

        return switch (lang) {
            case "pt" -> productTypeTranslationPT;
            case "en" -> productTypeTranslationEN;
            case "es" -> productTypeTranslationES;
            default -> null;
        };
    }

}
