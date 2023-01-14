/*

    Copyright (c) 2022 PySphere Technologies LLP.

    All rights reserved. Patents pending.

    Responsible: Rishabh Sinha

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
public class SubstationCart extends AbstractBaseEntity
{
    Long substationId;
    String cartPlateNumber;
    String cartDriverName;
    Long cartId;
}
