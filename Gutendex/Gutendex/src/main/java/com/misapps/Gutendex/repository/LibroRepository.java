package com.misapps.Gutendex.repository;

import com.misapps.Gutendex.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional; // ¡Importante!
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    // Esta es la línea exacta que te falta:
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

    List<Libro> findByIdioma(String idioma);

    long countByIdioma(String idioma);
}