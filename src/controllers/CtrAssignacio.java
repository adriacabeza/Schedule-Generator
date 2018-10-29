package controllers;

public class CtrAssignacio {
    private static CtrAssignacio ourInstance = new CtrAssignacio();

    public static CtrAssignacio getInstance() {
        return ourInstance;
    }

    private CtrAssignacio() {
    }
}
