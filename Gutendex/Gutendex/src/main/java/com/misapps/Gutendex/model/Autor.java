package com.misapps.Gutendex.model;

import com.misapps.Gutendex.model.Dto.AutorDTO;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    // Usamos los nombres que espera tu AutorRepository
    private Integer anioNacimiento;
    private Integer anioFallecimiento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor() {}

    public Autor(AutorDTO autorDTO) {
        this.nombre = autorDTO.nombre();
        this.anioNacimiento = autorDTO.anioNacimiento();
        this.anioFallecimiento = autorDTO.anioFallecimiento();
    }

    @Override
    public String toString() {
        // Esto limpia la lista de libros para que no sea un bloque ilegible
        String nombresLibros = (libros != null)
                ? libros.stream().map(Libro::getTitulo).collect(Collectors.joining(", "))
                : "Ninguno";

        return String.format(
                "%n*********** AUTOR ***********%n" +
                        " Nombre: %s%n" +
                        " Fecha de nacimiento: %s%n" +
                        " Fecha de fallecimiento: %s%n" +
                        " Libros: [%s]%n" +
                        "*****************************%n",
                nombre,
                (anioNacimiento != null ? anioNacimiento : "Desconocido"),
                (anioFallecimiento != null ? anioFallecimiento : "N/A"),
                nombresLibros
        );
    }

    // --- GETTERS Y SETTERS (Indispensables para que CatalogoService funcione) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; } // <--- ESTE ES EL QUE TE FALTABA

    public Integer getAnioNacimiento() { return anioNacimiento; }
    public void setAnioNacimiento(Integer anioNacimiento) { this.anioNacimiento = anioNacimiento; }

    public Integer getAnioFallecimiento() { return anioFallecimiento; }
    public void setAnioFallecimiento(Integer anioFallecimiento) { this.anioFallecimiento = anioFallecimiento; }

    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }
}





