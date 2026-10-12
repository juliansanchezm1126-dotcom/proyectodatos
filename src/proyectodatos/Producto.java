package proyectodatos;

/**
 * Representa un producto que puede venderse en el sistema.
 * Guarda su identificador, nombre, precio y cantidad vendida.
 *
 * @author
 */
public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int cantidadVendida;

    /**
     * Crea un producto con sus datos iniciales.
     *
     * @param id identificador único del producto
     * @param nombre nombre del producto
     * @param precio precio unitario del producto
     * @throws IllegalArgumentException si los datos son inválidos
     */
    public Producto(int id, String nombre, double precio) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "El identificador debe ser positivo."
            );
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El nombre del producto no puede estar vacío."
            );
        }

        if (!Double.isFinite(precio) || precio < 0) {
            throw new IllegalArgumentException(
                "El precio debe ser válido y no negativo."
            );
        }

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadVendida = 0;
    }

    /**
     * Obtiene el identificador del producto.
     *
     * @return identificador del producto
     */
    public int getId() {
        return id;
    }

    /**
     * Obtiene el nombre del producto.
     *
     * @return nombre del producto
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el precio unitario.
     *
     * @return precio del producto
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Obtiene la cantidad total vendida.
     *
     * @return unidades vendidas
     */
    public int getCantidadVendida() {
        return cantidadVendida;
    }

    /**
     * Suma unidades a la cantidad vendida.
     *
     * @param cantidad unidades que se agregarán
     * @throws IllegalArgumentException si la cantidad es negativa
     */
    public void agregarCantidad(int cantidad) {

        if (cantidad < 0) {
            throw new IllegalArgumentException(
                "La cantidad no puede ser negativa."
            );
        }

        if (cantidad > Integer.MAX_VALUE - cantidadVendida) {
            throw new IllegalArgumentException(
                "La cantidad total supera el límite permitido."
            );
        }

        cantidadVendida += cantidad;
    }
}