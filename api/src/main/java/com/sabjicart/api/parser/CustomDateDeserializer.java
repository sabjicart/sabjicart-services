package com.sabjicart.api.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CustomDateDeserializer extends JsonDeserializer<LocalDate>
{
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate deserialize (
        JsonParser jsonParser, DeserializationContext deserializationContext)
        throws IOException
    {

        String date = jsonParser.getText();
        return LocalDate.parse(date, formatter);
    }
}