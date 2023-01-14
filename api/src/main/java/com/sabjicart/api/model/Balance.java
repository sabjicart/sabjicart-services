
package com.sabjicart.api.model;

import com.sabjicart.api.parser.LocalDateConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.Convert;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Balance extends AbstractBaseEntity
{
    double amount;
    @Convert(converter = LocalDateConverter.class)
    LocalDate date;
}
