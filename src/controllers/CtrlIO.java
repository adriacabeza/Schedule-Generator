/**
 * @author Aina Garcia
 */

package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Assignatura;
import model.Aula;
import model.Horari;
import model.PlaEstudis;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CtrlIO {
    private static CtrlIO ourInstance;

    private CtrlIO() {

    }

    public static CtrlIO getInstance() {
        if (ourInstance == null) {
            ourInstance = new CtrlIO();
        }
        return ourInstance;
    }

    /* LECTURA DE FITXERS */

    public HashMap<String, Aula> carregaAules(String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader fr = new FileReader(filepath);
        HashMap<String, Aula> a = gson.fromJson(fr, new TypeToken<HashMap<String, Aula>>() {
        }.getType());
        fr.close();
        return a;
    }

    public HashMap<String, PlaEstudis> carregaPlansDEstudi(String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader fr = new FileReader(filepath);
        HashMap<String, PlaEstudis> ps = gson.fromJson(fr, new TypeToken<HashMap<String, PlaEstudis>>() {
        }.getType());
        fr.close();
        return ps;
    }

    public HashMap<String, Assignatura> carregaAssignatures(String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader fr = new FileReader(filepath);
        HashMap<String, Assignatura> ass = gson.fromJson(fr, new TypeToken<HashMap<String, Assignatura>>() {
        }.getType());
        fr.close();
        return ass;
    }

    public Horari carregaHorari(String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader fr = new FileReader(filepath);
        Horari horari = gson.fromJson(fr, new TypeToken<Horari>() {
        }.getType());
        fr.close();
        return horari;
    }


    /* ESCRIPTURA DE FITXERS */


    public void guardaHorari(Horari h, String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(h);
        FileWriter fw = new FileWriter(filepath);
        fw.write(json);
        fw.close();
    }

    public void guardaAssignatures(HashMap<String, Assignatura> assignatures, String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(assignatures);
        FileWriter fw = new FileWriter(filepath);
        fw.write(json);
        fw.close();
    }

    public void guardaAules(HashMap<String, Aula> aules, String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(aules);
        FileWriter fw = new FileWriter(filepath);
        fw.write(json);
        fw.close();
    }

    public void guardaPlaDEstudis(HashMap<String, PlaEstudis> ps, String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(ps);
        FileWriter fw = new FileWriter(filepath);
        fw.write(json);
        fw.close();
    }
}
