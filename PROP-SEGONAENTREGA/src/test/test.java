package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.GestorDisc;
import exceptions.RestriccioIntegritatException;
import model.Assignatura;

import java.io.IOException;
import java.util.ArrayList;

public class test {
    public static void main(String[] args) throws IOException, RestriccioIntegritatException {
        ArrayList<Assignatura> assignatures = new ArrayList<>();
        Assignatura a = new Assignatura("TestAssig", "TA", "ksamncjkabnsjkc", 1);
        Assignatura a2 = new Assignatura("TestAssig2", "TA2", "ksamncjkabnsjkc", 1);
        a.afegeixCorrequisit(a2);
        a2.afegeixCorrequisit(a);
        assignatures.add(a);
        assignatures.add(a2);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(assignatures);
        GestorDisc.getInstance().escriu(json, "test.json");
        System.out.println("hi");
    }
}
