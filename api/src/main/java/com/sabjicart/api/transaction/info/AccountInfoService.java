
package com.sabjicart.api.transaction.info;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.transaction.AccountInfoRequest;
import com.sabjicart.api.messages.transaction.AccountInfoResponse;

import java.time.LocalDate;

public interface AccountInfoService
{
    /**
     * interface to update transaction info
     * @param accountInfoRequest
     * @return
     * @throws ServiceException
     */
    Response updateTransactionInfo (AccountInfoRequest accountInfoRequest)
        throws ServiceException;

    /**
     * interface to fetch bank statement
     * @param fromDate
     * @param toDate
     * @return
     * @throws ServiceException
     */
    AccountInfoResponse getBankStatement (
        LocalDate fromDate,
        LocalDate toDate,
        int page,
        int fetchSize) throws ServiceException;
}
