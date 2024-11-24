package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import stock.hub.api.model.entity.Dealer;

@Service
@RequiredArgsConstructor
public class ContextService {

    private final MessageSource messageSource;

    public Dealer getCurrentDealer() {
        return (Dealer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

}
