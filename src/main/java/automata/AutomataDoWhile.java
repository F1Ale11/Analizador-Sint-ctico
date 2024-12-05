package automata;

/**
 * Autómata para analizar la estructura do-while.
 */
public class AutomataDoWhile {
    private static int estadoActual;

    public static void procesarDoWhile(String linea) {
        System.out.println("\nProcesando: " + linea);

        if (!linea.startsWith("do")) {
            System.out.println("La línea no es válida: no comienza con 'do'.");
            return;
        }

        String[] tokens = linea.split("(?<=\\{|\\}|\\(|\\)|;|\\s)|(?=\\{|\\}|\\(|\\)|;|\\s)");
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
                if (token.equals("do")) {
                    estadoActual = 1;
                    return true;
                }
            }
            case 1 -> { // Estado q1
                if (token.equals("{")) {
                    estadoActual = 2;
                    return true;
                }
            }
            case 2 -> { // Estado q2
                if (token.equals("}")) {
                    estadoActual = 3;
                    return true;
                } else {
                    // Aceptar cualquier token dentro del bloque
                    return true; // Permanecer en q2
                }
            }
            case 3 -> { // Estado q3
                if (token.equals("while")) {
                    estadoActual = 4;
                    return true;
                }
            }
            case 4 -> { // Estado q4
                if (token.equals("(")) {
                    estadoActual = 5;
                    return true;
                }
            }
            case 5 -> { // Estado q5
                if (token.equals(")")) {
                    estadoActual = 6;
                    return true;
                } else {
                    // Aceptar cualquier token como parte de la condición
                    return true; // Permanecer en q5
                }
            }
            case 6 -> { // Estado q6
                if (token.equals(";")) {
                    estadoActual = 7;
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean esEstadoDeAceptacion() {
        return estadoActual == 7; // Estado de aceptación
    }
}
