
package com.sabjicart.core.cart.repository;

import com.sabjicart.api.model.WorkItemScheduler;
import com.sabjicart.api.schedule.WorkItemSchedulerStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface WorkItemSchedulerRepository
    extends JpaRepository<WorkItemScheduler, Long>
{

    @Query("select wi from WorkItemScheduler wi where wi.id =:id")
    WorkItemScheduler getWorkItemById (
        @Param("id")
        long id);

    @Query("select wi from WorkItemScheduler wi where wi.name = :name "
        + "and wi.status in ('CREATED') and wi.active=true")
    WorkItemScheduler findCreatedJobWorkItem (
        @Param("name")
        String name);

    WorkItemScheduler findTop1ByNameAndStatusAndActiveOrderByTimeCreatedDesc (
        @Param("name")
        String name,
        @Param("status")
        WorkItemSchedulerStatus status,
        @Param("active")
        boolean active);

}
