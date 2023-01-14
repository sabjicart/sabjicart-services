/*
    Copyright (c) 2021 PySphere Technologies, LLP.
    All rights reserved. Patents pending.
    Creation Date: 25/05/2022
    Responsible: Ayush Kumar
*/
package com.sabjicart.api.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cart extends AbstractBaseEntity {
    String cartPlateNumber;
}