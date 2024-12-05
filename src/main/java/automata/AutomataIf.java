/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package automata;

/**
 *
 * @author alext
 */
public class AutomataIf {
    private static int estadoActual;

    public static void procesarIf(String linea) {
        System.out.println("\nProcesando: " + linea);

        if (!linea.startsWith("if")) {
            System.out.println("La línea no es válida: no comienza con 'if'.");
            return;
        }

        String[] tokens = linea.split("(?<=\\(|\\)|\\s)|(?=\\(|\\)|\\s)");
        estadoActual = 1; // Estado inicial q1
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
            case 1 -> { // Estado q1
                if (token.equals("if")) {
                    estadoActual = 2; 
                    return true;
                }
            }
            case 2 -> { // Estado q2
                if (token.equals("(")) {
                    estadoActual = 3; 
                    return true;
                }
                        }
            case 3 -> { // Estado q3
                if (token.equals("var")) {
                    estadoActual = 4; 
                    return true;
                }
            }
            case 4 -> { // Estado q4
                if (esVariableValida(token)) {
                    estadoActual = 5; 
                    return true;
                }
            }
            case 5 -> { // Estado q5
                if (esOperadorValido(token)) {
                    estadoActual = 6; 
                    return true;
                }
            }
            case 6 -> { // Estado q6
                if (token.equals("var")) {
                    estadoActual = 7; 
                    return true;
                }
            }
            case 7 -> { // Estado q7
                if (esVariableValida(token)) {
                    estadoActual = 8; 
                    return true;
                }
            }
            case 8 -> { // Estado q8
                if (token.equals(")")) {
                    estadoActual = 9; 
                    return true;
                }
            }
            case 9 -> { // Estado q9
                if (token.equals("else")) {
                    estadoActual = 10; 
                    return true;
                } else if (token.equals("if")) {
                    estadoActual = 2; 
                    return true;
                }
            }
            case 10 -> { // Estado q10
                if (token.equals("if")) {
                    estadoActual = 2; 
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean esEstadoDeAceptacion() {
        return estadoActual == 9 || estadoActual == 10;
    }

    private static boolean esVariableValida(String token) {
        // Validar nombres de variables que sigan el formato estándar
        return token.matches("[a-zA-Z_][a-zA-Z0-9_]*");
    }

    private static boolean esOperadorValido(String token) {
        // Validar operadores relacionales
        return token.matches("==|!=|<=|>=|<|>");
    }



}
