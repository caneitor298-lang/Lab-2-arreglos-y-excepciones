import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Caso casoActual;

    public static void main(String[] args) {
        try {
            System.out.println("===== AGENCIA DE DETECTIVES =====");
            // Si los datos iniciales son inválidos, se solicitan nuevamente.
            while (casoActual == null) {
                try {
                    casoActual = crearCaso();
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }

            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                int opcion = leerEntero("Seleccione una opción: ");
                try {
                    switch (opcion) {
                        case 1: {
                            // El caso anterior se reemplaza solo si el nuevo es válido.
                            casoActual = crearCaso();
                            System.out.println("Nuevo caso creado sin ubicaciones ni pistas.");
                            break;
                        }
                        case 2: {
                            int posicion = leerEntero("Posición del arreglo (0 a 4): ");
                            if (casoActual.obtenerUbicacion(posicion) != null) {
                                throw new IllegalArgumentException("La posición ya está ocupada.");
                            }
                            String codigo = leerTexto("Código de la ubicación: ");
                            String nombre = leerTexto("Nombre del lugar: ");
                            String direccion = leerTexto("Dirección o descripción: ");
                            int riesgo = leerEntero("Nivel de riesgo (1 a 10): ");
                            String estado = leerTexto("Estado: ");
                            Ubicacion ubicacion = new Ubicacion(codigo, nombre, direccion, riesgo, estado);
                            casoActual.registrarUbicacion(posicion, ubicacion);
                            System.out.println("Ubicación registrada.");
                            break;
                        }
                        case 3: {
                            System.out.println(casoActual.consultarUbicaciones());
                            break;
                        }
                        case 4: {
                            int posicion = leerEntero("Posición del arreglo (0 a 4): ");
                            Ubicacion ubicacion = casoActual.obtenerUbicacion(posicion);
                            if (ubicacion == null) {
                                System.out.println("La posición está vacía.");
                            } else {
                                System.out.println(ubicacion.getInformacion());
                            }
                            break;
                        }
                        case 5: {
                            int posicion = leerEntero("Posición del arreglo (0 a 4): ");
                            if (casoActual.obtenerUbicacion(posicion) == null) {
                                throw new IllegalArgumentException("La posición está vacía.");
                            }
                            int riesgo = leerEntero("Nuevo riesgo (1 a 10): ");
                            String estado = leerTexto("Nuevo estado: ");
                            casoActual.modificarUbicacion(posicion, riesgo, estado);
                            System.out.println("Ubicación modificada.");
                            break;
                        }
                        case 6: {
                            int posicion = leerEntero("Posición del arreglo (0 a 4): ");
                            casoActual.descartarUbicacion(posicion);
                            System.out.println("Ubicación descartada. La posición queda disponible.");
                            break;
                        }
                        case 7: {
                            String codigo = leerTexto("Código de la pista: ");
                            if (casoActual.buscarPista(codigo) != null) {
                                throw new IllegalArgumentException("Ya existe una pista con ese código.");
                            }
                            String descripcion = leerTexto("Descripción: ");
                            String tipo = leerTexto("Tipo de evidencia: ");
                            int importancia = leerEntero("Importancia (1 a 10): ");
                            int confiabilidad = leerEntero("Confiabilidad (0 a 100): ");
                            Pista pista = new Pista(codigo, descripcion, tipo, importancia, confiabilidad);
                            casoActual.registrarPista(pista);
                            System.out.println("Pista registrada.");
                            break;
                        }
                        case 8: {
                            System.out.println(casoActual.consultarPistas());
                            break;
                        }
                        case 9: {
                            String codigo = leerTexto("Código de la pista: ");
                            Pista pista = casoActual.buscarPista(codigo);
                            if (pista == null) {
                                System.out.println("No existe una pista con ese código.");
                            } else {
                                System.out.println(pista.getInformacion());
                            }
                            break;
                        }
                        case 10: {
                            String codigo = leerTexto("Código de la pista que desea modificar: ");
                            if (casoActual.buscarPista(codigo) == null) {
                                throw new IllegalArgumentException("No existe una pista con ese código.");
                            }
                            String descripcion = leerTexto("Nueva descripción: ");
                            String tipo = leerTexto("Nuevo tipo de evidencia: ");
                            int importancia = leerEntero("Nueva importancia (1 a 10): ");
                            int confiabilidad = leerEntero("Nueva confiabilidad (0 a 100): ");
                            casoActual.modificarPista(codigo, descripcion, tipo, importancia, confiabilidad);
                            System.out.println("Pista modificada.");
                            break;
                        }
                        case 11: {
                            String codigo = leerTexto("Código de la pista que desea eliminar: ");
                            casoActual.eliminarPista(codigo);
                            System.out.println("Pista eliminada.");
                            break;
                        }
                        case 12: {
                            System.out.println(casoActual.generarReporte());
                            break;
                        }
                        case 13: {
                            continuar = false;
                            System.out.println("Programa finalizado.");
                            break;
                        }
                        default: {
                            System.out.println("Seleccione una opción entre 1 y 13.");
                        }
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (NoSuchElementException e) {
            // También permite cerrar el programa si la entrada de consola termina.
            System.out.println("\nEntrada finalizada. Se cierra el programa.");
        } finally {
            // Se libera el recurso tanto al salir normalmente como ante un error.
            scanner.close();
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n===== MENÚ DEL CASO =====");
        System.out.println("1. Nuevo caso");
        System.out.println("2. Registrar ubicación");
        System.out.println("3. Consultar ubicaciones");
        System.out.println("4. Consultar una ubicación");
        System.out.println("5. Modificar ubicación");
        System.out.println("6. Descartar ubicación");
        System.out.println("7. Registrar pista");
        System.out.println("8. Consultar pistas");
        System.out.println("9. Buscar pista");
        System.out.println("10. Modificar pista");
        System.out.println("11. Eliminar pista");
        System.out.println("12. Mostrar reporte de investigación");
        System.out.println("13. Salir");
    }

    private static Caso crearCaso() {
        String nombre = leerTexto("Nombre del caso: ");
        String codigo = leerTexto("Código del caso: ");
        String detective = leerTexto("Detective responsable: ");
        return new Caso(nombre, codigo, detective);
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int numero = scanner.nextInt();
                scanner.nextLine(); // Consume el salto de línea después del número.
                return numero;
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Descarta la entrada incorrecta.
                System.out.println("Entrada incorrecta. Debe escribir un número entero.");
            }
        }
    }
}
