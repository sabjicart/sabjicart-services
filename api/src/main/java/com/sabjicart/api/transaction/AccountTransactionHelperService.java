
package com.sabjicart.api.transaction;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.ledger.LedgerAccountInfo;
import com.sabjicart.api.messages.ledger.LedgerAccountPojo;
import com.sabjicart.api.messages.transaction.AccountInfo;
import com.sabjicart.api.messages.transaction.AccountPojo;

import java.util.List;

public interface AccountTransactionHelperService
{
    /**
     * interface to convert List of AccountPojo to List of LedgerAccountPojo
     * @param accountPojos
     * @return
     * @throws ServiceException
     */
    List<LedgerAccountPojo> ledgerAccountPojoConverter (List<AccountPojo> accountPojos)
        throws ServiceException;

    /**
     * interface to convert List of LedgerAccountInfo to List of AccountInfo
     * @param ledgerAccountInfos
     * @return
     * @throws ServiceException
     */
    List<AccountInfo> accountInfoConverter (List<LedgerAccountInfo> ledgerAccountInfos)
        throws ServiceException;
}
