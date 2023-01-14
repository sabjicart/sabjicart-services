
package com.sabjicart.api.messages.schedule;

import com.sabjicart.api.schedule.WorkItemSchedulerStatus;
import com.sabjicart.util.ListUtil;
import com.sabjicart.util.SchedulerConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Slf4j
@AllArgsConstructor
public class WorkItemMessagePojo
{
    long scheduleId;
    String scheduleName;
    LocalDateTime runTime;
    WorkItemSchedulerStatus status;
    List<String> messageList;

    public WorkItemMessagePojo ()
    {
        messageList = new ArrayList<>();
    }

    public WorkItemMessagePojo (
        String scheduleName, List<String> messageList)
    {
        this.scheduleName = scheduleName;
        this.messageList = messageList;
    }

    public void addMessage (String message)
    {
        if (messageList.size()
            < SchedulerConstant.SCHEDULER_ERROR_MESSAGE_LIMIT)
        {
            messageList.add(message);
        }
        else {
            log.info(
                "Skipping message to be added to error stace to db, limit reached = {}",
                SchedulerConstant.SCHEDULER_ERROR_MESSAGE_LIMIT
            );
        }
    }

    public String getMessageList ()
    {
        StringBuilder messageBuilder = new StringBuilder();
        if (!ListUtil.nullOrEmptyList(messageList)) {
            for (String msg : messageList) {
                messageBuilder.append(msg);
                messageBuilder.append(", ");
            }
        }
        return messageBuilder.toString();
    }
}
