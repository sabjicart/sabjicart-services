
package com.sabjicart.core.cart.transaction.info;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.Response;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.ledger.LedgerAccountInfo;
import com.sabjicart.api.messages.ledger.LedgerAccountPojo;
import com.sabjicart.api.messages.transaction.AccountInfo;
import com.sabjicart.api.messages.transaction.AccountInfoRequest;
import com.sabjicart.api.messages.transaction.AccountInfoResponse;
import com.sabjicart.api.transaction.AccountTransactionHelperService;
import com.sabjicart.api.transaction.info.AccountInfoService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService
{
    AccountTransactionHelperService accountTransactionHelperService;

    @Override
    public Response updateTransactionInfo (AccountInfoRequest accountInfoRequest)
        throws ServiceException
    {
        Response response = new Response();
        List<LedgerAccountPojo> ledgerAccountPojos;
        try {
            ledgerAccountPojos =
                accountTransactionHelperService.ledgerAccountPojoConverter(
                    accountInfoRequest.getAccountPojos());
            //TODO:Call ledger service to update transaction info
            // LedgerService ledgerService = new LedgerService();
            // ledgerService.updateTransactionInfo(ledgerAccountPojos);
            //to throw exception
            if (!response.getStatus().equals("Success")) {
                throw new ServiceException(
                    "Error while updating transaction info");
            }
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error("Error updating transaction info:{}", e.getMessage());
            throw new ServiceException("Error in updateTransactionInfo");
        }
        ResponseUtil.success(response);
        return response;
    }

    @Override
    public AccountInfoResponse getBankStatement (
        LocalDate fromDate, LocalDate toDate, int page, int fetchSize)
        throws ServiceException
    {
        AccountInfoResponse response = new AccountInfoResponse();
        List<AccountInfo> accountInfos;
        List<LedgerAccountInfo> ledgerAccountInfos = new ArrayList<>();
        try {
            //TODO:Call ledger service to get bank statement
            // LedgerService ledgerService = new LedgerService();
            // ledgerAccountInfos=ledgerService.getTransactions( LocalDate fromDate, LocalDate toDate, int page, int fetchSize);
            accountInfos = accountTransactionHelperService.accountInfoConverter(
                ledgerAccountInfos);
            response.setAccountInfos(accountInfos);
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error("Error updating transaction info:{}", e.getMessage());
            throw new ServiceException("Error in updateTransactionInfo");
        }
        ResponseUtil.success(response);
        return response;
    }
}
