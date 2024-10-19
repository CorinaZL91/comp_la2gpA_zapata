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
public class PruebaAHM {
     public static void main(String[] args){
        ArrayList <Alumno> alumnos = new ArrayList();
        alumnos.add(new Alumno("Corina", 5, "Nada"));
        alumnos.add(new Alumno("Gerardo", 4, "Algebra"));
        alumnos.add(new Alumno("Jose ", 6, "Python"));
       //Parecido a cuando encontramos expresion regular
       
       
        for (Alumno alumno : alumnos){
        int grupoCurso = Cursos.getIDCurso(alumno.getCurso());
         if (grupoCurso == -1){
            System.out.println(alumno + "Grupo no definido");
         }
         if (grupoCurso >= 1 && grupoCurso <=99){
            System.out.println(alumno + "Básicas");
        }
          if (grupoCurso >= 100 && grupoCurso <=199){
            System.out.println(alumno + "Programacion");
        }
           if (grupoCurso >= 200 && grupoCurso <=299){
            System.out.println(alumno + "Especialidad");
        }
         
       
       
        }
     
        
    }
}
