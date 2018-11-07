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
