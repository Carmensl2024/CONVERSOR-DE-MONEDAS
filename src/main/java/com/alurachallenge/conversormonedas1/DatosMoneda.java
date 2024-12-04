package com.alurachallenge.conversormonedas1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosMoneda(
        String monedaOrigen,
        Double tasaConversion
) {

    @Override
    public String toString() {
        return "DatosMoneda[" +
                "monedaOrigen=" + monedaOrigen + ", " +
                "tasaConversion=" + tasaConversion + ']';
    }
}

