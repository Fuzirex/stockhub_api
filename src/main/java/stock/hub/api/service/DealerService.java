package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import stock.hub.api.model.dto.response.DealerResponseDTO;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.repository.DealerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DealerService {

    private final DealerRepository dealerRepository;

    public DealerResponseDTO getDealerDTOByCNPJ(String cnpj) {
        Optional<Dealer> dealerOpt = dealerRepository.findById(cnpj);
        return dealerOpt.map(DealerResponseDTO::new).orElse(null);
    }

    public Dealer getDealerByCNPJ(String cnpj) {
        return dealerRepository.findById(cnpj).orElse(null);
    }

    public List<DealerResponseDTO> getDealersToTransfer() {
        List<Dealer> dealers = dealerRepository.findAll(Example.of(Dealer.builder().active(true).build()));
        return CollectionUtils.isNotEmpty(dealers) ? dealers.stream().map(DealerResponseDTO::new).collect(Collectors.toList()) : List.of();
    }

    public boolean existsByCNPJ(String cnpj) {
        return dealerRepository.existsById(cnpj);
    }
}
