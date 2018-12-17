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

    public static GestorDisc getInstance() {
        if (ourInstance == null) {
            ourInstance = new GestorDisc();
        }
        return ourInstance;
    }

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
            default : throw new IllegalArgumentException();
        }

        FileWriter fw = new FileWriter(filepath);
        fw.write(json);
        fw.close();
    }

    public String llegeix(String tipus) throws IOException, IllegalArgumentException {
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
            default : filepath = filepath.concat(tipus);
        }
        byte[] encoded = Files.readAllBytes(Paths.get(filepath));
        return new String(encoded, Charset.defaultCharset());
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setFilenameAssig(String filenameAssig) {
        this.filenameAssig = filenameAssig;
    }

    public void setFilenamePlaEst(String filenamePlaEst) {
        this.filenamePlaEst = filenamePlaEst;
    }

    public void setFilenameAules(String filenameAules) {
        this.filenameAules = filenameAules;
    }

    public void setFilenameHorari(String filenameHorari) {
        this.filenameHorari = filenameHorari;
    }

    public void setDefaults() {
        new File (defaultFolder).mkdirs();

        filepath = defaultFolder;
        filenameAssig = defaultAssigFN;
        filenameAules = defaultAulesFN;
        filenamePlaEst = defaultPlaEstFN;
    }

    public ArrayList<String> getLlistaArxius() {
        ArrayList<String> res = new ArrayList<>();

        File[] files = new File(this.filepath).listFiles();

        for (File f:files) {
            if (f.isFile()) res.add(f.getName());
        }

        return res;
    }
}