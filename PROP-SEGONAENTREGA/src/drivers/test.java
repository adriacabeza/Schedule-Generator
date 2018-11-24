package drivers;

import model.Aula;
import model.InfoSessions;
import model.Teoria;

public class test {

    public static void main(String[] args){


        InfoSessions t = new Teoria(2, 2, null);
        Aula a = new Aula("a0", 2,3, null, 60);
        System.out.println(a.getTipusAula()==t.gettAula());
        System.out.println(a.getTipusAula().equals(null));

    }
}
