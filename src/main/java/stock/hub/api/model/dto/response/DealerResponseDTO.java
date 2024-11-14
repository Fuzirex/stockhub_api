package stock.hub.api.model.dto.response;

import lombok.*;
import stock.hub.api.model.entity.Dealer;
import stock.hub.api.util.ObjectMapperUtils;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DealerResponseDTO {

    private String cnpj;
    private String name;
    private String email;
    private String mobilePhone;
    private String landlinePhone;
    private Long countryId;
    private String countryDesc;
    private Long stateId;
    private String stateDesc;
    private Long cityId;
    private String cityDesc;
    private String street;
    private Long number;
    private Boolean active;

    public DealerResponseDTO(Dealer dealer) {
        this.cnpj = dealer.getCnpj();
        this.name = dealer.getName();
        this.email = dealer.getEmail();
        this.mobilePhone = dealer.getMobilePhone();
        this.landlinePhone = dealer.getLandlinePhone();
        this.countryId = dealer.getCountryId();
        this.countryDesc = dealer.getCountryDesc();
        this.stateId = dealer.getStateId();
        this.stateDesc = dealer.getStateDesc();
        this.cityId = dealer.getCityId();
        this.cityDesc = dealer.getCityDesc();
        this.street = dealer.getStreet();
        this.number = dealer.getNumber();
        this.active = dealer.getActive();
    }

    @Override
    @SneakyThrows
    public String toString() {
        return ObjectMapperUtils.writeValueAsString(this);
    }
}
