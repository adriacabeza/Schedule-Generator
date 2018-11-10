package model;
import exceptions.NotFoundException;
import model.*;

import java.util.ArrayList;


public abstract class Restriccions {

    private int id;
//    private static Restriccions ourInstance = new Restriccions();
//    public static Restriccions getInstance() {
//        return ourInstance;
//    }
//que restriccions torni un arraylist dels dies i les horesque podria la susodicha asignatura

    public Restriccions(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract boolean isable() throws NotFoundException; //li posarem els parametres necessaris en cada subclasse


}

//    private boolean comprovar_restricciones_lab(int aula1, Subgrup subgrup, int dia, int hora, Assignatura assig){//
//        for(int i = 0; i< aules.size(); ++i) {
//            if (horari[hora][dia][i].getAssignatura() == assig)
//        }*/
// return true;
//    }


//
//    private boolean comprovar_restricciones_teoria(int aula1, Grup grup, int dia, int hora, Assignatura assig) throws NotFoundException {
//        for (int i = 0; i < aules.size(); ++i) {
//            if (horari[hora][dia][i].getAssignatura().getCorrequisits().contains(assig)) return false;
//        } //restricció que mira si ja està posada una assignatura correquisit en aquesta hora
//
//
//        for (int i = 0; i < aules.size(); ++i) {
//            if (horari[hora][dia][i].getAssignatura().getQuadrimestre() == assig.getQuadrimestre()) return false;
//        } //restricció que mira si ja està posat una assignatura del mateix nivell
//
