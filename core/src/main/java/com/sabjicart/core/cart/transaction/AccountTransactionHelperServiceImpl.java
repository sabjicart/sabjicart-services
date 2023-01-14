
package com.sabjicart.core.cart.transaction;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.ledger.LedgerAccountInfo;
import com.sabjicart.api.messages.ledger.LedgerAccountPojo;
import com.sabjicart.api.messages.transaction.AccountInfo;
import com.sabjicart.api.messages.transaction.AccountPojo;
import com.sabjicart.api.shared.TransactionType;
import com.sabjicart.api.transaction.AccountTransactionHelperService;
import com.sabjicart.core.cart.repository.TransactionCategoryRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
@Slf4j
public class AccountTransactionHelperServiceImpl
    implements AccountTransactionHelperService
{
    TransactionCategoryRepository transactionCategoryRepository;

    @Override
    public List<LedgerAccountPojo> ledgerAccountPojoConverter (List<AccountPojo> accountPojos)
        throws ServiceException
    {
        List<LedgerAccountPojo> ledgerAccountPojos = new ArrayList<>();
        LedgerAccountPojo ledgerAccountPojo;
        for (AccountPojo accountPojo : accountPojos) {
            try {
                String category = accountPojo.getCategory();
                //maping category to transaction type for ledger
                TransactionType transactionType =
                    TransactionType.valueOf(transactionCategoryRepository.findByCategoryName(
                        category).getTransactionType());
                ledgerAccountPojo = LedgerAccountPojo.builder()
                                                     .category(accountPojo.getCategory())
                                                     .comment(accountPojo.getComment())
                                                     .creator(accountPojo.getCreator())
                                                     .currency(accountPojo.getCurrency())
                                                     .amount(accountPojo.getAmount())
                                                     .transactionType(
                                                         transactionType)
                                                     .build();
                ledgerAccountPojos.add(ledgerAccountPojo);
            }
            catch (Exception e) {
                log.error(
                    "Error converting ledger account pojo from account pojo for category: {} comment: {} creator: {} currency: {} amount: {}",
                    accountPojo.getCategory(),
                    accountPojo.getComment(),
                    accountPojo.getCreator(),
                    accountPojo.getCurrency(),
                    accountPojo.getAmount()
                );
                throw new ServiceException("Error updating transaction");
            }
        }
        return ledgerAccountPojos;
    }

    @Override
    public List<AccountInfo> accountInfoConverter (List<LedgerAccountInfo> ledgerAccountInfos)
        throws ServiceException
    {
        List<AccountInfo> accountInfos = new ArrayList<>();
        for (LedgerAccountInfo ledgerAccountInfo : ledgerAccountInfos) {
            try {
                accountInfos.add(new AccountInfo(ledgerAccountInfo));
            }
            catch (Exception e) {
                log.error(
                    "Error converting account info from ledger account info for refId: {}",
                    ledgerAccountInfo.getRefId()
                );
                throw new ServiceException("Error fetching transaction");
            }
        }
        return accountInfos;
    }
}
