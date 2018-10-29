package controllers;

public class CtrGeneradorHoraris {
    private static CtrGeneradorHoraris ourInstance = new CtrGeneradorHoraris();

    public static CtrGeneradorHoraris getInstance() {
        return ourInstance;
    }

    private CtrGeneradorHoraris() {
    }
}
