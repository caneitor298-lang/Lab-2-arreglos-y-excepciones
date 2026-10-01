import java.util.ArrayList;
import java.util.Locale;

public class Caso {
    private String nombre;
    private String codigo;
    private String detectiveResponsable;
    private Ubicacion[] ubicaciones;
    private ArrayList<Pista> pistas;

    public Caso(String nombre, String codigo, String detectiveResponsable) {
        if (nombre == null || nombre.trim().isEmpty()
                || codigo == null || codigo.trim().isEmpty()
                || detectiveResponsable == null || detectiveResponsable.trim().isEmpty()) {
            throw new IllegalArgumentException("Todos los datos del caso son obligatorios.");
        }
        this.nombre = nombre.trim();
        this.codigo = codigo.trim();
        this.detectiveResponsable = detectiveResponsable.trim();
        ubicaciones = new Ubicacion[5];
        pistas = new ArrayList<Pista>();
    }

    private void validarPosicion(int posicion) {
        if (posicion < 0 || posicion >= ubicaciones.length) {
            throw new IllegalArgumentException("La posición debe estar entre 0 y 4.");
        }
    }

    public void registrarUbicacion(int posicion, Ubicacion ubicacion) {
        validarPosicion(posicion);
        if (ubicaciones[posicion] != null) {
            throw new IllegalArgumentException("La posición seleccionada ya está ocupada.");
        }
        if (ubicacion == null) {
            throw new IllegalArgumentException("La ubicación no puede ser null.");
        }
        ubicaciones[posicion] = ubicacion;
    }

    public String consultarUbicaciones() {
        String resultado = "";
        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null) {
                resultado += "Posición: " + i + "\n"
                        + ubicaciones[i].getInformacion() + "\n--------------------\n";
            }
        }
        if (resultado.isEmpty()) {
            return "No hay ubicaciones registradas.";
        }
        return resultado;
    }

    public Ubicacion obtenerUbicacion(int posicion) {
        validarPosicion(posicion);
        return ubicaciones[posicion];
    }

    public void modificarUbicacion(int posicion, int nivelRiesgo, String estado) {
        Ubicacion ubicacion = obtenerUbicacion(posicion);
        if (ubicacion == null) {
            throw new IllegalArgumentException("La posición está vacía.");
        }
        ubicacion.actualizar(nivelRiesgo, estado);
    }

    public void descartarUbicacion(int posicion) {
        if (obtenerUbicacion(posicion) == null) {
            throw new IllegalArgumentException("La posición está vacía.");
        }
        ubicaciones[posicion] = null;
    }

    public void registrarPista(Pista pista) {
        if (pista == null) {
            throw new IllegalArgumentException("La pista no puede ser null.");
        }
        if (buscarPista(pista.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe una pista con ese código.");
        }
        pistas.add(pista);
    }

    public String consultarPistas() {
        if (pistas.isEmpty()) {
            return "No hay pistas registradas.";
        }
        String resultado = "";
        for (Pista pista : pistas) {
            resultado += pista.getInformacion() + "\n--------------------\n";
        }
        return resultado;
    }

    public Pista buscarPista(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código de búsqueda no puede estar vacío.");
        }
        for (Pista pista : pistas) {
            if (pista.getCodigo().equalsIgnoreCase(codigo.trim())) {
                return pista;
            }
        }
        return null;
    }

    public void modificarPista(String codigo, String descripcion, String tipoEvidencia,
                               int nivelImportancia, int nivelConfiabilidad) {
        Pista pista = buscarPista(codigo);
        if (pista == null) {
            throw new IllegalArgumentException("No existe una pista con ese código.");
        }
        pista.actualizar(descripcion, tipoEvidencia, nivelImportancia, nivelConfiabilidad);
    }

    public void eliminarPista(String codigo) {
        Pista pista = buscarPista(codigo);
        if (pista == null) {
            throw new IllegalArgumentException("No existe una pista con ese código.");
        }
        // La búsqueda ya terminó, por lo que se puede eliminar el objeto.
        pistas.remove(pista);
    }

    public String generarReporte() {
        int cantidadUbicaciones = 0;
        Ubicacion mayorRiesgo = null;
        for (int i = 0; i < ubicaciones.length; i++) {
            if (ubicaciones[i] != null) {
                cantidadUbicaciones++;
                if (mayorRiesgo == null
                        || ubicaciones[i].getNivelRiesgo() > mayorRiesgo.getNivelRiesgo()) {
                    mayorRiesgo = ubicaciones[i];
                }
            }
        }

        String reporte = "===== REPORTE DE INVESTIGACIÓN ====="
                + "\nCaso: " + nombre
                + "\nCódigo: " + codigo
                + "\nDetective responsable: " + detectiveResponsable
                + "\nUbicaciones registradas: " + cantidadUbicaciones
                + "\nEspacios disponibles: " + (ubicaciones.length - cantidadUbicaciones);

        if (mayorRiesgo == null) {
            reporte += "\nUbicación con mayor riesgo: no hay ubicaciones registradas.";
        } else {
            reporte += "\n\nUbicación con mayor riesgo:\n" + mayorRiesgo.getInformacion();
        }
        reporte += "\n\nPistas registradas: " + pistas.size();
        if (pistas.isEmpty()) {
            return reporte + "\nNo hay pistas para calcular máximos o promedio.";
        }

        Pista mayorImportancia = pistas.get(0);
        Pista mayorConfiabilidad = pistas.get(0);
        double sumaImportancia = 0;
        for (Pista pista : pistas) {
            sumaImportancia += pista.getNivelImportancia();
            if (pista.getNivelImportancia() > mayorImportancia.getNivelImportancia()) {
                mayorImportancia = pista;
            }
            if (pista.getNivelConfiabilidad() > mayorConfiabilidad.getNivelConfiabilidad()) {
                mayorConfiabilidad = pista;
            }
        }
        double promedio = sumaImportancia / pistas.size();
        reporte += "\n\nPista con mayor importancia:\n" + mayorImportancia.getInformacion()
                + "\n\nPista con mayor confiabilidad:\n" + mayorConfiabilidad.getInformacion()
                + "\n\nPromedio de importancia: " + String.format(Locale.US, "%.2f", promedio);
        return reporte;
    }
}
