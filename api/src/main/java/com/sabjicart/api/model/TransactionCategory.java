
package com.sabjicart.api.model;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.transaction.TransactionCategoryPojo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionCategory extends AbstractBaseEntity
{
    String categoryName;
    String description;
    String transactionType;

    public static List<TransactionCategoryPojo> transactionCategoryPojosInflater (
        List<TransactionCategory> transactionCategories) throws ServiceException
    {
        List<TransactionCategoryPojo> transactionCategoryPojos =
            new ArrayList<>();

        for (TransactionCategory transactionCategory : transactionCategories) {
            try {
                transactionCategoryPojos.add(new TransactionCategoryPojo(
                    transactionCategory));
            }
            catch (Exception e) {
                log.error(
                    "Error inflating transaction category pojos for transaction category: {} "
                        + transactionCategory.getCategoryName());
                throw new ServiceException(
                    "Error inflating transaction category pojos");
            }
        }
        return transactionCategoryPojos;
    }
}

