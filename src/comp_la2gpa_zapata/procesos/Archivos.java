package comp_la2gpa_zapata.procesos;

import comp_la2gpa_zapata.ventanas.Ventana;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

    public static void sumar(Ventana v) {

        String datos = v.getTxtContenido().getText();

        String[] num = datos.split(" ");

        int resultado = 0;

        for (String n : num) {
            try {
                int numero = Integer.parseInt(n);
                resultado += numero;
            } catch (NumberFormatException e) {
                System.out.println("Error: " + n);
            }
        }
        v.getTxtSalida().setText(String.valueOf(resultado));
    }

}
