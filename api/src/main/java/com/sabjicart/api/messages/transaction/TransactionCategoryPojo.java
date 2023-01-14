
package com.sabjicart.api.messages.transaction;

import com.sabjicart.api.model.TransactionCategory;
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
public class TransactionCategoryPojo
{
    String categoryName;
    String description;
    String transactionType;

    public TransactionCategoryPojo (TransactionCategory transactionCategory)
    {
        this.categoryName = transactionCategory.getCategoryName();
        this.description = transactionCategory.getDescription();
        this.transactionType = transactionCategory.getTransactionType();
    }

}
