
package com.sabjicart.api.model;

import com.sabjicart.api.schedule.WorkItemSchedulerStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WorkItemScheduler extends AbstractBaseEntity
{
    String name;
    LocalDateTime lastRunStartTime;
    LocalDateTime lastRunEndTime;
    LocalDateTime lastSuccessfulRunTime;
    @Enumerated(EnumType.STRING)
    WorkItemSchedulerStatus status;
    String description;
}
