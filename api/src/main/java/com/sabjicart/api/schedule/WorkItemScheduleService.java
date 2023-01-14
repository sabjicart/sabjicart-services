
package com.sabjicart.api.schedule;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.schedule.WorkItemMessagePojo;
import com.sabjicart.api.model.WorkItemScheduler;

import java.time.LocalDateTime;

public interface WorkItemScheduleService
{

    /**
     * Interface to add new work item scheduler
     * @param schedulerName
     * @throws ServiceException
     * @return
     */
    WorkItemScheduler createWorkItem (String schedulerName)
        throws ServiceException;

    /**
     * Interface to update status and latest execution time
     * @param workItemScheduler
     * @param runStartTime
     */
    void updateWorkItemProcessing (
        WorkItemScheduler workItemScheduler, long runStartTime)
        throws ServiceException;

    /**
     * Interface to update status and latest execution time
     * @param workItemSchedulerId
     * @param lastSuccessfulRunTime
     */
    void updateWorkItemSuccess (
        long workItemSchedulerId, LocalDateTime lastSuccessfulRunTime)
        throws ServiceException;

    WorkItemMessagePojo generateWorkItemMessagePojo (
        WorkItemScheduler workItemScheduler,
        WorkItemSchedulerStatus status,
        LocalDateTime runTime,
        String errorMessage);

    /**
     * Interface to update failure status and run completion time
     * @param workItemScheduler
     *
     */
    void updateWorkItemFailure (
        WorkItemScheduler workItemScheduler) throws ServiceException;
}
