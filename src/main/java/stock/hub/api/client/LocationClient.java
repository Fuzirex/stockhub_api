package stock.hub.api.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import stock.hub.api.model.dto.response.CityResponseDTO;
import stock.hub.api.model.dto.response.CountryResponseDTO;
import stock.hub.api.model.dto.response.StateResponseDTO;
import stock.hub.api.util.RestTemplateUtil;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LocationClient {

    @Value("${app.location.base-url}")
    private String baseUrl;

    @Value("${app.location.paths.getCountries}")
    private String getCountriesPath;
    @Value("${app.location.paths.getStates}")
    private String getStatesPath;
    @Value("${app.location.paths.getCities}")
    private String getCitiesPath;

    @Value("${app.location.paths.getCountryByID}")
    private String getCountryByIdPath;
    @Value("${app.location.paths.getStateByID}")
    private String getStateByIdPath;
    @Value("${app.location.paths.getCityByID}")
    private String getCityByIdPath;

    private final RestTemplateUtil restTemplateUtil;

    public List<CountryResponseDTO> getAllCountries() {
        String url = baseUrl + getCountriesPath;
        ResponseEntity<List<CountryResponseDTO>> exchange = restTemplateUtil.get(url, new ParameterizedTypeReference<>() {});
        return exchange.getBody();
    }

    public List<StateResponseDTO> getAllStates() {
        String url = baseUrl + getStatesPath;
        ResponseEntity<List<StateResponseDTO>> exchange = restTemplateUtil.get(url, new ParameterizedTypeReference<>() {});
        return exchange.getBody();
    }

    public List<CityResponseDTO> getCitiesByState(Long state) {
        String url = baseUrl + getCitiesPath.replace("{state}", String.valueOf(state));
        ResponseEntity<List<CityResponseDTO>> exchange = restTemplateUtil.get(url, new ParameterizedTypeReference<>() {});
        return exchange.getBody();
    }

    public List<CountryResponseDTO> getCountryByID(Long id) {
        String url = baseUrl + getCountryByIdPath.replace("{id}", String.valueOf(id));
        ResponseEntity<List<CountryResponseDTO>> exchange = restTemplateUtil.get(url, new ParameterizedTypeReference<>() {});
        return exchange.getBody();
    }

    public StateResponseDTO getStateByID(Long id) {
        String url = baseUrl + getStateByIdPath.replace("{id}", String.valueOf(id));
        ResponseEntity<StateResponseDTO> exchange = restTemplateUtil.get(url, new ParameterizedTypeReference<>() {});
        return exchange.getBody();
    }

    public CityResponseDTO getCityByID(Long id) {
        String url = baseUrl + getCityByIdPath.replace("{id}", String.valueOf(id));
        ResponseEntity<CityResponseDTO> exchange = restTemplateUtil.get(url, new ParameterizedTypeReference<>() {});
        return exchange.getBody();
    }

}
