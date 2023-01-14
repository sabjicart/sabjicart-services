
package com.sabjicart.api.messages.ledger;

import com.sabjicart.api.shared.Currency;
import com.sabjicart.api.shared.TransactionType;
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
public class LedgerAccountPojo
{
    String comment;
    double amount;
    String category;
    String creator;
    @Enumerated(EnumType.STRING)
    TransactionType transactionType;
    @Enumerated(EnumType.STRING)
    Currency currency;
}
