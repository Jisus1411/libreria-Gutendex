package com.misapps.Gutendex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component // Esta anotación permite que CatalogoService la reconozca mediante @Autowired
public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return mapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            // Si el JSON falla, lanzamos una excepción descriptiva
            throw new RuntimeException("Error al convertir los datos del JSON: " + e.getMessage());
        }
    }
}
