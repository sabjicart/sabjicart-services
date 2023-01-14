/*
    Copyright (c) 2021 PySphere Technologies, LLP.
    All rights reserved. Patents pending.
    Creation Date: 25/05/2022
    Responsible: Ayush Kumar
*/
package com.sabjicart.api.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Substation extends AbstractBaseEntity
{
    String substationName;
    String substationId;
}
