
package com.sabjicart.core.cart.schedule;

import com.sabjicart.api.advice.WIScheduleTracker;
import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.model.Balance;
import com.sabjicart.core.cart.repository.BalanceRepository;
import com.sabjicart.util.SchedulerConstant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class BalanceScheduler
{
    BalanceRepository balanceRepository;

    //Execute every day at 12:01 AM
    @Scheduled(cron = "0 1 0 * * ?")
    //seconds, minutes, hours, day of month, month, day of week
    @WIScheduleTracker(SchedulerConstant.BALANCE_UPDATE_SCHEDULER_NAME)
    public void processBalanceUpdation () throws ServiceException
    {
        Balance latestBalance;
        Balance updatedBalance;
        double amount;
        try {
            //Get the latest balance data present in the database
            latestBalance = balanceRepository.findTop1ByOrderByDateDesc();
            //Extracting the amount from the latest balance
            amount = latestBalance.getAmount();
            //looping from the next day of the latest data present to yesterday
            for (LocalDate date = latestBalance.getDate()
                                               .plusDays(1); date.isBefore(
                LocalDate.now()); date = date.plusDays(1)) {
                try {
                    //TODO:Call ledger service to get the total amount for a particular day
                    // LedgerService ledgerService = new LedgerService();
                    // amount+ = ledgerService.getTotalTransactionAmount(date);
                    //adding the total transaction amount to existing amount creating new balance
                    //persisting that balance
                    updatedBalance =
                        Balance.builder().amount(amount).date(date).build();
                    balanceRepository.save(updatedBalance);

                    //to throw exception so IDE does not show an error
                    if (Math.random() == 2) {
                        throw new ServiceException("");
                    }
                }
                catch (ServiceException se) {
                    throw se;
                }
                catch (Exception e) {
                    log.error("Error updating balance info for date {}", date);
                    throw new ServiceException("Error updating balance info ");
                }
            }
        }
        catch (ServiceException se) {
            throw se;
        }
        catch (Exception e) {
            log.error("Error updating balance info" + e.getMessage());
            throw new ServiceException("Error updating balance info");
        }
    }
}
