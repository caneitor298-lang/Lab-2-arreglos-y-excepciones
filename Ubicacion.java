public class Ubicacion {
    private String codigo;
    private String nombre;
    private String direccion;
    private int nivelRiesgo;
    private String estado;

    public Ubicacion(String codigo, String nombre, String direccion,
                     int nivelRiesgo, String estado) {
        if (codigo == null || codigo.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()
                || direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("Los datos de la ubicación no pueden estar vacíos.");
        }
        // actualizar valida el riesgo y el estado antes de asignarlos.
        actualizar(nivelRiesgo, estado);
        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.direccion = direccion.trim();
    }

    public int getNivelRiesgo() {
        return nivelRiesgo;
    }

    public void actualizar(int nivelRiesgo, String estado) {
        if (nivelRiesgo < 1 || nivelRiesgo > 10) {
            throw new IllegalArgumentException("El riesgo debe estar entre 1 y 10.");
        }
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede estar vacío.");
        }
        // Primero se validan todos los datos; después se hacen los cambios.
        this.nivelRiesgo = nivelRiesgo;
        this.estado = estado.trim();
    }

    public String getInformacion() {
        return "Código: " + codigo
                + "\nNombre: " + nombre
                + "\nDirección o descripción: " + direccion
                + "\nNivel de riesgo: " + nivelRiesgo
                + "\nEstado: " + estado;
    }
}
