package model;

public class Restriccions {
    private static Restriccions ourInstance = new Restriccions();

    public static Restriccions getInstance() {
        return ourInstance;
    }

    private Restriccions() {
    }
}
