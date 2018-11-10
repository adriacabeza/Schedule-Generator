package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Domini {

    private HashMap<Integer,HashMap <Integer, Set<Aula>>> domini;

    public HashMap<Integer, HashMap<Integer, Set<Aula>>> getDomini() {
        return domini;
    }

    public void setDomini(HashMap<Integer, HashMap<Integer, Set<Aula>>> domini) {
        this.domini = domini;
    }

    public Domini(HashMap<Integer, HashMap<Integer, Set<Aula>>> domini) {
        this.domini = domini;
    }

    private Domini aplica_restriccions_unaries(Map<String, Restriccions> restriccio){

    return this;
    }
}
