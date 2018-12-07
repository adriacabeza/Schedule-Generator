package controllers;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GestorDisc {
    private static GestorDisc ourInstance;

    private GestorDisc() {

    }

    public static GestorDisc getInstance() {
        if (ourInstance == null) {
            ourInstance = new GestorDisc();
        }
        return ourInstance;
    }

    public void escriu(String json, String filepath) throws IOException {
        FileWriter fw = new FileWriter(filepath);
        fw.write(json);
        fw.close();
    }

    public String llegeix(String filepath) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(filepath));
        return new String(encoded, Charset.defaultCharset());
    }
}