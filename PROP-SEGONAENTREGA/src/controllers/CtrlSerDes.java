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

    /**
     * Transforma un arxiu a un mapa d'objectes
     *
     * @return mapa d'objectes tipus aula
     * @throws IOException si hi ha hagut algun problema de lectura
     */
    HashMap<String, Aula> carregaAules() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(gd.llegeix("aules"), new TypeToken<HashMap<String, Aula>>() {
        }.getType());
    }

    /**
     * Transforma un arxiu a un mapa d'objectes
     *
     * @return mapa d'objectes tipus plaEstudis
     * @throws IOException si hi ha hagut algun problema de lectura
     */
    HashMap<String, PlaEstudis> carregaPlansDEstudi() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(gd.llegeix("plansestudi"), new TypeToken<HashMap<String, PlaEstudis>>() {
        }.getType());
    }

    /**
     * Transforma un arxiu a un mapa d'objectes
     *
     * @return mapa d'objectes tipus assignatura
     * @throws IOException si hi ha hagut algun problema de lectura
     */
    HashMap<String, Assignatura> carregaAssignatures() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.fromJson(gd.llegeix("assignatures"), new TypeToken<HashMap<String, Assignatura>>() {
        }.getType());
    }

    /**
     * Carrega horari de disc
     *
     * @param filepath path del arxiu que volem llegir
     * @return Informació sobre l'horari
     * @throws IOException si hi ha hagut algun problema de lectura
     */
    String carregaHorari(String filepath) throws IOException {
        return gd.llegeixAbsolute(filepath);
    }

    /**
     * Transforma un objecte horari a un format json i el retorna
     *
     * @param h horari objecte
     * @return json del horari
     */
    String horariToJson(Horari h) {
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

    /**
     * Guarda un horari en format text al filepath especificat
     *
     * @param h        horari
     * @param filepath filepath
     * @throws IOException si hi ha hagut algun error d'escriptura
     */
    public void guardaHorari(String h, String filepath) throws IOException {
        gd.escriu(h, filepath);
    }

    /**
     * Serialitza totes les assignatures i les envia a que siguin guardades a disc
     *
     * @param assignatures totes les assignatures
     * @throws IOException si hi ha hagut algun error d'escriptura
     */
    void guardaAssignatures(HashMap<String, Assignatura> assignatures) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(assignatures);
        gd.escriu(json, "assignatures");
    }

    /**
     * Serialitza totes les aules i les envia a que siguin guardades a disc
     *
     * @param aules totes les aules
     * @throws IOException si hi ha hagut algun error d'escriptura
     */
    void guardaAules(HashMap<String, Aula> aules) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(aules);
        gd.escriu(json, "aules");
    }

    /**
     * Serialitza tots els plans d'estudi i les envia a que siguin guardats a disc
     *
     * @param ps tots els plans d'estudi
     * @throws IOException si hi ha hagut algun error d'escriptura
     */
    void guardaPlaDEstudis(HashMap<String, PlaEstudis> ps) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(ps);
        gd.escriu(json, "plansestudi");
    }

    /**
     * Comprova que en el filepath per defecte s'hi troben els fitxers de dades necessaris
     *
     * @return cert si ha trobat tots els fitxers, fals altrament
     */
    boolean comprovaDefaultFilepath() {
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

    /**
     * Comprova que un fitxer correspon a un fitxer d'assignatures
     *
     * @param file nom del fitxer
     * @return cert si és un fitxer d'assignatures
     */
    private boolean checkAssigFile(String file) {
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

    /**
     * Comprova que un fitxer correspon a un fitxer d'aules
     *
     * @param file nom del fitxer
     * @return cert si és un fitxer d'aules
     */
    private boolean checkAulesFile(String file) {
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

    /**
     * Comprova que un fitxer correspon a un fitxer de plans d'estudi
     *
     * @param file nom del fitxer
     * @return cert si és un fitxer de plans d'estudi
     */
    private boolean checkPlansEstudiFile(String file) {
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

    /**
     * Estableix un directori com a directori de dades del programa
     *
     * @param dir nom del directori
     */
    void setDataDirectory(File dir) {
        gd.setFilepath(dir.getAbsolutePath());
    }

    /**
     * Busca en els paths per defecte si hi ha informació
     *
     * @return informació sobre els fitxers que s'han trobat
     */
    int buscaData() {
        ArrayList<String> files = gd.getLlistaArxius();

        int assigFound = 0;
        int plansEstudiFound = 0;
        int aulesFound = 0;

        for (String f : files) {
            try {
                if (checkAssigFile(gd.llegeix(f))) {
                    gd.setFilenameAssig(f);
                    assigFound = 3;
                } else if (checkAulesFile(gd.llegeix(f))) {
                    gd.setFilenameAules(f);
                    aulesFound = 5;
                } else if (checkPlansEstudiFile(gd.llegeix(f))) {
                    gd.setFilenamePlaEst(f);
                    plansEstudiFound = 7;
                }
            } catch (IOException ignored) {
            }
        }
        return assigFound * aulesFound * plansEstudiFound;
    }

    /**
     * Estableix les rutes per defecte dels arxius i carpetes de dades
     */
    void setDefaultPaths() {
        gd.setDefaults();
    }
}
