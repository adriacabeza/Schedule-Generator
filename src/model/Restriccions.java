package model;
import model.*;


public abstract class Restriccions {

    int id;
//
//    private static Restriccions ourInstance = new Restriccions();
//    //TODO: preguntar a aina que coño e ezto i why dafuq cannot be abstract
//    public static Restriccions getInstance() {
//        return ourInstance;
//    }

    public Restriccions(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract boolean compleix();
}
