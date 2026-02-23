package com.misapps.Gutendex;

import com.misapps.Gutendex.model.Autor;
import com.misapps.Gutendex.model.Libro;
import com.misapps.Gutendex.model.Dto.AutorDTO;
import com.misapps.Gutendex.model.Dto.LibroDTO;
import com.misapps.Gutendex.model.Dto.LibroResponseDTO;
import com.misapps.Gutendex.repository.AutorRepository;
import com.misapps.Gutendex.repository.LibroRepository;
import com.misapps.Gutendex.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatalogoService {
    @Autowired
    private ConvierteDatos conversor;
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private ApiGutendexClient apiCliente;


    // OPCIÓN 1: Buscar en la API y persistir en la BD
    public void buscarYGuardarLibro(String titulo) {
        String json = apiCliente.buscarLibroPorTitulo(titulo);
        LibroResponseDTO datos = conversor.obtenerDatos(json, LibroResponseDTO.class);

        if (datos != null && datos.results() != null && !datos.results().isEmpty()) {
            LibroDTO libroDTO = datos.results().get(0);

            // Manejo de Autor (Evitar duplicados)
            Autor autor;
            if (libroDTO.autores() != null && !libroDTO.autores().isEmpty()) {
                AutorDTO autorDTO = libroDTO.autores().get(0);
                autor = autorRepository.findByNombreIgnoreCase(autorDTO.nombre())
                        .orElseGet(() -> autorRepository.save(new Autor(autorDTO)));
            } else {
                autor = autorRepository.findByNombreIgnoreCase("Autor Desconocido")
                        .orElseGet(() -> {
                            Autor a = new Autor();
                            a.setNombre("Autor Desconocido");
                            return autorRepository.save(a);
                        });
            }

            // Manejo de Libro (Evitar duplicados)
            Optional<Libro> libroExistente = libroRepository.findByTituloContainsIgnoreCase(libroDTO.titulo());
            if (libroExistente.isPresent()) {
                System.out.println("\n--- El libro ya se encuentra en la base de datos ---");
                System.out.println(libroExistente.get());
            } else {
                Libro libro = new Libro(libroDTO, autor);
                libroRepository.save(libro);
                System.out.println("\n--- LIBRO ENCONTRADO Y GUARDADO ---");
                System.out.println(libro);
            }
        } else {
            System.out.println("\n[API] No se encontró el libro: " + titulo);
        }
    }

    // OPCIÓN 2: Listar lo que ya guardaste
    public void listarLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("\nLa base de datos está vacía. Busca un libro primero (Opción 1).");
        } else {
            System.out.println("\n=== LIBROS GUARDADOS ===");
            libros.forEach(System.out::println);
        }
    }

    // OPCIÓN 3: Listar autores de los libros guardados
    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("\nNo hay autores registrados.");
        } else {
            System.out.println("\n=== AUTORES REGISTRADOS ===");
            autores.forEach(System.out::println);
        }
    }

    // OPCIÓN 4: Filtro histórico en la BD
    public void autoresVivosEnAnio(int anio) {
        List<Autor> autores = autorRepository.findAutoresVivosEnDeterminadoAnio(anio);
        if (autores.isEmpty()) {
            System.out.println("\nNo se encontraron autores vivos en el año " + anio + " en la base de datos.");
        } else {
            System.out.println("\n=== AUTORES VIVOS EN EL AÑO " + anio + " ===");
            autores.forEach(System.out::println);
        }
    }

    // OPCIÓN 5: Estadísticas por idioma en la BD
    public void cantidadLibrosPorIdioma(String idioma) {
        long conteo = libroRepository.countByIdioma(idioma);
        if (conteo == 0) {
            System.out.println("\nNo hay libros registrados en el idioma: " + idioma);
        } else {
            System.out.println("\n--- RESULTADO ---");
            System.out.println("Encontrados " + conteo + " libros en [" + idioma + "].");
        }
    }
}