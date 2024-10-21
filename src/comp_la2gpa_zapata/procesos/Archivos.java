package comp_la2gpa_zapata.procesos;

import comp_la2gpa_zapata.ventanas.Ventana;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;

public class Archivos {

    public static String getContenido(String rutaNombre) {
        StringBuilder contenido = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(rutaNombre));
            String linea;
            while ((linea = reader.readLine()) != null) {
                contenido.append(linea).append("\n");
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al abrir el archivo: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("Ocurrió un error al cerrar el archivo: " + e.getMessage());
            }
            return contenido.toString();
        }
    }

    public static File getArchivo(Ventana v) {
        JFileChooser fileChooser = new JFileChooser();
        int obtenido = fileChooser.showOpenDialog(v);

        if (obtenido == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    /**
     * Este metodo separa los elemetos de una caja de texto,
     *
     * @param v - Ventana para manipular los elementos de la ventana.
     * @return <ArrayList> que contiene los elementos
     */
    public static ArrayList<String> separaLexemas(String datos) {
        String patron = "[a-zA-Z]\\w*|\\d+|<=|>=|==|!=|<|>|&&|\\|\\||[\\^\\*\\/\\+\\-=]|[(),;.]";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(datos);

        ArrayList<String> lista = new ArrayList<>();

        while (matcher.find()) {
            lista.add(matcher.group());
        }
        return lista;
    }

    private static HashMap mapaLexemas;

    private static void llenaLexemas() {

        mapaLexemas = new HashMap<String, Integer>();
        mapaLexemas.put("<", 1);
        mapaLexemas.put("<=", 2);
        mapaLexemas.put(">", 3);
        mapaLexemas.put(">=", 4);
        mapaLexemas.put("==", 5);
        mapaLexemas.put("!=", 6);
        mapaLexemas.put("||", 7);
        mapaLexemas.put("&&", 8);
        mapaLexemas.put("+", 9);
        mapaLexemas.put("-", 10);
        mapaLexemas.put("*", 11);
        mapaLexemas.put("/", 12);
        mapaLexemas.put("^", 13);
        mapaLexemas.put("=", 14);
        mapaLexemas.put("(", 15);
        mapaLexemas.put(")", 16);
        mapaLexemas.put(",", 17);
        mapaLexemas.put(";", 18);
        mapaLexemas.put(".", 19);
        mapaLexemas.put("Identificador", 100);
        mapaLexemas.put("Número", 200);
    }

    public static int getIDLex(String s) {
        llenaLexemas();
        if (s.matches("[a-zA-Z]\\w*")) {
            return 100;
        } else if (s.matches("\\d+")) {
            return 200;
        } else if (mapaLexemas.containsKey(s) && mapaLexemas.get(s) instanceof Integer) {
            return (Integer) mapaLexemas.get(s);
        }
        return -1;
    }

    public static void asociaList(Ventana v, ArrayList<String> lista) {
        ArrayList<Lexema> lexemas = new ArrayList<>();

        llenaLexemas();

        for (String elemento : lista) {
            int token = getIDLex(elemento);
            String grupo = "";

            if (token == 100) {
                grupo = "Identificador";
            } else if (token == 200) {
                grupo = "Número";
            } else if (token >= 1 && token <= 8) {
                grupo = "Operador Lógico";
            } else if (token >= 9 && token <= 14) {
                grupo = "Operador Aritmético";
            } else if (token == 15 || token == 16) {
                grupo = "Agrupador";
            } else if (token >= 17 && token <= 19) {
                grupo = " Separador";
            } else {
                continue;
            }

            Lexema lexema = new Lexema(token, elemento, token, grupo);
            lexemas.add(lexema);
        }

        StringBuilder resultado = new StringBuilder();
        for (Lexema lexema : lexemas) {
            resultado.append(lexema.toString()).append("\n");
        }

        v.getTxtSalida().setText(resultado.toString());
    }

    public static int precedencia(String operador) {
        switch (operador) {
            case "^":
                return 3;
            case "*":
            case "/":
                return 2;
            case "+":
            case "-":
                return 1;
            case "=":
                return 0;
            default:
                return -1;
        }
    }

    public static ArrayList<String> postfijo(ArrayList<String> listaLexemas) {
        ArrayList<String> salida = new ArrayList<>();
        Stack<String> pila = new Stack<>();

        for (String lexema : listaLexemas) {
            int token = getIDLex(lexema);

            if (token == 100 || token == 200) {
                salida.add(lexema);
            } else if (token >= 9 && token <= 14) {
                while (!pila.isEmpty() && precedencia(pila.peek()) >= precedencia(lexema)) {
                    salida.add(pila.pop());
                }
                pila.push(lexema);
            } else if (lexema.equals("(")) {
                pila.push(lexema);
            } else if (lexema.equals(")")) {
                while (!pila.isEmpty() && !pila.peek().equals("(")) {
                    salida.add(pila.pop());
                }
                pila.pop();
            }
        }
        while (!pila.isEmpty()) {
            salida.add(pila.pop());
        }

        return salida;
    }

    public static void triplos(Ventana v, ArrayList<String> listaLexemas) {

        ArrayList<String> expresionPostfija = postfijo(listaLexemas);
        Stack<String> pila = new Stack<>();
        ArrayList<Triplo> listaTriplos = new ArrayList<>();
        int index = 1;

        for (String lexema : expresionPostfija) {
            int token = getIDLex(lexema);
            if (token == 100 || token == 200) {
                pila.push(lexema);
            } else if (token >= 9 && token <= 14) {
                String operando2 = pila.pop();
                String operando1 = pila.pop();

                String temp = "idx" + index;

                Triplo triplo = new Triplo(index, lexema, operando1, operando2);
                listaTriplos.add(triplo);

                pila.push(temp);
                index++;
            }
        }
        StringBuilder resultado = new StringBuilder();
        for (Triplo triplo : listaTriplos) {
            resultado.append(triplo.toString()).append("\n");
        }
        v.getTxtSalida().setText(resultado.toString());
    }

}

/**



public static void triplos(Ventana v, ArrayList<String> listaLexemas) {
    // Convertir la expresión infija a postfijo
    ArrayList<String> expresionPostfija = postfijo(listaLexemas);

    // Pila para evaluar la expresión postfija
    Stack<String> pila = new Stack<>();

    // Lista para almacenar los triplos
    ArrayList<Triplo> listaTriplos = new ArrayList<>();
    int index = 1; // Índice de los triplos

    for (String lexema : expresionPostfija) {
        int token = getIDLex(lexema); // Obtener el token del lexema

        // Si es un identificador o número, se empuja a la pila
        if (token == 100 || token == 200) {
            pila.push(lexema);
        }
        // Si es un operador, sacamos dos operandos de la pila y generamos un triplo
        else if (token >= 9 && token <= 14) {
            String operando2 = pila.pop();  // Sacamos el segundo operando
            String operando1 = pila.pop();  // Sacamos el primer operando

            // Creamos un nombre temporal para el resultado del triplo
            String temp = "t" + index;
            
            // Creamos un nuevo triplo
            Triplo triplo = new Triplo(index, lexema, operando1, operando2);
            listaTriplos.add(triplo);  // Añadimos el triplo a la lista

            // El resultado de este triplo se vuelve a empujar a la pila
            pila.push(temp);
            index++;  // Incrementamos el índice para el siguiente triplo
        }
    }

    // Mostrar los triplos en el txtSalida de la ventana
    StringBuilder resultado = new StringBuilder();
    for (Triplo triplo : listaTriplos) {
        resultado.append(triplo.toString()).append("\n");
  
 * public static void asociaList(Ventana v, ArrayList<String> lista){ // Crear
 * un StringBuilder para almacenar el resultado StringBuilder resultado = new
 * StringBuilder();
 *
 * // Mapa para almacenar operadores lógicos, aritméticos, separadores,
 * identificadores y números Map<String, Integer> simbolos = new HashMap<>();
 * simbolos.put("<", 1);  * simbolos.put("<=", 2);
    simbolos.put(">", 3); simbolos.put(">=", 4); simbolos.put("==", 5);
 * simbolos.put("!=", 6); simbolos.put("||", 7); simbolos.put("&&", 8);
 * simbolos.put("+", 9); simbolos.put("-", 10); simbolos.put("*", 11);
 * simbolos.put("/", 12); simbolos.put("(", 13); simbolos.put(")", 14);
 * simbolos.put(",", 15); simbolos.put(";", 16); simbolos.put(".", 17);
 * simbolos.put("Identificador", 100); simbolos.put("Número", 200);  *
 *
 * for (String elemento : lista) { if (elemento.matches("[a-zA-Z]\\w*")) {
 *
 * resultado.append(elemento).append(" -> Identificador
 * ").append(simbolos.get("Identificador")).append("\n"); } else if
 * (elemento.matches("[1-9]\\d+")) { // Números
 * resultado.append(elemento).append(" -> Número
 * ").append(simbolos.get("Número")).append("\n"); } else if
 * (simbolos.containsKey(elemento)) { // Operadores lógicos if
 * (elemento.matches("<|<=|>|>=|==|!=")) { resultado.append(elemento).append("
 * -> Operador Lógico ").append(simbolos.get(elemento)).append("\n"); } //
 * Operadores aritméticos else if (elemento.matches("\\+|\\-|\\*|/")) {
 * resultado.append(elemento).append(" -> Operador Aritmético
 * ").append(simbolos.get(elemento)).append("\n"); } // Separadores else if
 * (elemento.matches(",|;|\\.")) { resultado.append(elemento).append(" ->
 * Separador ").append(simbolos.get(elemento)).append("\n"); } // Agrupadores
 * else if (elemento.matches("\\(|\\)")) { resultado.append(elemento).append("
 * -> Agrupador ").append(simbolos.get(elemento)).append("\n"); } } }
 * v.getTxtSalida().setText(resultado.toString()); }
 *
 *
 * }
 *
 * public static void identificaLexemas(Ventana v, ArrayList<String> lista) {
 * StringBuilder resultado = new StringBuilder();
 *
 * for (String elemento : lista) { if
 * (elemento.matches("[a-zA-Z][a-zA-Z0-9_]*")) {
 * resultado.append(elemento).append(" --> Identificador\n"); } else if
 * (elemento.matches("\\d+")) { resultado.append(elemento).append(" -->
 * Número\n"); } else if (elemento.matches("|<=|>=|==|!=|<|>|&&|\\|\\|")) {
 * resultado.append(elemento).append(" --> Operador logico\n"); } else if
 * (elemento.matches("[+\\-*")) { //aqui falta
 * resultado.append(elemento).append(" --> Operador aritmetico\n"); } else if
 * (elemento.matches("[(),;.]")) { resultado.append(elemento).append(" -->
 * Separador/Agrupador\n"); } } v.getTxtSalida().setText(resultado.toString());
 * }
 *
 *
 * //Hacerlo con arraylist, idexOf
 *
 * //Tambien se puede reealizar con mapas, es decir, separacion por bloques:
 * Map<int, str>{<1, ".">,<2, ",">, <3, ")">} //Podemos pedir que se imprima con
 * su llave, utilizar mapas es lo ideal en este caso de lógica de programación,
 * //Los números no necesariamente son ejecutivos, puede ser 3 y 100 a la vez
 *
 * /**public static void calcularConejos(Ventana v) { String generacion =
 * v.getTxtContenido().getText(); int gen = Integer.parseInt(generacion); int
 * conejos = 0; if (gen >=5){ int res = 0; int conejosdie = 0; res = gen - 4;
 * int mult2 = (int) Math.pow(3, res); conejosdie = 2 * mult2; int mult = (int)
 * Math.pow(3, gen); conejos = (2 * mult) - conejosdie;
 *
 * }else {
 *
 * int mult = (int) Math.pow(3, gen); conejos = 2 * mult;}
 *
 *
 * v.getTxtSalida().setText(String.valueOf(conejos));
 *
 * }
 *
 *
 * /**
 * public static void procesarContenido(Ventana v) { String texto =
 * v.getTxtContenido().getText(); String regex = "[a-zA-Z]+\\w*|\\d+|[.,:]";
 * //Ordenar por tamaño, primero mas grande, despues unitario, primero numeros,
 * ya que puden ser muchos, despues punto, ya que es unitario
 *
 * //identificadores, digitos y puto StringBuilder resultado = new
 * StringBuilder();
 *
 *
 * Pattern pattern = Pattern.compile(regex); Matcher matcher =
 * pattern.matcher(texto);
 *
 *
 * while (matcher.find()) { resultado.append(matcher.group()).append("\n"); }
 *
 * v.getTxtSalida().setText(resultado.toString()); }
 *
 * public static void procesarContenido(Ventana v) { String datos =
 * v.getTxtContenido().getText(); int caracteres = datos.length();
 *
 * String[] palabras = datos.split("\\s"); int palabrasCant = palabras.length;
 *
 * String[] lineas = datos.split("\n"); int lineasCant = lineas.length;
 *
 * String resultado = "Caracteres: " + caracteres + "\n" + "Palabras: " +
 * palabrasCant + "\n" + "Líneas: " + lineasCant;
 * v.getTxtSalida().setText(resultado);
 *
 *
 *
 * String datos = v.getTxtContenido().getText();
 *
 * String[] num = datos.split(" ");
 *
 * int resultado = 0;
 *
 * for (String n : num) { try { int numero = Integer.parseInt(n); resultado +=
 * numero; } catch (NumberFormatException e) { System.out.println("Error: " +
 * n); } } v.getTxtSalida().setText(String.valueOf(resultado));
 *
 * public static separarElementos (Ventana v){ String contenido =
 * getContenido().getText(); String regex =
 * "[a-zA-Z]\\w*|[1-9]\\d+|<=|>=|==|!=|<|>|&&|\\|\\||[+\\-*.....; Pattern
 * pattern = Pattern.compile(patron); Matcher matcher = pattern.matcher(datos);
 *
 * public static mostrarSalida(Ventana v){ ventana.getTxtSalida().setText();
 * ArrayList <String> elementos = separarElementos(v); for (String elemento :
 * elementos){ v.getTxtSalida().append(elemento+"\n"); } }
 *
 *
 *
 */
