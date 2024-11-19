package stock.hub.api.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import stock.hub.api.model.entity.auditable.Auditable;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DEALER")
public class Dealer extends Auditable implements UserDetails {

    @Id
    @Column(name = "CNPJ", length = 14, nullable = false)
    private String cnpj;

    @Column(name = "NAME", length = 100, nullable = false)
    private String name;

    @Column(name = "EMAIL", length = 200, nullable = false)
    private String email;

    @Column(name = "MOBILE_PHONE", length = 20)
    private String mobilePhone;

    @Column(name = "LANDLINE_PHONE", length = 20)
    private String landlinePhone;

    @Column(name = "COUNTRY_ID", nullable = false)
    private Long countryId;

    @Column(name = "COUNTRY_DESC", length = 100, nullable = false)
    private String countryDesc;

    @Column(name = "STATE_ID", nullable = false)
    private Long stateId;

    @Column(name = "STATE_DESC", length = 100, nullable = false)
    private String stateDesc;

    @Column(name = "CITY_ID", nullable = false)
    private Long cityId;

    @Column(name = "CITY_DESC", length = 100, nullable = false)
    private String cityDesc;

    @Column(name = "STREET", length = 200)
    private String street;

    @Column(name = "NUMBER")
    private Long number;

    @Column(name = "ACTIVE", nullable = false)
    private Boolean active;

    @Column(name = "PASSWORD", length = 255, nullable = false)
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return cnpj;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

}
