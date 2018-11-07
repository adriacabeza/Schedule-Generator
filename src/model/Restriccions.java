package model;
import model.*;


public abstract class Restriccions {

    private int id;
    private boolean active;

//    private static Restriccions ourInstance = new Restriccions();
//    public static Restriccions getInstance() {
//        return ourInstance;
//    }

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

    public boolean isable() { return false; }
}

//    private boolean comprovar_restricciones_lab(int aula1, Subgrup subgrup, int dia, int hora, Assignatura assig){
//
//        //AQUÍ SE COMPLICAN LAS RESTRICCIONES
//        /*for (int i = 0; i < aules.size(); ++i) {
//            if (horari[hora][dia][i].getAssignatura().getCorrequisits().contains(assig)) return false;
//        }
//            if (horari[hora][dia][i].getAssignatura().getQuadrimestre() == assig.getQuadrimestre()) return false;
//        }
//
//        for(int i = 0; i< aules.size(); ++i) {
//            if (horari[hora][dia][i].getAssignatura() == assig && horari[hora][dia][i].getAula().getTipusAula() == "teoria")
//        }*/
//
//
//        //if (aula.getTipusAula() == "teoria") return false;
//
//        return true;
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
//
//        /*
//        if (aula.getTipusAula() == "laboratori") return false; TONI DICE QUE NO
//        */
//
//
//        return true;
//    }