package comp_la2gpa_zapata.procesos;

import comp_la2gpa_zapata.ventanas.Ventana;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
 * @param v - Ventana para manipular los elementos de la ventana.
 * @return <ArrayList>  que contiene los elementos
 */
    public static ArrayList<String> separaLexemas(String datos) {
        String patron = "[a-zA-Z]\\w*|[1-9]\\d+|<=|>=|==|!=|<|>|&&|\\|\\||[+\\-*/]|[(),;.]";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(datos);

        ArrayList<String> lista = new ArrayList<>();

        while (matcher.find()) {
            lista.add(matcher.group());
        }
        return lista;
    }

   public static void asociaList(Ventana v, ArrayList<String> lista){
    // Crear un StringBuilder para almacenar el resultado
    StringBuilder resultado = new StringBuilder();

    // Mapa para almacenar operadores lógicos, aritméticos, separadores, identificadores y números
    Map<String, Integer> simbolos = new HashMap<>();
    simbolos.put("<", 1);
    simbolos.put("<=", 2);
    simbolos.put(">", 3);
    simbolos.put(">=", 4);
    simbolos.put("==", 5);
    simbolos.put("!=", 6);
    simbolos.put("||", 7);
    simbolos.put("&&", 8);
    simbolos.put("+", 9);
    simbolos.put("-", 10);
    simbolos.put("*", 11);
    simbolos.put("/", 12);
    simbolos.put("(", 13);
    simbolos.put(")", 14);
    simbolos.put(",", 15);
    simbolos.put(";", 16);
    simbolos.put(".", 17);
    simbolos.put("Identificador", 100); 
    simbolos.put("Número", 200);        

    
    for (String elemento : lista) {
        if (elemento.matches("[a-zA-Z]\\w*")) {
            
            resultado.append(elemento).append(" -> Identificador   ").append(simbolos.get("Identificador")).append("\n");
        } else if (elemento.matches("[1-9]\\d+")) {
            // Números
            resultado.append(elemento).append(" -> Número   ").append(simbolos.get("Número")).append("\n");
        } else if (simbolos.containsKey(elemento)) {
            // Operadores lógicos
            if (elemento.matches("<|<=|>|>=|==|!=")) {
                resultado.append(elemento).append(" -> Operador Lógico   ").append(simbolos.get(elemento)).append("\n");
            }
            // Operadores aritméticos
            else if (elemento.matches("\\+|\\-|\\*|/")) {
                resultado.append(elemento).append(" -> Operador Aritmético   ").append(simbolos.get(elemento)).append("\n");
            }
            // Separadores
            else if (elemento.matches(",|;|\\.")) {
                resultado.append(elemento).append(" -> Separador   ").append(simbolos.get(elemento)).append("\n");
            }
            // Agrupadores
            else if (elemento.matches("\\(|\\)")) {
                resultado.append(elemento).append(" -> Agrupador   ").append(simbolos.get(elemento)).append("\n");
            }
        }
    }
    v.getTxtSalida().setText(resultado.toString());
}
}
/**
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
 * public static separarElementos (Ventana v){
 *  String contenido = getContenido().getText();
 *  String regex = "[a-zA-Z]\\w*|[1-9]\\d+|<=|>=|==|!=|<|>|&&|\\|\\||[+\\-*.....;
 *  Pattern pattern = Pattern.compile(patron);
    Matcher matcher = pattern.matcher(datos);

 *  public static mostrarSalida(Ventana v){
 *  ventana.getTxtSalida().setText();
 *      ArrayList <String> elementos = separarElementos(v);
 * for (String elemento : elementos){
 *  v.getTxtSalida().append(elemento+"\n");
 * }
 * }
 *  
 * 
 * 
 */
