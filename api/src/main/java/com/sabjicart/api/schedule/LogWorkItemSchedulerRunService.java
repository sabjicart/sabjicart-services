
package com.sabjicart.api.schedule;

import com.sabjicart.api.exceptions.ServiceException;
import com.sabjicart.api.messages.schedule.WorkItemMessagePojo;

public interface LogWorkItemSchedulerRunService
{
    void logWorkItemMessage (WorkItemMessagePojo workItemMessagePojo)
        throws ServiceException;
}
