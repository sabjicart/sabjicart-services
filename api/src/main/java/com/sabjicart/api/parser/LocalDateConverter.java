
package com.sabjicart.api.parser;

import com.sabjicart.util.DateUtil;

import javax.persistence.AttributeConverter;
import java.time.LocalDate;

public class LocalDateConverter implements AttributeConverter<LocalDate, Long>
{
    @Override
    public Long convertToDatabaseColumn (LocalDate attribute)
    {
        return DateUtil.getZonedTimeInMillis(attribute);
    }

    @Override
    public LocalDate convertToEntityAttribute (Long dbData)
    {
        if (null == dbData)
            return null;
        return DateUtil.getDateFromEpochMillis(dbData);
    }
}
