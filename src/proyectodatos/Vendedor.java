package proyectodatos;

public class Vendedor {

    private String tipoDocumento;
    private long numeroDocumento;
    private String nombre;
    private String apellido;
    private double dineroRecaudado;

    public Vendedor(String tipoDocumento, long numeroDocumento,
                    String nombre, String apellido) {

        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dineroRecaudado = 0;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public long getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public double getDineroRecaudado() {
        return dineroRecaudado;
    }

    public void agregarDinero(double dinero) {
        dineroRecaudado = dineroRecaudado + dinero;
    }
}