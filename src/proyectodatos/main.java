package proyectodatos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Procesa los archivos de ventas y genera los reportes
 * de dinero recaudado por vendedor y productos vendidos.
 */
public class main {

    /**
     * Ejecuta el procesamiento completo de las ventas.
     *
     * @param args argumentos de ejecución
     */
    public static void main(String[] args) {

        System.out.println("INICIO DEL PROCESAMIENTO");

        try {
            List<Producto> productos = leerProductos();
            List<Vendedor> vendedores = leerVendedores();

            validarProductos(productos);
            validarVendedores(vendedores);
            procesarVentas(productos, vendedores);

            crearReporteVendedores(vendedores);
            crearReporteProductos(productos);

            System.out.println(
                "PROCESO FINALIZADO CORRECTAMENTE."
            );

        } catch (IOException | IllegalArgumentException e) {
            System.out.println("ERROR AL PROCESAR LOS ARCHIVOS.");
            System.out.println("Detalle: " + e.getMessage());
        }
    }

    /**
     * Lee los productos desde productos.txt.
     *
     * @return lista de productos registrados
     * @throws IOException si el archivo no existe o tiene datos inválidos
     */
    public static List<Producto> leerProductos() throws IOException {

        List<Producto> productos = new ArrayList<Producto>();

        try (BufferedReader lector =
                new BufferedReader(new FileReader("productos.txt"))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numeroLinea++;

                String[] datos = linea.split(";", -1);

                if (datos.length != 3) {
                    throw new IOException(
                        "Formato incorrecto en productos.txt, línea "
                        + numeroLinea
                    );
                }

                try {
                    int id = Integer.parseInt(datos[0].trim());
                    String nombre = datos[1].trim();
                    double precio = Double.parseDouble(datos[2].trim());

                    productos.add(new Producto(id, nombre, precio));

                } catch (IllegalArgumentException e) {
                    throw new IOException(
                        "Datos inválidos en productos.txt, línea "
                        + numeroLinea
                    );
                }
            }
        }

        if (productos.isEmpty()) {
            throw new IOException(
                "El archivo productos.txt está vacío."
            );
        }

