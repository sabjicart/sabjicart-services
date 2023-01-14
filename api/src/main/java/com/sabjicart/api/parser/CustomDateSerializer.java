package com.sabjicart.api.parser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDateSerializer extends StdSerializer<LocalDate>
{

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public CustomDateSerializer ()
    {
        this(null);
    }

    public CustomDateSerializer (Class t)
    {
        super(t);
    }

    @Override
    public void serialize (
        LocalDate value,
        JsonGenerator gen,
        SerializerProvider arg2) throws IOException
    {
        gen.writeString(formatter.format(value));
    }
}

