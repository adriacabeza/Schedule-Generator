/**
 * @author Aina Garcia
 */

package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
    private GestorDisc gd = GestorDisc.getInstance();

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
        HashMap<String, Aula> a = gson.fromJson(gd.llegeix(filepath), new TypeToken<HashMap<String, Aula>>() {
        }.getType());
        return a;
    }

    public HashMap<String, PlaEstudis> carregaPlansDEstudi(String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, PlaEstudis> ps = gson.fromJson(gd.llegeix(filepath), new TypeToken<HashMap<String, PlaEstudis>>() {
        }.getType());
        return ps;
    }

    public HashMap<String, Assignatura> carregaAssignatures(String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, Assignatura> ass = gson.fromJson(gd.llegeix(filepath), new TypeToken<HashMap<String, Assignatura>>() {
        }.getType());
        return ass;
    }

    /**
     * Carrega horari de la forma guardaHorari2
     * @param filepath
     * @return
     * @throws IOException
     */
    public String carregaHorari(String filepath) throws IOException {
        return gd.llegeix(filepath);
    }


    /* ESCRIPTURA DE FITXERS */

    public void guardaHorari(Horari h, String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray obj = new JsonArray();
        for (int i = 0; i < h.getHorari().length; ++i) {
            for (int j = 0; j < h.getHorari()[i].length; ++j) {
                for (int k = 0; k < h.getHorari()[i][j].length; ++k) {
                    JsonObject jsonElement = new JsonObject();
                    if (h.getHorari()[i][j][k] != null) {
                        jsonElement.addProperty("dia", h.getHorari()[i][j][k].getDiaSetmana());
                        jsonElement.addProperty("hora", h.getHorari()[i][j][k].getHora());
                        jsonElement.addProperty("assignatura", h.getHorari()[i][j][k].getAssignatura().getNom().toUpperCase());
                        jsonElement.addProperty("grup", h.getHorari()[i][j][k].getGrup().getNum());
                        jsonElement.addProperty("aula", h.getHorari()[i][j][k].getAula().getKey().toUpperCase());
                        obj.add(jsonElement);
                    }
                }
            }
        }
        String json = gson.toJson(obj);
        gd.escriu(json, filepath);
    }

    public void guardaAssignatures(HashMap<String, Assignatura> assignatures, String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(assignatures);
        gd.escriu(json, filepath);
    }

    public void guardaAules(HashMap<String, Aula> aules, String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(aules);
        gd.escriu(json, filepath);
    }

    public void guardaPlaDEstudis(HashMap<String, PlaEstudis> ps, String filepath) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(ps);
        gd.escriu(json, filepath);
    }

    /*
    public void guardaRestriccions(,String filepath) throws IOException{
       SHA DE PENSAR COM FER LO DE GUARDAR RESTRICCIONS EN TEXT
    }*/
}
