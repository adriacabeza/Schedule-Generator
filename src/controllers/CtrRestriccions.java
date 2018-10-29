package controllers;

public class CtrRestriccions {
    private static CtrRestriccions ourInstance = new CtrRestriccions();

    public static CtrRestriccions getInstance() {
        return ourInstance;
    }

    private CtrRestriccions() {
    }
}
