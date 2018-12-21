/**
 * @author Aina Garcia
 */

package controllers;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GestorDisc {
    private static GestorDisc ourInstance;
    private String filepath;
    private String filenameAssig;
    private String filenamePlaEst;
    private String filenameAules;
    private String filenameHorari;

    /**** default constants ****/
    private final static String defaultFolder = "data";
    private final static String defaultAssigFN = "assignatures.json";
    private final static String defaultPlaEstFN = "plansestudi.json";
    private final static String defaultAulesFN = "aules.json";

    private GestorDisc() {
        setDefaults();
    }

    /**
     * Mètode per accedir a la instancia del Gestor de disc
     * @return La instancia del Gestor de disc
     */
    public static GestorDisc getInstance() {
        if (ourInstance == null) {
            ourInstance = new GestorDisc();
        }
        return ourInstance;
    }

    /**
     * Escriu a disc un dels fitxers predefinits, o bé escriu directament a l'arxiu indicat
     *
     * @param json  text a escriure
     * @param tipus aula, assignatura, plans d'estudi o un path absolut
     * @throws IOException si hi ha hagut algun problema d'escriptura
     */
    public void escriu(String json, String tipus) throws IOException {
        String filepath = this.filepath + "/";
        switch (tipus.toUpperCase()) {
            case "ASSIGNATURES":
                filepath = filepath.concat(filenameAssig);
                break;
            case "AULES":
                filepath = filepath.concat(filenameAules);
                break;
            case "PLANSESTUDI":
                filepath = filepath.concat(filenamePlaEst);
                break;
            default:
                filepath = tipus;
        }

        FileWriter fw = new FileWriter(filepath);
        fw.write(json);
        fw.close();
    }

    /**
     * Llegeix de disc un dels fitxers predefinits, o un arxiu qualsevol dins de la carpeta de dades
     *
     * @param tipus aula, assignatura, o plans d'estudi
     * @return text llegit
     * @throws IOException              si hi ha hagut algun problema d'escriptura
     * @throws IllegalArgumentException si el tipus d'arxiu es incorrecte
     */
    String llegeix(String tipus) throws IOException, IllegalArgumentException {
        String filepath = this.filepath + "/";
        switch (tipus.toUpperCase()) {
            case "ASSIGNATURES":
                filepath = filepath.concat(filenameAssig);
                break;
            case "AULES":
                filepath = filepath.concat(filenameAules);
                break;
            case "PLANSESTUDI":
                filepath = filepath.concat(filenamePlaEst);
                break;
            default:
                filepath = filepath.concat(tipus);
        }
        byte[] encoded = Files.readAllBytes(Paths.get(filepath));
        return new String(encoded, Charset.defaultCharset());
    }

    /**
     * Llegeix un arxiu des de qualsevol punt del sistema de fitxers.
     * @param absoluteFilepath El path absolut a l'arxiu que es vol llegir
     * @return L'arxiu en un String
     * @throws IOException Excepció si hi ha algun problema al llegir de disc
     */
    String llegeixAbsolute(String absoluteFilepath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(absoluteFilepath));
        return new String(encoded, Charset.defaultCharset());
    }

    /**
     * Estableix el nom del directori de dades
     *
     * @param filepath nom del directori
     */
    void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Estableix el nom del arxiu d'assignatures
     *
     * @param filenameAssig nom del arxiu
     */
    void setFilenameAssig(String filenameAssig) {
        this.filenameAssig = filenameAssig;
    }

    /**
     * Estableix el nom del arxiu de plans d'estudi
     *
     * @param filenamePlaEst nom del arxiu
     */
    void setFilenamePlaEst(String filenamePlaEst) {
        this.filenamePlaEst = filenamePlaEst;
    }

    /**
     * Estableix el nom del arxiu d'aules
     *
     * @param filenameAules nom del arxiu
     */
    void setFilenameAules(String filenameAules) {
        this.filenameAules = filenameAules;
    }

    /**
     * Estableix el nom del arxiu d'horari
     *
     * @param filenameHorari nom del arxiu
     */
    public void setFilenameHorari(String filenameHorari) {
        this.filenameHorari = filenameHorari;
    }

    /**
     * Reset dels paths d'arxius als paths per defecte
     */
    void setDefaults() {
        new File(defaultFolder).mkdirs();

        filepath = defaultFolder;
        filenameAssig = defaultAssigFN;
        filenameAules = defaultAulesFN;
        filenamePlaEst = defaultPlaEstFN;
    }

    /**
     * Obté una llista d'arxius que hi ha en un directori
     *
     * @return llista d'arxius
     */
    ArrayList<String> getLlistaArxius() {
        ArrayList<String> res = new ArrayList<>();

        File[] files = new File(this.filepath).listFiles();

        for (File f : files) {
            if (f.isFile()) res.add(f.getName());
        }

        return res;
    }
}