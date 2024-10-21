/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package comp_la2gpa_zapata.procesos;

/**
 *
 * @author corin
 */
public class Triplo {

    private int index;
    private String operador;
    private String operando1;
    private String operando2;

    public Triplo(int index, String operador, String operando1, String operando2) {
        this.index = index;
        this.operador = operador;
        this.operando1 = operando1;
        this.operando2 = operando2;
    }

    public int getIndex() {
        return index;
    }

    public String getOperador() {
        return operador;
    }

    public String getOperando1() {
        return operando1;
    }

    public String getOperando2() {
        return operando2;
    }

    @Override
    public String toString() {
        return "idx" + index + " (" + operador + ", " + operando1 + ", " + operando2 + ")";
    }

}
