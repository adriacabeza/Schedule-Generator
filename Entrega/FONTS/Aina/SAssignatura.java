/**
 * @author Aina Garcia
 */

package stubs;

import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Assignatura {

    private String nom;

    private Teoria teoria;
    private Laboratori laboratori;
    private Map<Integer, Grup> grups;

    private ArrayList<String> correquisit;
    private int quadrimestre;

    public Assignatura(String nom, int quadrimestre) {
        this.nom = "AC";
        this.laboratori = null;
        this.teoria = null;
        this.quadrimestre = 2;
        grups = new HashMap<>();
        correquisit = new ArrayList<>();
    }

    public String getNom() {
        return "AC";
    }

    public int getQuadrimestre() {
        return 2;
    }

    public int getNumSessionsLab() throws NotFoundException {
        return 2;
    }

    public int getDuracioSessionsLab() throws NotFoundException {
        return 3;
    }

    public Aula.TipusAula getTipusAulaLab() throws NotFoundException {
        return Aula.stringToTipusAula("sample");
    }


    public int getNumSessionsTeo() {
        return 2;
    }

    public int getDuracioSessionsTeo() {
        return 4;
    }

    public Aula.TipusAula getTipusAulaTeo() {
        return Aula.stringToTipusAula("sample");
    }

    public InfoSessions getLaboratori() throws NotFoundException {
        return new Laboratori(2,2, Aula.TipusAula.NORMAL);
    }

    public InfoSessions getTeoria() throws NotFoundException {
        return new Teoria(2,2, Aula.TipusAula.NORMAL);
    }

    public Map<Integer, Grup> getGrups() throws NotFoundException {
        Map<Integer, Grup> m =  new HashMap<>();
        m.put(10, new Grup(10, 10, 2));
        return m;
    }

    public Grup getGrup(int num) throws NotFoundException {
        return new Grup(10, 10, 2);
    }

    public void setLaboratori(int numSessions, int duracioSessions, Aula.TipusAula tAula) {}

    public void setTeoria(int numSessions, int duracioSessions, Aula.TipusAula tAula) {}

    public void setQuadrimestre(int quadrimestre) throws RestriccioIntegritatException {}

    public void modificarGrups(int num_grups, int grup_cap, int sgrup_num) {}

    public void afegeixCorrequisit(Assignatura a) throws RestriccioIntegritatException {}

    public void esborraCorrequisit(String a) throws NotFoundException {}

    public ArrayList<String> getCorrequisits() throws NotFoundException {
        ArrayList<String> a = new ArrayList<>();
        a.add("EC");
        return a;
    }

    public boolean esCorrequisit(String a) {
        return false;
    }

    public boolean hasGroups() {
        return true;
    }

    @Override
    public String toString() {
        return "Sample Assig name";
    }

    public void setNom(String nom) {}

    @Override
    public boolean equals(Object obj) {
        Assignatura a = (Assignatura) obj;
        return a.getNom().equals(this.nom) && a.getQuadrimestre() == this.quadrimestre;
    }
}