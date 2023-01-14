
package com.sabjicart.api.messages.transaction;

import com.sabjicart.api.messages.ledger.LedgerAccountInfo;
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
public class AccountInfo
{
    String refId;
    String comment;
    double amount;
    @Enumerated(EnumType.STRING)
    TransactionType type;
    String creator;
    LocalDate date;
    @Enumerated(EnumType.STRING)
    Currency currency;
    String category;

    public AccountInfo (LedgerAccountInfo ledgerAccountInfo)
    {
        this.category = ledgerAccountInfo.getCategory();
        this.refId = this.category+"-"+ledgerAccountInfo.getRefId();
        this.comment = ledgerAccountInfo.getComment();
        this.amount = ledgerAccountInfo.getAmount();
        this.type = ledgerAccountInfo.getTransactionType();
        this.creator = ledgerAccountInfo.getCreator();
        this.date = ledgerAccountInfo.getDate();
        this.currency = ledgerAccountInfo.getCurrency();
    }
}
