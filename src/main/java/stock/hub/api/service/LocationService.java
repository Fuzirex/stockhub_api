package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import stock.hub.api.client.LocationClient;
import stock.hub.api.model.dto.request.InvoiceHistoryRequestDTO;
import stock.hub.api.model.dto.response.*;
import stock.hub.api.model.type.InvoiceOperationType;
import stock.hub.api.repository.ItemRepository;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationClient locationClient;

    public List<CountryResponseDTO> findAvailableCountries() {
        List<CountryResponseDTO> allCountries = locationClient.getAllCountries();

        // For now return just Brazil
        return allCountries.stream().filter(c -> Objects.equals(c.getCode(), 76L)).toList();
    }

    public List<StateResponseDTO> findAvailableStates() {
        return locationClient.getAllStates();
    }

    public List<CityResponseDTO> getCitiesByState(Long state) {
        return locationClient.getCitiesByState(state);
    }
}
