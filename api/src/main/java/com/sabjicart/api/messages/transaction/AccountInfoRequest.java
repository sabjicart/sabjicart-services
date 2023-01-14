
package com.sabjicart.api.messages.transaction;

import com.sabjicart.api.messages.inoutbound.Request;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountInfoRequest extends Request
{
    List<AccountPojo> accountPojos;
}
