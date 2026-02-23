package com.misapps.Gutendex.model;

import com.misapps.Gutendex.model.Dto.LibroDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String idioma;
    private Double numeroDescargas;

    @ManyToOne
    private Autor autor;

    public Libro() {}

    public Libro(LibroDTO libroDTO, Autor autor) {
        this.titulo = libroDTO.titulo();
        this.idioma = libroDTO.idiomas().isEmpty() ? "Desconocido" : libroDTO.idiomas().get(0);
        this.numeroDescargas = libroDTO.numeroDescargas();
        this.autor = autor;
    }

    @Override
    public String toString() {
        return String.format("--- LIBRO: %s [Idioma: %s] ---", titulo, idioma);
    }
    // Getters y Setters...
    public String getTitulo() { return titulo; }
}
