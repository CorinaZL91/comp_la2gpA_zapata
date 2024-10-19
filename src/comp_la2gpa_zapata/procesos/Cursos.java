/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp_la2gpa_zapata.procesos;

import java.util.HashMap;

/**
 *
 * @author corin
 */
public class Cursos {
    
    private static HashMap mapaCursos;
    
    //Inicialixamos componente
    private static void llenaCursos(){
        
        mapaCursos = new HashMap<String, Integer>();
                
        mapaCursos.put("Alegebra", 1);
        mapaCursos.put("Calculo", 2); //1 a 99 basicos
        mapaCursos.put("Java", 101);
        mapaCursos.put("Python", 102); //100 a 199 programacion 
        mapaCursos.put("Seguridad", 201);
        mapaCursos.put("Web", 202); //200 a 299 especialidad 
    }
    
    public static int getIDCurso(String s){
        llenaCursos();
        int r = -1;
        if (mapaCursos.containsKey(s) && mapaCursos.get(s) instanceof Integer){
            return (Integer)mapaCursos.get(s);
        }
        return r; //si no entra al if devuelve -1;
    }
}
