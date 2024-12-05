/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automata;

/**
 *
 * @author alext
 */
public class ReadAutomata {
    private static int estadoActual; 

    public static void procesarRead(String linea) {
        System.out.println("\nProcesando: " + linea);

        if (!linea.startsWith("read")) {
            System.out.println("La línea no es válida: no comienza con 'read'.");
            return;
        }

        
        String[] tokens = linea.split("(?<=\\(|\\)|,|;)|(?=\\(|\\)|,|;)");
        estadoActual = 0; // Estado inicial q0
        System.out.println("Estado inicial: q" + estadoActual);

        for (String token : tokens) {
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
            case 0 -> {
                if (token.equals("read")) {
                    estadoActual = 1;
                    return true;
                }
            }
            case 1 -> {
                if (token.equals("(")) {
                    estadoActual = 2;
                    return true;
                }
            }
            case 2 -> {
                if (esVariableValida(token)) {
                    estadoActual = 3;
                    return true;
                }
            }
            case 3 -> {
                if (token.equals(",")) {
                    estadoActual = 2;
                    return true;
                } else if (token.equals(")")) {
                    estadoActual = 4;
                    return true;
                }
            }
            case 4 -> {
                if (token.equals(";")) {
                    estadoActual = 5;
                    return true;
                }
            }
        }
        return false; 
    }

    private static boolean esEstadoDeAceptacion() {
        return estadoActual == 5; //Estado de aceptacion
    }

    private static boolean esVariableValida(String token) {
        return token.matches("[a-zA-Z_][a-zA-Z0-9_]*"); // nombre de variables
    }
}
