package controllers;

public class CtrQuadrimestre {
    private static CtrQuadrimestre ourInstance = new CtrQuadrimestre();

    public static CtrQuadrimestre getInstance() {
        return ourInstance;
    }

    private CtrQuadrimestre() {
    }
}
