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
import java.util.List;
public class main {
	public static void main(String[] args) {

    System.out.println("INICIO DEL PROCESAMIENTO");

    try {

        List<Producto> productos = leerProductos();

        List<Vendedor> vendedores = leerVendedores();

        procesarVentas(productos, vendedores);

        crearReporteVendedores(vendedores);

        crearReporteProductos(productos);

        System.out.println(
            "PROCESO FINALIZADO CORRECTAMENTE."
        );

    } catch (IOException e) {

        System.out.println(
            "ERROR AL PROCESAR LOS ARCHIVOS."
        );

        System.out.println(
            "Detalle: " + e.getMessage()
        );
    }
}public static List<Producto> leerProductos()
        throws IOException {

    List<Producto> productos =
            new ArrayList<Producto>();

    BufferedReader lector =
            new BufferedReader(
                new FileReader("productos.txt")
            );

    String linea;

    while ((linea = lector.readLine()) != null) {

        String[] datos = linea.split(";");

        int id =
                Integer.parseInt(datos[0]);

        String nombre =
                datos[1];

        double precio =
                Double.parseDouble(datos[2]);

        Producto producto =
                new Producto(
                    id,
                    nombre,
                    precio
                );

        productos.add(producto);
    }

    lector.close();

    return productos;
}public static List<Vendedor> leerVendedores()
        throws IOException {

    List<Vendedor> vendedores =
            new ArrayList<Vendedor>();

    BufferedReader lector =
            new BufferedReader(
                new FileReader("vendedores.txt")
            );

    String linea;

    while ((linea = lector.readLine()) != null) {

        String[] datos = linea.split(";");

        String tipoDocumento =
                datos[0];

        long numeroDocumento =
                Long.parseLong(datos[1]);

        String nombre =
                datos[2];

        String apellido =
                datos[3];

        Vendedor vendedor =
                new Vendedor(
                    tipoDocumento,
                    numeroDocumento,
                    nombre,
                    apellido
                );

        vendedores.add(vendedor);
    }

    lector.close();

    return vendedores;
}public static void procesarVentas(
        List<Producto> productos,
        List<Vendedor> vendedores)
        throws IOException {

    File carpeta = new File("ventas");

    File[] archivos = carpeta.listFiles();

    if (archivos == null) {
        return;
    }

    for (File archivo : archivos) {

        if (!archivo.isFile()) {
            continue;
        }

        BufferedReader lector =
                new BufferedReader(
                    new FileReader(archivo)
                );

        // Primera linea: tipo de documento y numero de documento
        String primeraLinea =
                lector.readLine();

        if (primeraLinea == null) {
            lector.close();
            continue;
        }

        String[] datosVendedor =
                primeraLinea.split(";");

        long numeroDocumento =
                Long.parseLong(datosVendedor[1]);

        Vendedor vendedorEncontrado = null;

        // Buscar el vendedor
        for (Vendedor vendedor : vendedores) {

            if (vendedor.getNumeroDocumento()
                    == numeroDocumento) {

                vendedorEncontrado = vendedor;
                break;
            }
        }

        String linea;

        // Leer las ventas del vendedor
        while ((linea = lector.readLine()) != null) {

            String[] datos =
                    linea.split(";");

            int idProducto =
                    Integer.parseInt(datos[0]);

            int cantidad =
                    Integer.parseInt(datos[1]);

            // Buscar el producto
            for (Producto producto : productos) {

                if (producto.getId() == idProducto) {

                    double dinero =
                            producto.getPrecio()
                            * cantidad;

                    if (vendedorEncontrado != null) {

                        vendedorEncontrado
                            .agregarDinero(dinero);
                    }

                    producto.agregarCantidad(cantidad);

                    break;
                }
            }
        }

        lector.close();
    }
}public static void crearReporteVendedores(
        List<Vendedor> vendedores)
        throws IOException {

    Collections.sort(
        vendedores,
        new Comparator<Vendedor>() {

            @Override
            public int compare(
                    Vendedor vendedor1,
                    Vendedor vendedor2) {

                return Double.compare(
                    vendedor2.getDineroRecaudado(),
                    vendedor1.getDineroRecaudado()
                );
            }
        }
    );

    BufferedWriter escritor =
            new BufferedWriter(
                new FileWriter(
                    "reporte_vendedores.csv"
                )
            );

    for (Vendedor vendedor : vendedores) {

        escritor.write(
            vendedor.getNombre()
            + " "
            + vendedor.getApellido()
            + ";"
            + vendedor.getDineroRecaudado()
        );

        escritor.newLine();
    }

    escritor.close();
}public static void crearReporteProductos(
        List<Producto> productos)
        throws IOException {

    Collections.sort(
        productos,
        new Comparator<Producto>() {

            @Override
            public int compare(
                    Producto producto1,
                    Producto producto2) {

                return Integer.compare(
                    producto2.getCantidadVendida(),
                    producto1.getCantidadVendida()
                );
            }
        }
    );

    BufferedWriter escritor =
            new BufferedWriter(
                new FileWriter(
                    "reporte_productos.csv"
                )
            );

    for (Producto producto : productos) {

    	escritor.write(
    		    producto.getNombre()
    		    + ";"
    		    + producto.getPrecio()
    		);

        escritor.newLine();
    }

    escritor.close();
}

}
