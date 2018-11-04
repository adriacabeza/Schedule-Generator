package model;
import model.*;


public abstract class Restriccions {

    int id;
    boolean active;
//
//    private static Restriccions ourInstance = new Restriccions();
//    //TODO: preguntar a aina que coño e ezto i why dafuq cannot be abstract
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
}
