package drivers;

import model.Aula;

import java.util.Scanner;

public class DriverHorari {


    public static void mostraopcions() {
        System.out.println("Escull una opcio:");
        System.out.println("1: Crear Horari");
        System.out.println("2: Consultar Horari");
        System.out.println("3: Sortir");
    }


    public static void opcioinavalida() {
        System.out.println("Has escollit una opcio incorrecta.");
        System.out.println("");
    }
}