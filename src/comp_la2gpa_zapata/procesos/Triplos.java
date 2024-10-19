/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp_la2gpa_zapata.procesos;

import comp_la2gpa_zapata.ventanas.Ventana;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author corin
 */
public class Triplos {
     private ArrayList<String> triplos;
    private int idxCounter;  // Contador para índices temporales

    // Constructor
    public Triplos() {
        this.triplos = new ArrayList<>();
        this.idxCounter = 1;
    }

    // Método para calcular los triplos de una lista de lexemas
    public void calcularTriplos(ArrayList<String> lexemas) {
        Stack<String> pilaOperadores = new Stack<>();  // Pila para los operadores
        Stack<String> pilaOperandos = new Stack<>();   // Pila para los operandos

        for (String lexema : lexemas) {
            int tipoLexema = Archivos.getIDLex(lexema);

            if (tipoLexema == 200) {  // Si es un número
                pilaOperandos.push(lexema);  // Agregar el número a la pila de operandos
            } else if (tipoLexema == 100) {  // Si es un identificador
                pilaOperandos.push(lexema);  // Agregar el identificador a la pila de operandos
            } else if (lexema.equals("(")) {
                pilaOperadores.push(lexema);  // Agregar paréntesis de apertura
            } else if (lexema.equals(")")) {
                // Procesar operadores hasta encontrar el paréntesis de apertura
                while (!pilaOperadores.isEmpty() && !pilaOperadores.peek().equals("(")) {
                    procesarOperador(pilaOperadores, pilaOperandos);
                }
                pilaOperadores.pop();  // Eliminar el paréntesis de apertura
            } else {  // Es un operador
                // Mientras haya operadores en la pila con mayor o igual precedencia, procesar
                while (!pilaOperadores.isEmpty() && tieneMayorPrecedencia(pilaOperadores.peek(), lexema)) {
                    procesarOperador(pilaOperadores, pilaOperandos);
                }
                pilaOperadores.push(lexema);  // Agregar el nuevo operador a la pila
            }
        }

        // Procesar los operadores restantes
        while (!pilaOperadores.isEmpty()) {
            procesarOperador(pilaOperadores, pilaOperandos);
        }
    }

    // Función auxiliar para procesar operadores
    private void procesarOperador(Stack<String> pilaOperadores, Stack<String> pilaOperandos) {
        String operador = pilaOperadores.pop();
        String operando2 = pilaOperandos.pop();
        String operando1 = pilaOperandos.pop();
        String temporal = "idx" + idxCounter;
        triplos.add(temporal + " = (" + operador + ", " + operando1 + ", " + operando2 + ")");
        pilaOperandos.push(temporal);  // Guardar el resultado en la pila de operandos
        idxCounter++;  // Incrementar el contador de temporales
    }

    // Función para verificar la precedencia de operadores
    private boolean tieneMayorPrecedencia(String operadorEnPila, String operadorActual) {
        return getPrecedencia(operadorEnPila) >= getPrecedencia(operadorActual);
    }

    // Obtener precedencia de un operador
    private int getPrecedencia(String operador) {
        switch (operador) {
            case "^": return 3;  // Mayor precedencia: Potenciación
            case "*": case "/": return 2;  // Multiplicación / División
            case "+": case "-": return 1;  // Suma / Resta
            case "=": return 0;  // Menor precedencia: Asignación
            default: return -1;
        }
    }

    // Método para obtener la lista de triplos generados
    public ArrayList<String> getTriplos() {
        return triplos;
    }

    // Método para generar el resultado de triplos como un String
    public String getResultadoTriplos() {
        StringBuilder resultado = new StringBuilder();
        for (String triplo : triplos) {
            resultado.append(triplo).append("\n");
        }
        return resultado.toString();
    }

    // Método para imprimir los triplos en un JTextArea o similar
    public void imprimirTriplos(Ventana v) {
        StringBuilder resultado = new StringBuilder();
        for (String triplo : triplos) {
            resultado.append(triplo).append("\n");
        }
        // Imprimir los triplos en el componente de salida
        v.getTxtSalida().setText(resultado.toString());
    }

    
}
