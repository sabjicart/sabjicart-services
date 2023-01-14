
package com.sabjicart.api.transaction.category;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.transaction.TransactionCategoryResponse;

public interface TransactionCategoryService
{
    /**
     * Interface to get transaction categories
     * @return
     */
    TransactionCategoryResponse getTransactionCategories ()
        throws ServiceException;
}
