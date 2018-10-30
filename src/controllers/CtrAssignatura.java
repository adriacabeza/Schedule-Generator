package controllers;
import model.*;
import java.util.HashMap;

public class CtrAssignatura {
    private static CtrAssignatura ourInstance = new CtrAssignatura();

    public static CtrAssignatura getInstance() {
        return ourInstance;
    }

    private HashMap<String, Assignatura> assignatures;

    private CtrAssignatura() {
        //carrega totes les assignatures de la DB o d'on sigui, si n'hi ha
    }

    /**
     * Permet donar d'alta una nova instancia d'assignatura
     * @param nom Nom de l'assignatura
     * @param grup_num Numero de grups que tindrà l'assignatura
     * @param grup_cap Capacitat dels grups de l'assignatura
     * @param sgrup_num Numero de subgrups de laboratori que tindra l'assignatura
     */
    public void creaAssignatura(String nom, int grup_num, int grup_cap, int sgrup_num, int quadrimestre, Teoria t, Laboratori l){
        assignatures.put(nom, new Assignatura(nom, grup_num, grup_cap, sgrup_num, quadrimestre, t, l));
    }

    /**
     * Permet esborrar una assignatura
     * @param nom_assignatura nom de l'assignatura a esborrar
     */
    public void esborraAssignatura(String nom_assignatura){
        assignatures.remove(nom_assignatura);
    }

    /**
     * Permet modificar la informació sobre les sessions de teoria d'una assignatura
     * @param nom_assig Nom de l'assignatura
     * @param duracio Duració de les sessions de teoria
     * @param num_sessions Numero de sessions setmanals de l'assignatura
     */
    public void modificaInformacioTeoria(String nom_assig, int duracio, int num_sessions){
        Teoria t = new Teoria(num_sessions, duracio);
        Assignatura a = getAssignatura(nom_assig);
        a.setTeoria(t);
        assignatures.put(nom_assig, a);
    }

    /**
     * Permet modificar la informació sobre les sessions de laboratori d'una assignatura
     * @param nom_assig Nom de l'assignatura
     * @param duracio Duració de les sessions de laboratori
     * @param num_sessions Numero de sessions setmanals de l'assignatura
     */
    public void modificaInformacioLaboratori(String nom_assig, int duracio, int num_sessions){
        Laboratori l = new Laboratori(num_sessions, duracio);
        Assignatura a = getAssignatura(nom_assig);
        a.setLaboratori(l);
        assignatures.put(nom_assig, a);
    }

    /**
     * Permet modificar els grups i subgrups d'una assignatura
     * @param nom_assig nom de l'assignatura
     * @param num_grups numero de grups  de l'assignatura
     * @param grup_cap capacitat de cada grup
     * @param sgrup_num capacitat dels subgrups
     */
    public void modificarGrups(String nom_assig, int num_grups, int grup_cap, int sgrup_num) {
        Assignatura a = assignatures.get(nom_assig);
        a.modificarGrups(num_grups, grup_cap, sgrup_num);
        assignatures.put(nom_assig, a);
    }

    /**
     * Permet afegir una relacio de correquisits entre dues assignatures
     * @param nom_a nom de d'una assignatura
     * @param nom_b nom de l'altre assignatura
     */
    public void afegeixCorrequisit(String nom_a, String nom_b){
        Assignatura a = assignatures.get(nom_a);
        a.afegeixCorrequisit(assignatures.get(nom_b));

        Assignatura b = assignatures.get(nom_b);
        b.afegeixCorrequisit(assignatures.get(nom_a));

        assignatures.put(nom_a, a);
        assignatures.put(nom_b, b);
    }

    /**
     * Esborra la relació de correqusiits entre dues assignatures
     * @param nom_a nom de d'una assignatura
     * @param nom_b nom de l'altre assignatura
     */
    public void esborraCorrequisit(String nom_a, String nom_b){
        Assignatura a = assignatures.get(nom_a);
        a.esborraCorrequisit(assignatures.get(nom_b));

        Assignatura b = assignatures.get(nom_b);
        b.esborraCorrequisit(assignatures.get(nom_a));

        assignatures.put(nom_a, a);
        assignatures.put(nom_b, b);
    }

    /**
     * Obte una copia de totes les assignatures
     * @param nom nom de l'assignatura que busquem
     * @return assignatura amb nom especificat si existeix
     */
    public Assignatura getAssignatura(String nom) {
        return assignatures.get(nom);
    }

    /**
     * Obte totes les assignatures
     * @return totes les assignatures
     */
    public HashMap<String, Assignatura> getAssignatures(){
        return assignatures;
    }
}
