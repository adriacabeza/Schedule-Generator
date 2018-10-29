package controllers;

public class CtrAssignatura {
    private static CtrAssignatura ourInstance = new CtrAssignatura();

    public static CtrAssignatura getInstance() {
        return ourInstance;
    }

    private CtrAssignatura() {
    }
}
