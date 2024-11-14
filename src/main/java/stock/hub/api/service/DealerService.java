package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stock.hub.api.model.dto.response.DealerResponseDTO;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.repository.DealerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DealerService {

    private final DealerRepository dealerRepository;

    public DealerResponseDTO getDealerByCNPJ(String cnpj) {
        Optional<Dealer> dealerOpt = dealerRepository.findById(cnpj);
        return dealerOpt.map(DealerResponseDTO::new).orElse(null);
    }

    public boolean existsByCNPJ(String cnpj) {
        return dealerRepository.existsById(cnpj);
    }
}
