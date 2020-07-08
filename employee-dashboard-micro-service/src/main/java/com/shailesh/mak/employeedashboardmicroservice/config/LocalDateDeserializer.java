package com.shailesh.mak.employeedashboardmicroservice.config;

import java.io.IOException;
import java.time.LocalDate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

	@Override
	public LocalDate deserialize(final JsonParser p, final DeserializationContext ctxt)
			throws IOException, JsonProcessingException {

		LocalDate localDate = null;
		final String date = p.getText();

		localDate = LocalDate.parse(date);

		return localDate;
	}
}