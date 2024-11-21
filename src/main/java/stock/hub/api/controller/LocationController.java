package stock.hub.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import stock.hub.api.configuration.logs.LogExecutionTime;
import stock.hub.api.model.dto.response.CityResponseDTO;
import stock.hub.api.model.dto.response.CountryResponseDTO;
import stock.hub.api.model.dto.response.StateResponseDTO;
import stock.hub.api.service.LocationService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/location")
public class LocationController extends BaseController {

    private final LocationService locationService;

    @LogExecutionTime
    @GetMapping("/countries")
    public ResponseEntity<List<CountryResponseDTO>> getCountries() {
        return ok(locationService.findAvailableCountries());
    }

    @LogExecutionTime
    @GetMapping("/states")
    public ResponseEntity<List<StateResponseDTO>> getStates() {
        return ok(locationService.findAvailableStates());
    }

    @LogExecutionTime
    @GetMapping("/cities-by-state/{state}")
    public ResponseEntity<List<CityResponseDTO>> getCitiesByState(@PathVariable Long state) {
        return ok(locationService.getCitiesByState(state));
    }

}
