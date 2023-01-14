
package com.sabjicart.api.messages.metadata.cart;

import com.sabjicart.api.messages.inoutbound.Response;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubstationCartResponse extends Response
{
    List<CartPlateInfo> cartPlateInfoList;
}
