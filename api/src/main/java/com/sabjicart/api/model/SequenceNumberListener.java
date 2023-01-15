/*
    Copyright (c) 2021 sabjicartphere Technologies, LLP.
    All rights reserved. Patents pending.
    Creation Date: 25/05/2022
    Responsible: Ayush Kumar
*/
package com.sabjicart.api.model;

import com.sabjicart.util.GenericUtil;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Slf4j
public class SequenceNumberListener {


    /**
     * @param object the object needs to be persisted
     */
    @PrePersist
    public void prePersist(Object object) {

        if (!(object instanceof AbstractBaseEntity)) {
            return;
        }

        AbstractBaseEntity abe = (AbstractBaseEntity) object;
        if (abe.getTimeCreated() == 0) {
            abe.setTimeCreated(System.currentTimeMillis());
        }

        if (abe.getTimeUpdated() == 0) {
            abe.setTimeUpdated(System.currentTimeMillis());
        }
        if (null == abe.getCreatedBy())
            abe.setCreatedBy(GenericUtil.getUserName());
        if (null == abe.getModifiedBy())
            abe.setModifiedBy(GenericUtil.getUserName());

    }

    @PreUpdate
    public void preUpdate(Object object) {
        if (!(object instanceof AbstractBaseEntity)) {
            return;
        }

        AbstractBaseEntity abe = (AbstractBaseEntity) object;
        abe.setTimeUpdated(System.currentTimeMillis());
        if (null == abe.getModifiedBy())
            abe.setModifiedBy(GenericUtil.getUserName());
    }

}
