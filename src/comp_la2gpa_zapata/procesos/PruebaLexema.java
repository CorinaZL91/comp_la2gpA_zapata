/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp_la2gpa_zapata.procesos;

import java.util.ArrayList;

/**
 *
 * @author corin
 */
public class PruebaLexema {
    public static void main(String[] args) {
        ArrayList<Lexema> allex = new ArrayList();
        allex.add(new Lexema(1,"hola",2,"identificadores"));
        allex.add(new Lexema(2,";",3,"Separadores"));
        for (Lexema lexema : allex) {
            System.out.println(lexema);
        }
    }
}
