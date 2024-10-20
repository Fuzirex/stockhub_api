package stock.hub.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stock.hub.api.repository.DealerRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {

    private final DealerRepository dealerRepository;

    @Override
    public UserDetails loadUserByUsername(String cnpj) throws UsernameNotFoundException {
        return dealerRepository.findById(cnpj).orElse(null);
    }

}
