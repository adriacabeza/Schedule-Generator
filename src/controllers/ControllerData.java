package controllers;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Assignatura;
import model.Aula;
import model.PlaEstudis;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ControllerData {
    public static void main(String[] args) {

        Type aulatype = new  TypeToken<HashMap<String, Aula>>(){}.getType();
        Type assignaturatype = new TypeToken<HashMap<String, Assignatura>>(){}.getType();
        Type plaestudistype = new TypeToken<HashMap<String, PlaEstudis>>(){}.getType();


        HashMap<String, Aula> AulaArray = new Gson().fromJson("{\"aules\":{\n" +
                "    \"A5101\":{\"tAula\":\"NORMAL\", \"edifici\":\"A5\", \"planta\":\"1\", \"aula\":\"01\", \"capacitat\":50},\n" +
                "    \"A5102\":{\"tAula\":\"NORMAL\", \"edifici\":\"A5\", \"planta\":\"1\", \"aula\":\"02\", \"capacitat\":40},\n" +
                "    \"A5103\":{\"tAula\":\"NORMAL\", \"edifici\":\"A5\", \"planta\":\"1\", \"aula\":\"03\", \"capacitat\":40},\n" +
                "    \"A4201\":{\"tAula\":\"LABORATORI\", \"edifici\":\"A4\", \"planta\":\"2\", \"aula\":\"01\", \"capacitat\":30},\n" +
                "    \"A3203\":{\"tAula\":\"LABORATORI\", \"edifici\":\"A4\", \"planta\":\"1\", \"aula\":\"01\", \"capacitat\":30},\n" +
                "    \"A3203\":{\"tAula\":\"PCS\", \"edifici\":\"A3\", \"planta\":\"2\", \"aula\":\"03\", \"capacitat\":45},\n" +
                "    \"A3103\":{\"tAula\":\"PCS\", \"edifici\":\"A3\", \"planta\":\"1\", \"aula\":\"03\", \"capacitat\":45},\n" +
                "    \"A5105\":{\"tAula\":\"PCS\", \"edifici\":\"A5\", \"planta\":\"1\", \"aula\":\"05\", \"capacitat\":40}\n" +
                "}\n" +
                "}",aulatype);


        HashMap<String, Assignatura> AssignaturaArray = new Gson().fromJson("",assignaturatype);
        HashMap<String,PlaEstudis> PlaEstudiArray = new Gson().fromJson("",plaestudistype);



    }

}



