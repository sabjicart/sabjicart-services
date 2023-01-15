
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