        return productos;
    }

    /**
     * Lee los vendedores desde vendedores.txt.
     *
     * @return lista de vendedores registrados
     * @throws IOException si el archivo no existe o contiene errores
     */
    public static List<Vendedor> leerVendedores() throws IOException {

        List<Vendedor> vendedores = new ArrayList<Vendedor>();

        try (BufferedReader lector =
                new BufferedReader(new FileReader("vendedores.txt"))) {

            String linea;
            int numeroLinea = 0;

            while ((linea = lector.readLine()) != null) {
                numeroLinea++;

                String[] datos = linea.split(";", -1);

                if (datos.length != 4) {
                    throw new IOException(
                        "Formato incorrecto en vendedores.txt, línea "
                        + numeroLinea
                    );
                }

                try {
                    String tipoDocumento = datos[0].trim();
                    long numeroDocumento =
                            Long.parseLong(datos[1].trim());
                    String nombre = datos[2].trim();
                    String apellido = datos[3].trim();

                    vendedores.add(new Vendedor(
                        tipoDocumento,
                        numeroDocumento,
                        nombre,
                        apellido
                    ));

                } catch (IllegalArgumentException e) {
                    throw new IOException(
                        "Datos inválidos en vendedores.txt, línea "
                        + numeroLinea
                    );
                }
            }
        }

        if (vendedores.isEmpty()) {
            throw new IOException(
                "El archivo vendedores.txt está vacío."
            );
        }

        return vendedores;
    }

    /**
     * Comprueba que los identificadores de los productos no se repitan.
     *
     * @param productos productos que se van a validar
     * @throws IOException si hay identificadores duplicados
     */
    public static void validarProductos(List<Producto> productos)
            throws IOException {

        Set<Integer> identificadores = new HashSet<Integer>();

        for (Producto producto : productos) {
            if (!identificadores.add(producto.getId())) {
                throw new IOException(
                    "Identificador de producto duplicado: "
                    + producto.getId()
                );
            }
        }
    }

    /**
     * Comprueba que los números de documento de los vendedores
     * no se repitan.
     *
     * @param vendedores vendedores que se van a validar
     * @throws IOException si hay documentos duplicados
     */
    public static void validarVendedores(List<Vendedor> vendedores)
            throws IOException {

        Set<Long> documentos = new HashSet<Long>();

        for (Vendedor vendedor : vendedores) {
            if (!documentos.add(vendedor.getNumeroDocumento())) {
                throw new IOException(
                    "Documento de vendedor duplicado: "
                    + vendedor.getNumeroDocumento()
                );
            }
        }
    }

    /**
     * Lee todos los archivos de ventas de la carpeta ventas.
     * Cada archivo contiene primero el documento del vendedor
     * y después una línea por venta con identificador y cantidad.
     *
     * @param productos productos disponibles
     * @param vendedores vendedores registrados
     * @throws IOException si hay archivos o datos incorrectos
     */
    public static void procesarVentas(
            List<Producto> productos,
            List<Vendedor> vendedores) throws IOException {

        File carpeta = new File("ventas");

        if (!carpeta.isDirectory()) {
            throw new IOException(
                "No existe la carpeta de ventas."
            );
        }

        File[] archivos = carpeta.listFiles();
        System.out.println("Carpeta de ventas leída: " + carpeta.getAbsolutePath());
        if (archivos == null || archivos.length == 0) {
            throw new IOException(
                "La carpeta ventas está vacía o no se puede leer."
            );
        }

        for (File archivo : archivos) {
            if (!archivo.isFile() ||
                    !archivo.getName().toLowerCase().endsWith(".txt")) {
                continue;
            }

            try (BufferedReader lector =
                    new BufferedReader(new FileReader(archivo))) {

                String primeraLinea = lector.readLine();

                if (primeraLinea == null) {
                    throw new IOException(
                        "Archivo de ventas vacío: " + archivo.getName()
                    );
                }

                String[] datosVendedor = primeraLinea.split(";", -1);

                if (datosVendedor.length != 2) {
                    throw new IOException(
                        "Encabezado inválido en " + archivo.getName()
                    );
                }

                long numeroDocumento;

                try {
                    numeroDocumento =
                            Long.parseLong(datosVendedor[1].trim());
                } catch (NumberFormatException e) {
                    throw new IOException(
                        "Documento inválido en " + archivo.getName()
                    );
                }

                Vendedor vendedorEncontrado = null;

                for (Vendedor vendedor : vendedores) {
                    if (vendedor.getNumeroDocumento()
                            == numeroDocumento) {
                        vendedorEncontrado = vendedor;
                        break;
                    }
                }

                if (vendedorEncontrado == null) {
                    throw new IOException(
                        "Vendedor no registrado en "
                        + archivo.getName()
                    );
                }

                String linea;
                int numeroLinea = 1;

                while ((linea = lector.readLine()) != null) {
                    numeroLinea++;

                    String[] datos = linea.split(";", -1);

                    if (datos.length != 2) {
                        throw new IOException(
                            "Venta inválida en " + archivo.getName()
                            + ", línea " + numeroLinea
                        );
                    }

                    int idProducto;
                    int cantidad;

                    try {
                        idProducto = Integer.parseInt(datos[0].trim());
                        cantidad = Integer.parseInt(datos[1].trim());
                    } catch (NumberFormatException e) {
                        throw new IOException(
                            "Valores inválidos en " + archivo.getName()
                            + ", línea " + numeroLinea
                        );
                    }

                    if (cantidad <= 0) {
                        throw new IOException(
                            "La cantidad debe ser positiva en "
                            + archivo.getName() + ", línea " + numeroLinea
                        );
                    }

                    Producto productoEncontrado = null;

                    for (Producto producto : productos) {
                        if (producto.getId() == idProducto) {
                            productoEncontrado = producto;
                            break;
                        }
                    }

                    if (productoEncontrado == null) {
                        throw new IOException(
                            "Producto " + idProducto
                            + " no registrado en " + archivo.getName()
                        );
                    }

                    double dinero =
                            productoEncontrado.getPrecio() * cantidad;

                    if (!Double.isFinite(dinero)) {
                        throw new IOException(
                            "Valor de venta inválido en "
                            + archivo.getName()
                        );
                    }

                    vendedorEncontrado.agregarDinero(dinero);
                    productoEncontrado.agregarCantidad(cantidad);
                }
            }
        }
    }

    /**
     * Genera el reporte de vendedores ordenado por dinero recaudado,
     * de mayor a menor.
     *
     * @param vendedores vendedores que aparecerán en el reporte
     * @throws IOException si no se puede escribir el archivo
     */
    public static void crearReporteVendedores(
            List<Vendedor> vendedores) throws IOException {

        Collections.sort(
            vendedores,
            new Comparator<Vendedor>() {
                @Override
                public int compare(Vendedor v1, Vendedor v2) {
                    return Double.compare(
                        v2.getDineroRecaudado(),
                        v1.getDineroRecaudado()
                    );
                }
            }
        );

        try (BufferedWriter escritor = new BufferedWriter(
                new FileWriter("reporte_vendedores.csv"))) {

            escritor.write(
                "TipoDocumento;NumeroDocumento;Nombre;Apellido;TotalRecaudado"
            );
            escritor.newLine();

            for (Vendedor vendedor : vendedores) {
                escritor.write(
                    vendedor.getTipoDocumento() + ";"
                    + vendedor.getNumeroDocumento() + ";"
                    + vendedor.getNombre() + ";"
                    + vendedor.getApellido() + ";"
                    + vendedor.getDineroRecaudado()
                );
                escritor.newLine();
            }
        }
    }

    /**
     * Genera el reporte de productos ordenado por cantidad vendida,
     * de mayor a menor.
     *
     * @param productos productos que aparecerán en el reporte
     * @throws IOException si no se puede escribir el archivo
     */
    public static void crearReporteProductos(
            List<Producto> productos) throws IOException {

        Collections.sort(
            productos,
            new Comparator<Producto>() {
                @Override
                public int compare(Producto p1, Producto p2) {
                    return Integer.compare(
                        p2.getCantidadVendida(),
                        p1.getCantidadVendida()
                    );
                }
            }
        );

        try (BufferedWriter escritor = new BufferedWriter(
                new FileWriter("reporte_productos.csv"))) {

            escritor.write(
                "Id;Nombre;PrecioUnitario;CantidadVendida"
            );
            escritor.newLine();

            for (Producto producto : productos) {
                escritor.write(
                    producto.getId() + ";"
                    + producto.getNombre() + ";"
                    + producto.getPrecio() + ";"
                    + producto.getCantidadVendida()
                );
                escritor.newLine();
            }
        }
    }
}