
package com.sabjicart.core.cart.transaction.balance;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.inoutbound.ResponseUtil;
import com.sabjicart.api.messages.transaction.BalanceResponse;
import com.sabjicart.api.model.Balance;
import com.sabjicart.api.transaction.balance.BalanceInfoService;
import com.sabjicart.core.cart.repository.BalanceRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
@Slf4j
public class BalanceInfoServiceImpl implements BalanceInfoService
{
    BalanceRepository balanceRepository;

    @Override
    public BalanceResponse getCurrentBalance () throws ServiceException
    {
        BalanceResponse response = new BalanceResponse();
        double amount;
        LocalDate tomorrow;
        Balance latestBalance;
        try {
            latestBalance = balanceRepository.findTop1ByOrderByDateDesc();
            tomorrow = LocalDate.now().plusDays(1);
            amount = latestBalance.getAmount();
            for (LocalDate date =
                 latestBalance.getDate().plusDays(1); date.isBefore(tomorrow);
                 date = date.plusDays(1)) {
                try {
                    //TODO:Call ledger service to get the total amount for a particular day
                    // LedgerService ledgerService = new LedgerService();
                    // amount+ = ledgerService.getTotalTransactionAmount(date);

                    //to throw exception
                    if (Math.random() == 2) {
                        throw new ServiceException("");
                    }
                }
                catch (ServiceException se) {
                    throw se;
                }
                catch (Exception e) {
                    log.error(
                        "Error while getting transaction amount for date {}",
                        date
                    );
                    throw new ServiceException(
                        "Error while getting transaction amount ");
                }
            }
            response.setAmount(amount);
            response.setDate(LocalDate.now());
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error("Error getting current balance" + e.getMessage());
            throw new ServiceException("Error getting current balance ");
        }
        ResponseUtil.success(response);
        return response;
    }
}

