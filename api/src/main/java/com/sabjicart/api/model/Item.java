
package com.sabjicart.api.model;


import com.sabjicart.api.shared.Denomination;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item extends AbstractBaseEntity {
    String itemName;
    String itemId;
    String itemDescription;
    @Enumerated(EnumType.STRING)
    Denomination denomination;

}
