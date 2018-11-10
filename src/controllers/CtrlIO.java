 package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Assignatura;
import model.Aula;
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
        if(ourInstance == null){
            ourInstance = new CtrlIO();
        }
        return ourInstance;
    }

    /* LECTURA DE FITXERS */

    public void carregaAules(String filepath){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileReader fr = new FileReader(filepath);
            HashMap<String, Aula> a = gson.fromJson(fr, new TypeToken<HashMap<String, Aula>>(){}.getType());
            fr.close();
      //      return a;

        } catch (IOException e) {
            e.printStackTrace();
        }
      //  return null;
    }

    public void carregaPlansDEstudi(String filepath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileReader fr = new FileReader(filepath);

            HashMap<String, PlaEstudis> ps = gson.fromJson(fr, new TypeToken<HashMap<String, PlaEstudis>>(){}.getType());
            fr.close();
           // return ps;

        } catch (IOException e) {
            e.printStackTrace();
        }
      //  return null;

    }

    public HashMap<String,Assignatura> carregaAssignatures(String filepath){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            FileReader fr = new FileReader(filepath);

            HashMap<String, Assignatura> ass = gson.fromJson(fr, new TypeToken<HashMap<String, Assignatura>>(){}.getType());
            fr.close();
            return ass;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void carregaHorari(String filepath) {

    }


    /* ESCRIPTURA DE FITXERS */


    public void guardaHorari(){

    }

    public void guardaAssignatures(HashMap<String, Assignatura> assignatures){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            String json = gson.toJson(assignatures);
            FileWriter fw = new FileWriter("assignatures.json");
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardaAules(HashMap<String, Aula> aules){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            String json = gson.toJson(aules);
            FileWriter fw = new FileWriter("aules.json");
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardaPlaDEstudis(HashMap<String, PlaEstudis> ps){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            String json = gson.toJson(ps);
            FileWriter fw = new FileWriter("aules.json");
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
