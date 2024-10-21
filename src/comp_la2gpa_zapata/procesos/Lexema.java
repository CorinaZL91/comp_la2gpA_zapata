/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp_la2gpa_zapata.procesos;

/**
 *
 * @author corin
 */
public class Lexema {

    private int token;
    private String cadena;
    private int valor;
    private String grupo;

    public Lexema(int token, String cadena, int valor, String grupo) {
        this.token = token;
        this.cadena = cadena;
        this.valor = valor;
        this.grupo = grupo;
    }

    public int getToken() {
        return token;
    }

    public String getCadena() {
        return cadena;
    }

    public int getValor() {
        return valor;
    }
    
     public String getGrupo() {
        return grupo;
    }

    @Override
    public String toString() {
        return "[" + token + "\t" + cadena + "\t" + valor + "\t" + grupo + "]";
    }

}
