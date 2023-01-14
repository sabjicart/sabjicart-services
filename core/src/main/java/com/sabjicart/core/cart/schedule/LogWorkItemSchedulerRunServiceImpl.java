
package com.sabjicart.core.cart.schedule;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.schedule.WorkItemMessagePojo;
import com.sabjicart.api.model.LogWorkItemSchedulerRun;
import com.sabjicart.api.schedule.LogWorkItemSchedulerRunService;
import com.sabjicart.core.cart.repository.LogWorkItemSchedulerRunRepository;
import com.sabjicart.util.GenericConstant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
@AllArgsConstructor
public class LogWorkItemSchedulerRunServiceImpl
    implements LogWorkItemSchedulerRunService
{
    LogWorkItemSchedulerRunRepository logWorkItemSchedulerRunRepository;

    @Override
    public void logWorkItemMessage (WorkItemMessagePojo workItemMessagePojo)
        throws ServiceException
    {

        // Declaration
        LogWorkItemSchedulerRun logWorkItemSchedulerRun;

        try {

            logWorkItemSchedulerRun = LogWorkItemSchedulerRun.builder()
                                                             .workItemSchedulerId(
                                                                 workItemMessagePojo.getScheduleId())
                                                             .name(
                                                                 workItemMessagePojo.getScheduleName())
                                                             .runTime(
                                                                 workItemMessagePojo.getRunTime())
                                                             .status(
                                                                 workItemMessagePojo.getStatus())
                                                             .errorDescription(
                                                                 workItemMessagePojo.getMessageList())
                                                             .build();

            logWorkItemSchedulerRun.setCreatedBy(GenericConstant.SYSTEM_USER);
            logWorkItemSchedulerRun.setModifiedBy(GenericConstant.SYSTEM_USER);

            logWorkItemSchedulerRunRepository.save(logWorkItemSchedulerRun);
        }
        catch (Exception e) {

        }

    }
}
