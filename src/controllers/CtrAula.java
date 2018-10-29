package controllers;

public class CtrAula {
    private static CtrAula ourInstance = new CtrAula();

    public static CtrAula getInstance() {
        return ourInstance;
    }

    private CtrAula() {
    }
}
