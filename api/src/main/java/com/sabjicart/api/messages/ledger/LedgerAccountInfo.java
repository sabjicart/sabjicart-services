
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
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class LedgerAccountInfo
{
    String refId;
    String comment;
    double amount;
    @Enumerated(EnumType.STRING)
    TransactionType transactionType;
    String creator;
    LocalDate date;
    @Enumerated(EnumType.STRING)
    Currency currency;
    String category;
}
