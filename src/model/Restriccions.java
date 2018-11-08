package model;
import model.*;


public abstract class Restriccions {

    private int id;
    private boolean active;

//    private static Restriccions ourInstance = new Restriccions();
//    public static Restriccions getInstance() {
//        return ourInstance;
//    }


    public Restriccions() {
        this.id = 0;
        this.active = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public abstract boolean isable();


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
