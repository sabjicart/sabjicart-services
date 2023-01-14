
package com.sabjicart.core.cart.schedule;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.schedule.WorkItemMessagePojo;
import com.sabjicart.api.model.WorkItemScheduler;
import com.sabjicart.api.schedule.WorkItemScheduleService;
import com.sabjicart.api.schedule.WorkItemSchedulerStatus;
import com.sabjicart.core.cart.repository.WorkItemSchedulerRepository;
import com.sabjicart.util.DateUtil;
import com.sabjicart.util.GenericConstant;
import com.sabjicart.util.SchedulerConstant;
import com.sabjicart.util.StringUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
@AllArgsConstructor
public class WorkItemScheduleServiceImpl implements WorkItemScheduleService
{

    WorkItemSchedulerRepository workItemSchedulerRepository;

    // Method to add new work item for next run of the schedule job
    @Override
    public WorkItemScheduler createWorkItem (String schedulerName)
        throws ServiceException
    {
        // Declaration
        WorkItemScheduler wi;
        WorkItemScheduler newWorkItem;
        WorkItemScheduler lastSuccessWi;
        LocalDateTime lastSuccessfulRunTime;

        // Initialisation
        lastSuccessfulRunTime = DateUtil.getLocalDateTimeAsOf1970();

        try {

            wi =
                workItemSchedulerRepository.findTop1ByNameAndStatusAndActiveOrderByTimeCreatedDesc(
                    schedulerName,
                    WorkItemSchedulerStatus.CREATED,
                    true
                );
            if (null != wi) {
                log.info(
                    "Work item already exist for scheduler ={}, skipping creating new one..",
                    schedulerName
                );
                return wi;
            }

            log.info("Creating new Work item for scheduler ={}", schedulerName);

            newWorkItem = WorkItemScheduler.builder()
                                           .name(schedulerName)
                                           .description(schedulerName)
                                           .status(WorkItemSchedulerStatus.CREATED)
                                           .build();
            newWorkItem.setCreatedBy(GenericConstant.SYSTEM_USER);
            newWorkItem.setModifiedBy(GenericConstant.SYSTEM_USER);

            lastSuccessWi =
                workItemSchedulerRepository.findTop1ByNameAndStatusAndActiveOrderByTimeCreatedDesc(
                    schedulerName,
                    WorkItemSchedulerStatus.SUCCESS,
                    true
                );

            if (null != lastSuccessWi) {
                lastSuccessfulRunTime =
                    lastSuccessWi.getLastSuccessfulRunTime();
            }

            newWorkItem.setLastSuccessfulRunTime(lastSuccessfulRunTime);
            workItemSchedulerRepository.saveAndFlush(newWorkItem);

            log.info(
                "Successfully Created new Work item for scheduler ={}",
                schedulerName
            );
        }
        catch (Exception e) {
            log.error("Error creation new work item for scheduler={}",
                schedulerName,
                e
            );
            throw new ServiceException(
                "Error creation new work item for scheduler " + schedulerName,
                e
            );
        }

        return newWorkItem;
    }

    @Override
    public void updateWorkItemProcessing (
        WorkItemScheduler workItemScheduler, long runStartTime)
        throws ServiceException
    {
        // Declaration

        if (null == workItemScheduler) {
            log.error(
                "Invalid workItemScheduler, Error updating run start time for schedule job={}",
                workItemScheduler
            );
            throw new ServiceException("Error processing schedule job");
        }

        try {
            // update last success run time
            workItemScheduler.setLastRunStartTime(LocalDateTime.now());
            workItemScheduler.setStatus(WorkItemSchedulerStatus.PROCESSING);
            workItemScheduler.setModifiedBy(GenericConstant.SYSTEM_USER);
            workItemSchedulerRepository.saveAndFlush(workItemScheduler);
        }
        catch (Exception e) {
            log.error(
                "Error creation new work item for scheduler={}",
                workItemScheduler.getName(),
                e
            );
            throw new ServiceException("Error creation new work item for scheduler "
                    + workItemScheduler.getName(),
                e
            );
        }
    }

