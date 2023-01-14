
package com.sabjicart.core.cart.transaction.category;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.transaction.TransactionCategoryPojo;
import com.sabjicart.api.messages.transaction.TransactionCategoryResponse;
import com.sabjicart.api.model.TransactionCategory;
import com.sabjicart.api.transaction.category.TransactionCategoryService;
import com.sabjicart.core.cart.repository.TransactionCategoryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
@Slf4j
@AllArgsConstructor
public class TransactionCategoryServiceImpl
    implements TransactionCategoryService
{
    TransactionCategoryRepository transactionCategoryRepository;

    @Override
    public TransactionCategoryResponse getTransactionCategories ()
        throws ServiceException
    {
        TransactionCategoryResponse response =
            new TransactionCategoryResponse();
        List<TransactionCategoryPojo> transactionCategoryPojos;
        try {
            List<TransactionCategory> transactionCategories =
                transactionCategoryRepository.findAll();
            transactionCategoryPojos =
                TransactionCategory.transactionCategoryPojosInflater(
                    transactionCategories);
            response.setTransactionCategoryPojos(transactionCategoryPojos);
        }
        catch (Exception e) {
            log.error("Error getting transaction categories");
            throw new ServiceException("Error getting transaction categories");
        }
        return response;
    }
}
