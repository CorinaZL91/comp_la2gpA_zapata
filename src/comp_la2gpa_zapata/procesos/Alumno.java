/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp_la2gpa_zapata.procesos;

/**
 *
 * @author corin
 */
public class Alumno {
    private String nombre;
    private int semestre;
    private String curso;

    public Alumno(String nombre, int semestre, String curso) {
        this.nombre = nombre;
        this.semestre = semestre;
        this.curso = curso;
    }

    public String getNombre() {
        return nombre;
    }

    public int getSemestre() {
        return semestre;
    }

    public String getCurso() {
        return curso;
    }

    @Override
    public String toString() {
        return '[' + nombre +"\t" + semestre + "\t" + curso + ']';
    }

}
