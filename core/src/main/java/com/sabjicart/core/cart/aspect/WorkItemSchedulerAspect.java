
package com.sabjicart.core.cart.aspect;

import com.sabjicart.api.advice.WIScheduleTracker;
import com.sabjicart.api.messages.schedule.WorkItemMessagePojo;
import com.sabjicart.api.model.WorkItemScheduler;
import com.sabjicart.api.schedule.LogWorkItemSchedulerRunService;
import com.sabjicart.api.schedule.WorkItemScheduleService;
import com.sabjicart.api.schedule.WorkItemSchedulerStatus;
import com.sabjicart.core.cart.repository.WorkItemSchedulerRepository;
import com.sabjicart.util.StringUtil;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Configuration
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkItemSchedulerAspect
{

    @Autowired
    WorkItemSchedulerRepository workItemSchedulerRepository;

    @Autowired
    WorkItemScheduleService workItemScheduleService;

    @Autowired
    LogWorkItemSchedulerRunService logWorkItemSchedulerRunService;

    LocalDateTime currentExecutionTime;

    @Around("@annotation(com.sabjicart.api.advice.WIScheduleTracker)")
    public void around (ProceedingJoinPoint joinPoint) throws Throwable
    {
        // Declaration
        WorkItemScheduler workItemScheduler;
        WIScheduleTracker wiScheduleTracker;
        String workItemSchedulerName;
        MethodSignature methodSignature;

        // Initialization
        currentExecutionTime = LocalDateTime.now();

        methodSignature = (MethodSignature)joinPoint.getSignature();
        wiScheduleTracker =
            methodSignature.getMethod().getAnnotation(WIScheduleTracker.class);
        workItemSchedulerName = wiScheduleTracker.value();

        if (StringUtil.nullOrBlankOrEmptyString(workItemSchedulerName)) {
            log.error("No Scheduler found with the name: {}",
                workItemSchedulerName
            );
            return;
        }
        workItemScheduler = workItemSchedulerRepository.findCreatedJobWorkItem(
            workItemSchedulerName);

        if (null == workItemScheduler) {
            workItemScheduler =
                workItemScheduleService.createWorkItem(workItemSchedulerName);
        }

        try {

            // updated last execution run time for work item
            if (workItemScheduler.getStatus()
                                 .equals(WorkItemSchedulerStatus.PROCESSING))
            {
                // skipping execution if job already running
                return;
            }


            joinPoint.proceed();

            // updating work item status to SUCCESS
            workItemScheduleService.updateWorkItemSuccess(workItemScheduler.getId(),
                currentExecutionTime
                // this will be new last successful run time
            );

            // logging success publishing for debug
            WorkItemMessagePojo workItemMessagePojo =
                workItemScheduleService.generateWorkItemMessagePojo(
                    workItemScheduler,
                    WorkItemSchedulerStatus.SUCCESS,
                    currentExecutionTime,
                    ""
                );

            logWorkItemSchedulerRunService.logWorkItemMessage(
                workItemMessagePojo);
        }
        catch (Exception e) {
            log.error("Error Processing WorkItemSchedulerAspect,", e);
            // logging failed publishing for debug
            WorkItemMessagePojo workItemMessagePojo =
                workItemScheduleService.generateWorkItemMessagePojo(
                    workItemScheduler,
                    WorkItemSchedulerStatus.FAILURE,
                    currentExecutionTime,
                    e.getMessage() + " | " + e.getCause() + " | "
                        + Arrays.stream(e.getStackTrace()).findFirst().get()
                );
            log.info(
                "WIScheduleTracker Executed with Exception, workItemMessagePojo={}",
                workItemMessagePojo
            );
            logWorkItemSchedulerRunService.logWorkItemMessage(
                workItemMessagePojo);
        }
    }

    @AfterThrowing(pointcut = "@annotation(com.sabjicart.api.advice.WIScheduleTracker)",
        throwing = "exception")
    public void afterThrowing (JoinPoint joinPoint, Throwable exception)
        throws Throwable
    {
        // Declaration
        WorkItemScheduler workItemScheduler;
        WIScheduleTracker wiScheduleTracker;
        String workItemSchedulerName;
        MethodSignature methodSignature;

        // Initialization
        currentExecutionTime = LocalDateTime.now();
        methodSignature = (MethodSignature)joinPoint.getSignature();
        wiScheduleTracker =
            methodSignature.getMethod().getAnnotation(WIScheduleTracker.class);
        workItemSchedulerName = wiScheduleTracker.value();

        log.info("LogSchedulerRun afterThrowing by {} at {}",
            joinPoint,
            currentExecutionTime
        );

        workItemScheduler =
            workItemSchedulerRepository.findTop1ByNameAndStatusAndActiveOrderByTimeCreatedDesc(
                workItemSchedulerName,
                WorkItemSchedulerStatus.PROCESSING,
                true
            );

        if (null == workItemScheduler) {

            // this can never happen let's see
            log.error(
                "No work item found in state={}, with name={} to update failure status",
                WorkItemSchedulerStatus.PROCESSING.name(),
                workItemSchedulerName
            );
            return;
        }

        workItemScheduleService.updateWorkItemFailure(workItemScheduler);

        // logging failed publishing for debug
        WorkItemMessagePojo workItemMessagePojo =
            workItemScheduleService.generateWorkItemMessagePojo(
                workItemScheduler,
                WorkItemSchedulerStatus.FAILURE,
                currentExecutionTime,
                exception.getMessage() + " | " + exception.getCause() + " | "
                    + Arrays.stream(exception.getStackTrace()).findFirst().get()
            );
        log.info(
            "afterThrowing WIScheduleTracker Executed with Exception, workItemMessagePojo={}",
            workItemMessagePojo
        );
        logWorkItemSchedulerRunService.logWorkItemMessage(workItemMessagePojo);
    }

}
