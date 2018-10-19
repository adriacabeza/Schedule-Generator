package model;

public class InfoPeriodeLectiu {
    private static InfoPeriodeLectiu ourInstance = new InfoPeriodeLectiu();

    public static InfoPeriodeLectiu getInstance() {
        return ourInstance;
    }

    private InfoPeriodeLectiu() {
    }
}
