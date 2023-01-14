
package com.sabjicart.core.cart.aspect;

import com.sabjicart.api.advice.LogSchedulerRun;
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
public class LogSchedulerRunAspect
{
    @Autowired
    WorkItemScheduleService workItemScheduleService;

    @Autowired
    LogWorkItemSchedulerRunService logWorkItemSchedulerRunService;

    @Autowired
    WorkItemSchedulerRepository workItemSchedulerRepository;

    LocalDateTime currentExecutionTime;

    @Around("@annotation(com.sabjicart.api.advice.LogSchedulerRun)")
    public void around (ProceedingJoinPoint joinPoint) throws Throwable
    {
        // Declaration
        WorkItemScheduler workItemScheduler;
        LogSchedulerRun logSchedulerRun;
        String logSchedulerRunName;
        MethodSignature methodSignature;

        joinPoint.proceed();

        try {
            // Initialization
            currentExecutionTime = LocalDateTime.now();
            methodSignature = (MethodSignature)joinPoint.getSignature();
            logSchedulerRun = methodSignature.getMethod()
                                             .getAnnotation(LogSchedulerRun.class);
            logSchedulerRunName = logSchedulerRun.value();

            if (StringUtil.nullOrBlankOrEmptyString(logSchedulerRunName)) {
                log.error("No Scheduler found with the name: {}",
                    logSchedulerRunName
                );
                return;
            }

            if (StringUtil.nullOrBlankOrEmptyString(logSchedulerRunName)) {
                log.error("No Scheduler found with the name: {}",
                    logSchedulerRunName
                );
                return;
            }

            workItemScheduler =
                workItemSchedulerRepository.findTop1ByNameAndStatusAndActiveOrderByTimeCreatedDesc(
                    logSchedulerRunName,
                    WorkItemSchedulerStatus.PROCESSING,
                    true
                );

            if (null != workItemScheduler) {

                WorkItemMessagePojo workItemMessagePojo =
                    workItemScheduleService.generateWorkItemMessagePojo(
                        workItemScheduler,
                        WorkItemSchedulerStatus.SUCCESS,
                        currentExecutionTime,
                        ""
                    );

                log.info(
                    "LogSchedulerRun Executed successfully, workItemMessagePojo={}",
                    workItemMessagePojo
                );
            }

        }
        catch (Exception e) {
            log.error("Error Processing LogSchedulerRun,", e);
        }
    }

    @AfterThrowing(pointcut = "@annotation(com.sabjicart.api.advice.LogSchedulerRun)",
        throwing = "exception")
    public void afterThrowing (JoinPoint joinPoint, Throwable exception)
        throws Throwable
    {
        // Declaration
        WorkItemScheduler workItemScheduler;
        LogSchedulerRun logSchedulerRun;
        String logSchedulerRunName;
        MethodSignature methodSignature;

        // Initialization
        currentExecutionTime = LocalDateTime.now();
        methodSignature = (MethodSignature)joinPoint.getSignature();
        logSchedulerRun =
            methodSignature.getMethod().getAnnotation(LogSchedulerRun.class);
        logSchedulerRunName = logSchedulerRun.value();

        if (StringUtil.nullOrBlankOrEmptyString(logSchedulerRunName)) {
            log.error("No Scheduler find with the name: {}",
                logSchedulerRunName
            );
            return;
        }

        if (StringUtil.nullOrBlankOrEmptyString(logSchedulerRunName)) {
            log.error("No Scheduler found with the name: {}",
                logSchedulerRunName
            );
            return;
        }
        workItemScheduler =
            workItemSchedulerRepository.findTop1ByNameAndStatusAndActiveOrderByTimeCreatedDesc(
                logSchedulerRunName,
                WorkItemSchedulerStatus.PROCESSING,
                true
            );

        log.info("LogSchedulerRun afterThrowing by {} at {}",
            joinPoint,
            currentExecutionTime
        );

        if (null != workItemScheduler) {

            // logging failed publishing for debug
            WorkItemMessagePojo workItemMessagePojo =
                workItemScheduleService.generateWorkItemMessagePojo(
                    workItemScheduler,
                    WorkItemSchedulerStatus.FAILURE,
                    currentExecutionTime,
                    exception.getMessage() + " | " + exception.getCause()
                        + " | " + Arrays.stream(exception.getStackTrace())
                                        .findFirst()
                                        .get()
                );

            log.info(
                "afterThrowing LogSchedulerRun Executed with Exception, workItemMessagePojo={}",
                workItemMessagePojo
            );

            logWorkItemSchedulerRunService.logWorkItemMessage(
                workItemMessagePojo);
        }

    }
}
