package stock.hub.api.configuration.database;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareConfiguration implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("STOCKHUB_API");
    }

}
