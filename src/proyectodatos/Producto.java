package proyectodatos;



public class Producto {

    private int id;
    private String nombre;
    private double precio;
    private int cantidadVendida;

    public Producto(int id, String nombre, double precio) {

        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidadVendida = 0;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void agregarCantidad(int cantidad) {
        cantidadVendida = cantidadVendida + cantidad;
    }
}