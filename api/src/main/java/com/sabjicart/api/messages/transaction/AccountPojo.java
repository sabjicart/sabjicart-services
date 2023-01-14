
package com.sabjicart.api.messages.transaction;

import com.sabjicart.api.shared.Currency;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountPojo
{
    String comment;
    double amount;
    String category;
    String creator;
    @Enumerated(EnumType.STRING)
    Currency currency;
}
