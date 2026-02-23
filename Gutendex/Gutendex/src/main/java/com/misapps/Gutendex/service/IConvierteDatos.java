package com.misapps.Gutendex.service;

public interface IConvierteDatos {
    // Método genérico para convertir cualquier JSON a cualquier Clase/Record
    <T> T obtenerDatos(String json, Class<T> clase);
}
