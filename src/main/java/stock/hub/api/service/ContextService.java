package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import stock.hub.api.model.entity.Dealer;

@Service
@RequiredArgsConstructor
public class ContextService {

    public Dealer getCurrentDealer() {
        return (Dealer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
