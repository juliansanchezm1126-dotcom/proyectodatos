package proyectodatos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Genera archivos de prueba para el sistema de procesamiento de ventas.
 * Crea productos, vendedores y archivos individuales de ventas.
 *
 * @author
 */
public class GenerateInfoFiles {

    private static final Random RANDOM = new Random();

    private static final String[] NOMBRES = {
        "Juan", "Carlos", "Maria", "Ana",
        "Pedro", "Laura", "Luis", "Sofia"
    };

    private static final String[] APELLIDOS = {
        "Gomez", "Perez", "Rodriguez", "Martinez",
        "Lopez", "Garcia", "Sanchez", "Torres"
    };

    private static final String[] PRODUCTOS = {
        "Cuaderno", "Esfero", "Lapiz", "Borrador",
        "Regla", "Carpeta", "Marcador", "Tijeras",
        "Resaltador", "Pincel"
    };

    private static final List<Vendedor> VENDEDORES =
            new ArrayList<Vendedor>();

    /**
     * Punto de entrada del programa generador.
     *
     * @param args argumentos de ejecución
     */
    public static void main(String[] args) {

        try {
            crearCarpetaVentas();
            createProductsFile(10);
            createSalesManInfoFile(4);
            crearArchivosDeVentas();

            System.out.println(
                "PROCESO EXITOSO: archivos de prueba creados."
            );

        } catch (IOException | IllegalArgumentException e) {
            System.out.println(
                "ERROR AL GENERAR LOS ARCHIVOS: " + e.getMessage()
            );
        }
    }

    /**
     * Crea la carpeta que contiene los archivos de ventas.
     *
     * @throws IOException si no es posible crear la carpeta
     */
    public static void crearCarpetaVentas() throws IOException {

        File carpeta = new File("ventas");

        if (carpeta.exists() && !carpeta.isDirectory()) {
            throw new IOException(
                "Existe un archivo llamado ventas; se necesita una carpeta."
            );
        }

        if (!carpeta.exists() && !carpeta.mkdirs()) {
            throw new IOException(
                "No fue posible crear la carpeta ventas."
            );
        }
    }

    /**
     * Genera un archivo individual con las ventas de un vendedor.
     *
     * @param randomSalesCount cantidad de ventas por generar
     * @param name nombre que identifica al vendedor
     * @param id número de documento del vendedor
     * @throws IOException si ocurre un error al escribir el archivo
     */
    public static void createSalesMenFile(
            int randomSalesCount, String name, long id)
            throws IOException {

        if (randomSalesCount < 0) {
            throw new IllegalArgumentException(
                "La cantidad de ventas no puede ser negativa."
            );
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El nombre del vendedor no puede estar vacío."
            );
        }

        if (id <= 0) {
            throw new IllegalArgumentException(
                "El documento debe ser positivo."
            );
        }

        crearCarpetaVentas();

        String nombreArchivo = "ventas" + File.separator
                + name + "_" + id + ".txt";

        try (BufferedWriter escritor =
                new BufferedWriter(new FileWriter(nombreArchivo))) {

            escritor.write("CC;" + id);
            escritor.newLine();

            for (int i = 0; i < randomSalesCount; i++) {
                int idProducto = RANDOM.nextInt(10) + 1;
                int cantidad = RANDOM.nextInt(5) + 1;

                escritor.write(idProducto + ";" + cantidad);
                escritor.newLine();
            }
        }
    }

    /**
     * Genera el archivo productos.txt.
     *
     * @param productsCount cantidad de productos por generar
     * @throws IOException si ocurre un error al escribir el archivo
     */
    public static void createProductsFile(
            int productsCount) throws IOException {

        if (productsCount <= 0) {
            throw new IllegalArgumentException(
                "Debe generarse al menos un producto."
            );
        }

        try (BufferedWriter escritor =
                new BufferedWriter(new FileWriter("productos.txt"))) {

            for (int i = 0; i < productsCount; i++) {
                int id = i + 1;
                String nombre = PRODUCTOS[i % PRODUCTOS.length];
                int precio = (RANDOM.nextInt(99) + 2) * 500;

                escritor.write(id + ";" + nombre + ";" + precio);
                escritor.newLine();
            }
        }
    }

    /**
     * Genera el archivo vendedores.txt y carga los vendedores creados.
     *
     * @param salesmanCount cantidad de vendedores por generar
     * @throws IOException si ocurre un error al escribir el archivo
     */
    public static void createSalesManInfoFile(
            int salesmanCount) throws IOException {

        if (salesmanCount <= 0) {
            throw new IllegalArgumentException(
                "Debe generarse al menos un vendedor."
            );
        }

        VENDEDORES.clear();

        try (BufferedWriter escritor =
                new BufferedWriter(new FileWriter("vendedores.txt"))) {

            for (int i = 0; i < salesmanCount; i++) {
                String tipoDocumento = "CC";
                long numeroDocumento =
                        1000000000L + i + RANDOM.nextInt(1000000);

                String nombre = NOMBRES[i % NOMBRES.length];
                String apellido = APELLIDOS[i % APELLIDOS.length];

                Vendedor vendedor = new Vendedor(
                    tipoDocumento,
                    numeroDocumento,
                    nombre,
                    apellido
                );

                VENDEDORES.add(vendedor);

                escritor.write(
                    tipoDocumento + ";" + numeroDocumento + ";"
                    + nombre + ";" + apellido
                );

                escritor.newLine();
            }
        }
    }

    /**
     * Genera un archivo de ventas para cada vendedor registrado.
     *
     * @throws IOException si ocurre un error al escribir los archivos
     */
    public static void crearArchivosDeVentas() throws IOException {

        for (Vendedor vendedor : VENDEDORES) {
            int cantidadVentas = RANDOM.nextInt(6) + 5;

            String nombreArchivo = vendedor.getNombre() + "_"
                    + vendedor.getApellido();

            createSalesMenFile(
                cantidadVentas,
                nombreArchivo,
                vendedor.getNumeroDocumento()
            );
        }
    }
    /**
     * Elimina los archivos de ventas anteriores.
     *
     * @throws IOException si no se pueden eliminar los archivos antiguos
     */
    public static void limpiarArchivosDeVentas() throws IOException {
        File carpeta = new File("ventas");

        if (!carpeta.exists()) {
            return;
        }

        File[] archivos = carpeta.listFiles();

        if (archivos == null) {
            throw new IOException("No se pudo leer la carpeta ventas.");
        }

        for (File archivo : archivos) {
            if (archivo.isFile() && archivo.getName().endsWith(".txt")) {
                if (!archivo.delete()) {
                    throw new IOException(
                        "No se pudo eliminar el archivo: " + archivo.getName()
                    );
                }
            }
        }
    }}