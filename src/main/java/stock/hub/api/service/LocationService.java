package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import stock.hub.api.client.LocationClient;
import stock.hub.api.model.dto.response.CityResponseDTO;
import stock.hub.api.model.dto.response.CountryResponseDTO;
import stock.hub.api.model.dto.response.StateResponseDTO;

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

    public CountryResponseDTO getCountryByID(Long id) {
        List<CountryResponseDTO> result = locationClient.getCountryByID(id);
        return CollectionUtils.isNotEmpty(result) ? result.getFirst() : null;
    }

    public StateResponseDTO getStateByID(Long id) {
        return locationClient.getStateByID(id);
    }

    public CityResponseDTO getCityByID(Long id) {
        return locationClient.getCityByID(id);
    }
}
