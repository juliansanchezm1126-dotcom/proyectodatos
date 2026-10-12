package proyectodatos;

/**
 * Representa a un vendedor del sistema.
 * Almacena sus datos personales y el dinero recaudado por sus ventas.
 */
public class Vendedor {

    private String tipoDocumento;
    private long numeroDocumento;
    private String nombre;
    private String apellido;
    private double dineroRecaudado;

    /**
     * Crea un vendedor con sus datos personales.
     *
     * @param tipoDocumento tipo de documento de identidad
     * @param numeroDocumento número de documento
     * @param nombre nombre del vendedor
     * @param apellido apellido del vendedor
     * @throws IllegalArgumentException si algún dato es inválido
     */
    public Vendedor(String tipoDocumento, long numeroDocumento,
                    String nombre, String apellido) {

        if (tipoDocumento == null || tipoDocumento.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El tipo de documento no puede estar vacío."
            );
        }

        if (numeroDocumento <= 0) {
            throw new IllegalArgumentException(
                "El número de documento debe ser positivo."
            );
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El nombre no puede estar vacío."
            );
        }

        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "El apellido no puede estar vacío."
            );
        }

        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dineroRecaudado = 0;
    }

    /**
     * Obtiene el tipo de documento.
     *
     * @return tipo de documento
     */
    public String getTipoDocumento() {
        return tipoDocumento;
    }

    /**
     * Obtiene el número de documento.
     *
     * @return número de documento
     */
    public long getNumeroDocumento() {
        return numeroDocumento;
    }

    /**
     * Obtiene el nombre del vendedor.
     *
     * @return nombre del vendedor
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el apellido del vendedor.
     *
     * @return apellido del vendedor
     */
    public String getApellido() {
        return apellido;
    }

    /**
     * Obtiene el dinero recaudado.
     *
     * @return total recaudado por el vendedor
     */
    public double getDineroRecaudado() {
        return dineroRecaudado;
    }

    /**
     * Agrega dinero al total recaudado.
     *
     * @param dinero cantidad de dinero que se agregará
     * @throws IllegalArgumentException si el valor no es válido
     */
    public void agregarDinero(double dinero) {

        if (!Double.isFinite(dinero) || dinero < 0) {
            throw new IllegalArgumentException(
                "El dinero debe ser válido y no negativo."
            );
        }

        if (!Double.isFinite(dineroRecaudado + dinero)) {
            throw new IllegalArgumentException(
                "El total recaudado supera el límite permitido."
            );
        }

        dineroRecaudado += dinero;
    }
}