    @Override
    public void updateWorkItemSuccess (
        long workItemSchedulerId, LocalDateTime lastSuccessfulRunTime)
        throws ServiceException
    {

        // Declaration
        WorkItemScheduler workItemSchedulerInProcessing;

        workItemSchedulerInProcessing =
            workItemSchedulerRepository.getWorkItemById(workItemSchedulerId);

        if (null == workItemSchedulerInProcessing) {
            log.error(
                "Invalid workItemScheduler, Error fetching work item with id={}, name={}",
                workItemSchedulerInProcessing.getId(),
                workItemSchedulerInProcessing.getName()
            );
            throw new ServiceException(
                "Failure Processing Error processing work item name: "
                    + workItemSchedulerInProcessing.getName());
        }

        try {
            // update last success run time
            workItemSchedulerInProcessing.setLastSuccessfulRunTime(
                lastSuccessfulRunTime);
            workItemSchedulerInProcessing.setLastRunEndTime(LocalDateTime.now());
            workItemSchedulerInProcessing.setStatus(WorkItemSchedulerStatus.SUCCESS);
            workItemSchedulerInProcessing.setModifiedBy(GenericConstant.SYSTEM_USER);
            workItemSchedulerRepository.saveAndFlush(
                workItemSchedulerInProcessing);

            // adding new work item for next run
            createWorkItem(workItemSchedulerInProcessing.getName());
        }
        catch (Exception e) {
            log.error(
                "Error creation new work item for scheduler id={}",
                workItemSchedulerId,
                e
            );
            throw new ServiceException("Error creation new work item for scheduler id "
                    + workItemSchedulerId,
                e
            );
        }

    }

    @Override
    public WorkItemMessagePojo generateWorkItemMessagePojo (
        WorkItemScheduler workItemScheduler,
        WorkItemSchedulerStatus status,
        LocalDateTime runTime,
        String errorMessage)
    {
        // logging failed publishing for debug
        WorkItemMessagePojo workItemMessagePojo = WorkItemMessagePojo.builder()
                                                                     .scheduleId(
                                                                         workItemScheduler.getId())
                                                                     .scheduleName(
                                                                         workItemScheduler.getName())
                                                                     .status(
                                                                         status)
                                                                     .runTime(
                                                                         runTime)
                                                                     .messageList(
                                                                         new ArrayList<String>())
                                                                     .build();
        if (!StringUtil.nullOrBlankOrEmptyString(errorMessage)) {
            workItemMessagePojo.addMessage(StringUtil.trimByLength(errorMessage,
                SchedulerConstant.SCHEDULER_ERROR_MESSAGE_LENGTH
            ));
        }

        return workItemMessagePojo;
    }

    @Override
    public void updateWorkItemFailure (
        WorkItemScheduler workItemScheduler) throws ServiceException
    {
        if (null == workItemScheduler) {
            log.error(
                "Invalid workItemScheduler, Error updating run start time for schedule job={}",
                workItemScheduler
            );
            throw new ServiceException("Error processing schedule job");
        }

        try {
            // update last run time
            workItemScheduler.setLastRunEndTime(LocalDateTime.now());
            workItemScheduler.setStatus(WorkItemSchedulerStatus.FAILURE);
            workItemScheduler.setModifiedBy(GenericConstant.SYSTEM_USER);
            workItemSchedulerRepository.saveAndFlush(workItemScheduler);

            // adding new work item for next run
            createWorkItem(workItemScheduler.getName());
        }
        catch (Exception e) {
            log.error(
                "Error creation new work item for scheduler={}",
                workItemScheduler.getName(),
                e
            );
            throw new ServiceException("Error creation new work item for scheduler "
                    + workItemScheduler.getName(),
                e
            );
        }
    }
}
