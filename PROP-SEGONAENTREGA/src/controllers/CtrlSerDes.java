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

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CtrlSerDes {
    private static CtrlSerDes ourInstance;
    private GestorDisc gd = GestorDisc.getInstance();

    private CtrlSerDes() {

    }

    public static CtrlSerDes getInstance() {
        if (ourInstance == null) {
            ourInstance = new CtrlSerDes();
        }
        return ourInstance;
    }

    /* LECTURA DE FITXERS */
    public HashMap<String, Aula> carregaAules() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, Aula> a = gson.fromJson(gd.llegeix("aules"), new TypeToken<HashMap<String, Aula>>() {
        }.getType());
        return a;
    }

    public HashMap<String, PlaEstudis> carregaPlansDEstudi() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, PlaEstudis> ps = gson.fromJson(gd.llegeix("plansestudi"), new TypeToken<HashMap<String, PlaEstudis>>() {
        }.getType());
        return ps;
    }

    public HashMap<String, Assignatura> carregaAssignatures() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        HashMap<String, Assignatura> ass = gson.fromJson(gd.llegeix("assignatures"), new TypeToken<HashMap<String, Assignatura>>() {
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

    public String horariToJson(Horari h){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray obj = new JsonArray();
        for (int i = 0; i < h.getHorari().length; ++i) {
            for (int j = 0; j < h.getHorari()[i].length; ++j) {
                for (int k = 0; k < h.getHorari()[i][j].length; ++k) {
                    JsonObject jsonElement = new JsonObject();
                    if (h.getHorari()[i][j][k] != null) {
                        jsonElement.addProperty("dia", h.getHorari()[i][j][k].getDiaSetmana());
                        jsonElement.addProperty("hora", h.getHorari()[i][j][k].getHora());
                        jsonElement.addProperty("assignatura", h.getHorari()[i][j][k].getAssignatura().getAbr());
                        jsonElement.addProperty("grup", h.getHorari()[i][j][k].getGrup().getNum());
                        jsonElement.addProperty("aula", h.getHorari()[i][j][k].getAula().getKey().toUpperCase());
                        obj.add(jsonElement);
                    }
                }
            }
        }
        return gson.toJson(obj);
    }

    public void guardaHorari(String h, String filepath) throws IOException {
        gd.escriu(h, filepath);
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

    public boolean comprovaDefaultFilepath() {
        gd.setDefaults();

        try {
            boolean b = checkAssigFile(gd.llegeix("assignatures"));
            if (!b) return false;
        } catch (IOException e) {
            return false;
        }

        try {
            boolean b = checkAulesFile(gd.llegeix("aules"));
            if (!b) return false;
        } catch (IOException e) {
            return false;
        }

        try {
            boolean b = checkPlansEstudiFile(gd.llegeix("plansestudi"));
            if (!b) return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    private boolean checkAssigFile(String file){
        Map assignatures;

        try {
            assignatures = new Gson().fromJson(file, Map.class);
            Set set = assignatures.keySet();
            Map m = (Map) assignatures.get(set.iterator().next());
            String test = (String) m.get("nom");
            return test != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkAulesFile(String file){
        Map aules;
        try {
            aules = new Gson().fromJson(file, Map.class);
            Set set = aules.keySet();
            Map m = (Map) aules.get(set.iterator().next());
            String test = (String) m.get("edifici");
            return test != null;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkPlansEstudiFile(String file){
        Map pe;
        try {
            pe = new Gson().fromJson(file, Map.class);
            Set set = pe.keySet();
            Map m = (Map) pe.get(set.iterator().next());
            String test = (String) m.get("nomTitulacio");
            return test != null;
        } catch (Exception e) {
            return false;
        }
    }

    public void setDataDirectory(File dir) {
        gd.setFilepath(dir.getAbsolutePath());
    }

    public int buscaData() {
        ArrayList<String> files = gd.getLlistaArxius();

        int assigFound = 0;
        int plansEstudiFound = 0;
        int aulesFound = 0;

        for (String f : files) {
            try {
                if(checkAssigFile(gd.llegeix(f))) {
                    gd.setFilenameAssig(f);
                    assigFound = 3;
                }
                else if (checkAulesFile(gd.llegeix(f))) {
                    gd.setFilenameAules(f);
                    aulesFound = 5;
                }
                else if (checkPlansEstudiFile(gd.llegeix(f))) {
                    gd.setFilenamePlaEst(f);
                    plansEstudiFound = 7;
                }
            } catch (IOException e) {

            }
        }
        return assigFound * aulesFound * plansEstudiFound;
    }
}
