package proyectodatos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Programa encargado de generar los archivos de prueba
 * para el proyecto de procesamiento de ventas.
 */
public class GenerateInfoFiles {

    // Generador de numeros aleatorios
    private static Random random = new Random();

    // Listas utilizadas para crear datos de prueba
    private static String[] nombres = {
        "Juan", "Carlos", "Maria", "Ana",
        "Pedro", "Laura", "Luis", "Sofia"
    };

    private static String[] apellidos = {
        "Gomez", "Perez", "Rodriguez", "Martinez",
        "Lopez", "Garcia", "Sanchez", "Torres"
    };

    private static String[] productos = {
        "Cuaderno", "Esfero", "Lapiz", "Borrador",
        "Regla", "Carpeta", "Marcador", "Tijeras",
        "Resaltador", "Pincel"
    };

    // Lista para guardar los vendedores creados
    private static List<Vendedor> vendedores = new ArrayList<Vendedor>();

    /**
     * Metodo principal.
     */
    public static void main(String[] args) {

        try {

            crearCarpetaVentas();

            createProductsFile(10);

            createSalesManInfoFile(4);

            crearArchivosDeVentas();

            System.out.println(
                "FINALIZACION EXITOSA: Los archivos fueron creados correctamente."
            );

        } catch (IOException e) {

            System.out.println(
                "ERROR: No fue posible crear los archivos."
            );

            System.out.println("Detalle: " + e.getMessage());
        }
    }

    /**
     * Crea la carpeta donde se guardaran los archivos
     * de ventas de los vendedores.
     */
    public static void crearCarpetaVentas() {

        File carpeta = new File("ventas");

        if (!carpeta.exists()) {
            carpeta.mkdir();
        }
    }

    /**
     * Crea un archivo de ventas para un vendedor.
     *
     * @param randomSalesCount cantidad de ventas
     * @param name nombre del vendedor
     * @param id numero de documento del vendedor
     */
    public static void createSalesMenFile(
            int randomSalesCount, String name, long id)
            throws IOException {

        String nombreArchivo =
            "ventas" + File.separator + name + "_" + id + ".txt";

        BufferedWriter escritor =
            new BufferedWriter(new FileWriter(nombreArchivo));

        // Primera linea: documento del vendedor
        escritor.write("CC;" + id);
        escritor.newLine();

        // Se generan las ventas
        for (int i = 0; i < randomSalesCount; i++) {

            int idProducto = random.nextInt(10) + 1;
            int cantidad = random.nextInt(5) + 1;

            escritor.write(
                idProducto + ";" + cantidad + ";"
            );

            escritor.newLine();
        }

        escritor.close();
    }

    /**
     * Crea el archivo productos.txt.
     *
     * @param productsCount cantidad de productos
     */
    public static void createProductsFile(
            int productsCount) throws IOException {

        BufferedWriter escritor =
            new BufferedWriter(new FileWriter("productos.txt"));

        for (int i = 0; i < productsCount; i++) {

            int id = i + 1;

            String nombre =
                productos[i % productos.length];

            int precio =
                (random.nextInt(99) + 2) * 500;

            escritor.write(
                id + ";" + nombre + ";" + precio
            );

            escritor.newLine();
        }

        escritor.close();
    }

    /**
     * Crea el archivo vendedores.txt.
     *
     * @param salesmanCount cantidad de vendedores
     */
    public static void createSalesManInfoFile(
            int salesmanCount) throws IOException {

        BufferedWriter escritor =
            new BufferedWriter(new FileWriter("vendedores.txt"));

        vendedores.clear();

        for (int i = 0; i < salesmanCount; i++) {

            String tipoDocumento = "CC";

            long numeroDocumento =
                1000000000L + random.nextInt(900000000);

            String nombre =
                nombres[i % nombres.length];

            String apellido =
                apellidos[i % apellidos.length];

            // Se guarda el vendedor en la lista
            Vendedor vendedor = new Vendedor(
                tipoDocumento,
                numeroDocumento,
                nombre,
                apellido
            );

            vendedores.add(vendedor);

            // Se escribe el vendedor en el archivo
            escritor.write(
                tipoDocumento + ";" +
                numeroDocumento + ";" +
                nombre + ";" +
                apellido
            );

            escritor.newLine();
        }

        escritor.close();
    }

    /**
     * Crea un archivo de ventas para cada vendedor.
     */
    public static void crearArchivosDeVentas()
            throws IOException {

        for (Vendedor vendedor : vendedores) {

            int cantidadVentas =
                random.nextInt(6) + 5;

            String nombre =
                vendedor.nombre + "_" +
                vendedor.apellido;

            createSalesMenFile(
                cantidadVentas,
                nombre,
                vendedor.numeroDocumento
            );
        }
    }

    /**
     * Clase utilizada para guardar temporalmente
     * la informacion de cada vendedor.
     */
    private static class Vendedor {

        String tipoDocumento;
        long numeroDocumento;
        String nombre;
        String apellido;

        public Vendedor(
                String tipoDocumento,
                long numeroDocumento,
                String nombre,
                String apellido) {

            this.tipoDocumento = tipoDocumento;
            this.numeroDocumento = numeroDocumento;
            this.nombre = nombre;
            this.apellido = apellido;
        }
    }
}