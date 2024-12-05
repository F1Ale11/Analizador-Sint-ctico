package automata;

/**
 * Autómata para analizar la estructura for.
 */
public class ForAutomata {
    private static int estadoActual;

    public static void procesarFor(String linea) {
        System.out.println("\nProcesando: " + linea);

        if (!linea.startsWith("for")) {
            System.out.println("La línea no es válida: no comienza con 'for'.");
            return;
        }

        String[] tokens = linea.split("(?<=\\(|\\)|;|\\s|\\{|\\}|\\+\\+)|(?=\\(|\\)|;|\\s|\\{|\\}|\\+\\+)");
        estadoActual = 0; // Estado inicial q0
        System.out.println("Estado inicial: q" + estadoActual);

        for (String token : tokens) {
            token = token.trim();
            if (token.isEmpty()) continue;

            if (!transicionarEstado(token)) {
                System.out.println("Error: El token '" + token + "' no es válido en el estado q" + estadoActual);
                return;
            }
            System.out.println("Después de procesar '" + token + "': Estado actual -> q" + estadoActual);
        }

        if (esEstadoDeAceptacion()) {
            System.out.println("La secuencia fue aceptada.");
        } else {
            System.out.println("La secuencia fue rechazada.");
        }
    }

    private static boolean transicionarEstado(String token) {
        switch (estadoActual) {
            case 0 -> { // Estado q0
                if (token.equals("for")) {
                    estadoActual = 1;
                    return true;
                }
            }
            case 1 -> { // Estado q1
                if (token.equals("(")) {
                    estadoActual = 2;
                    return true;
                }
            }
            case 2 -> { // Estado q2 - Inicialización
                if (token.equals(";")) {
                    estadoActual = 3;
                    return true;
                } else {
                    // Aceptar cualquier token como parte de la inicialización
                    return true; // Permanecer en q2
                }
            }
            case 3 -> { // Estado q3 - Transición a condición
                if (!token.equals(";")) {
                    estadoActual = 4;
                    // Procesar el token actual en el estado q4
                    return transicionarEstado(token);
                }
            }
            case 4 -> { // Estado q4 - Condición
                if (token.equals(";")) {
                    estadoActual = 5;
                    return true;
                } else {
                    // Aceptar cualquier token como parte de la condición
                    return true; // Permanecer en q4
                }
            }
            case 5 -> { // Estado q5 - Transición a actualización
                if (!token.equals(")")) {
                    estadoActual = 6;
                    // Procesar el token actual en el estado q6
                    return transicionarEstado(token);
                } else {
                    estadoActual = 7;
                    return true;
                }
            }
            case 6 -> { // Estado q6 - Actualización
                if (token.equals(")")) {
                    estadoActual = 7;
                    return true;
                } else {
                    // Aceptar cualquier token como parte de la actualización
                    return true; // Permanecer en q6
                }
            }
            case 7 -> { // Estado q7
                if (token.equals("{")) {
                    estadoActual = 8;
                    return true;
                } else {
                    System.out.println("Error: Se esperaba '{' después de ')'");
                    return false;
                }
            }
            case 8 -> { // Estado q8 - Cuerpo del bucle
                if (token.equals("}")) {
                    estadoActual = 9;
                    return true;
                } else {
                    // Aceptar cualquier token dentro del cuerpo del bucle
                    return true; // Permanecer en q8
                }
            }
            case 9 -> { // Estado q9 - Estado de aceptación
                // No se esperan más tokens
                return false;
            }
        }
        return false;
    }

    private static boolean esEstadoDeAceptacion() {
        return estadoActual == 9; // Estado de aceptación
    }
}
