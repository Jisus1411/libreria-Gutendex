package com.misapps.Gutendex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

@Component
public class Principal {

    private final CatalogoService catalogoService;
    private final Scanner sc = new Scanner(System.in);

    // Al usar constructor, Spring inyecta el servicio automáticamente
    public Principal(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    public void mostrarMenu() {
        int opcion = -1;

        do {
            System.out.println("\n=== MENÚ DE GUTENDEX ===");
            System.out.println("1 - Buscar libro por título");
            System.out.println("2 - Listar libros guardados");
            System.out.println("3 - Listar autores");
            System.out.println("4 - Listar autores vivos en determinado año");
            System.out.println("5 - Cantidad de libros por idioma");
            System.out.println("0 - Salir");
            System.out.print("Seleccione una opción: ");

            try {
                // Leemos toda la línea como texto y convertimos a número
                // Esto es mucho más seguro que sc.nextInt()
                String entrada = sc.nextLine();
                opcion = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número entero entre 0 y 5.");
                opcion = -1;
                continue; // Reinicia el ciclo
            }

            switch(opcion) {
                case 1:
                    System.out.print("Ingrese el nombre del libro que desea buscar: ");
                    String titulo = sc.nextLine().trim();
                    if (!titulo.isEmpty()) {
                        catalogoService.buscarYGuardarLibro(titulo);
                    } else {
                        System.out.println("El título no puede estar vacío.");
                    }
                    break;
                case 2:
                    catalogoService.listarLibros();
                    break;
                case 3:
                    catalogoService.listarAutores();
                    break;
                case 4:
                    System.out.print("Ingrese el año para buscar autores vivos: ");
                    try {
                        int anio = Integer.parseInt(sc.nextLine());
                        catalogoService.autoresVivosEnAnio(anio);
                    } catch (NumberFormatException e) {
                        System.out.println("Año no válido. Debe ser un número.");
                    }
                    break;
                case 5:
                    System.out.print("Ingrese el código del idioma (ej: en, es, fr): ");
                    String idioma = sc.nextLine().trim().toLowerCase();
                    catalogoService.cantidadLibrosPorIdioma(idioma);
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación... ¡Hasta pronto!");
                    break;
                default:
                    System.out.println("Opción no reconocida. Intente de nuevo.");
            }

        } while(opcion != 0);
    }
}