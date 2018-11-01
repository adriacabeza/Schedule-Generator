package model;
import model.*;


public abstract class Restriccions {

    int id;

    private static Restriccions ourInstance = new Restriccions();

    public static Restriccions getInstance() {
        return ourInstance;
    }

    //TODO: tenemos que tener las restricciones unàries, binarias i n-arias
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
