package com.sabjicart.api.messages.metadata.cart;

import com.sabjicart.api.model.Address;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressInfo
{
    String addressLine1;
    String addressLine2;
    String city;
    String state;
    String country;
    int postCode;

    public AddressInfo (Address address)
    {
        this.addressLine1 = address.getAddressLine1();
        this.addressLine2 = address.getAddressLine2();
        this.city = address.getCity();
        this.state = address.getState();
        this.country = address.getCountry();
        this.postCode = address.getPostcode();
    }
}
