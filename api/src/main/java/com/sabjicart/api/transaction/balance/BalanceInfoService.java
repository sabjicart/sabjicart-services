
package com.sabjicart.api.transaction.balance;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.transaction.BalanceResponse;

public interface BalanceInfoService
{
    /**
     * Interface to get current balance info
     * @return
     * @throws ServiceException
     */
    BalanceResponse getCurrentBalance () throws ServiceException;
}
