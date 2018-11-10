package drivers;


import controllers.CtrlDomini;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class DriverCtrDomini {

    public static void menu() {
        System.out.println("Escull una opció");
        System.out.println("1: crea assignatures");
        System.out.println("2: crea pla d'estudis");
        System.out.println("3: crea aules");
        System.out.println("4: consulta atributs");
        System.out.println("5: modifica atributs");
        System.out.println("6: genera horari");
        System.out.println("7: consulta horari");
        System.out.println("8: sortir");

    }

    public static void creaAssignatures(Scanner s, CtrlDomini c) {
        String nom, tipusAula;
        Aula.TipusAula Atipus;
        int opcio, opciol, quadrimestre, nsest, nsesl, dsest, dsesl, gnum, gcap, gsub;
        Teoria t;
        Laboratori l = null;
        opcio = 0;
        while (opcio != 1) {
            System.out.println("introdueix el nom de la assignatura i el quadrimestre al qual pertany");
            nom = s.next();
            quadrimestre = s.nextInt();
            System.out.println("Introdueix el numero de sessions, la duracio de sessions, el tipus d'aula de les sessions de teoria, el numero de grups de la assignatura, la capacitat d'aquests grups, i el numero de subgrups");
            nsest = s.nextInt();
            dsest = s.nextInt();
            tipusAula = s.next();
            gnum = s.nextInt();
            gcap = s.nextInt();
            gsub = s.nextInt();
            Atipus = Aula.stringToTipusAula(tipusAula);
            if (Atipus != null) {
                t = new Teoria(nsest, dsest, Atipus);
            } else {
                System.out.println("Tipus d'aula incorrecte, recorda que pot ser \"pcs, normal, laboratori\"");
                continue;
            }
            System.out.println("introdueix un 0 si la assigantura tindra sessions de laboratori, qualsevol altre numero en cas contrari");
            opciol = s.nextInt();
            if (opciol == 0) {
                System.out.println("Introdueix el numero de sessions, la duracio de sessions i el tipus d'aula de les sessions de laboratori");
                nsesl = s.nextInt();
                dsesl = s.nextInt();
                tipusAula = s.next();
                Atipus = Aula.stringToTipusAula(tipusAula);
                if (Atipus != null) {
                    l = new Laboratori(nsesl, dsesl, Atipus);
                } else {
                    System.out.println("Tipus d'aula incorrecte, recorda que pot ser \"pcs, normal, laboratori\"");
                    continue;
                }
            }
            try {
                c.crearAssignatura(nom, quadrimestre, t, l);
                c.modificarGrups(nom, gnum, gcap, gsub);
            } catch (RestriccioIntegritatException e) {
                e.printStackTrace();
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("introdueix un 1 per a deixar de crear plans, qualsevol altre numero per a seguir");
            opcio = s.nextInt();
        }
    }

    public static void crearPlaEstudis(Scanner s, CtrlDomini c) {
        String aux;
        Date date;
        int opt = 0;
        while (opt != 1) {
            System.out.println("introdueix el nom i l'any <YYYY>");
            DateFormat format = new SimpleDateFormat("YYYY");
            aux = s.next();
            try {
                date = format.parse(s.next());
                c.crearPlaEstudis(aux, date);

            } catch (ParseException e) {
                System.out.println("error en l'input de any");
            } catch (RestriccioIntegritatException e) {
                e.printStackTrace();
            }
            System.out.println("introdueix un 1 per a deixar de crear plans, qualsevol altre numero per a seguir");
            opt = s.nextInt();
        }
    }

    public static void crearAules(Scanner s, CtrlDomini c) {
        String edifici, tipusaula;
        int opt, planta, aula, capacitat;
        Aula.TipusAula tAula;
        opt = 0;
        while (opt != 1) {
            System.out.println("Introdueix el nom, la planta, el numero, el tipus de l'aula(en cas d'input erroni sera una aula normal) i la capacitat");
            edifici = s.next();
            planta = s.nextInt();
            aula = s.nextInt();
            tipusaula = s.next();
            if (tipusaula.equalsIgnoreCase("pcs")) {
                tAula = Aula.TipusAula.PCS;
            } else if (tipusaula.equalsIgnoreCase("laboratori")) {
                tAula = Aula.TipusAula.LABORATORI;
            } else
                tAula = Aula.TipusAula.NORMAL;
            capacitat = s.nextInt();
            try {
                c.creaAula(edifici, planta, aula, capacitat, tAula, null);
            } catch (RestriccioIntegritatException e) {
                e.printStackTrace();
            }
            System.out.println("introdueix un 1 per a deixar de crear aules, qualsevol altre numero per a seguir");
            opt = s.nextInt();
        }
    }


    public static void main(String[] args) throws NotFoundException {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        CtrlDomini ctrl = CtrlDomini.getInstance();
        while (option != 8) {
            menu();
            option = scan.nextInt();
            switch (option) {
                case 1:
                    creaAssignatures(scan, ctrl);
                    break;
                case 2:
                    crearPlaEstudis(scan, ctrl);
                    break;
                case 3:
                    crearAules(scan, ctrl);
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    ctrl.generaHorari();
                    break;
                case 7:
                    ctrl.printarHorari_hora(0);
                    break;
                case 11:
                    break;
                default:
                    System.out.println("Opció incorrecte. Introdueix una opcio vàlida");
                    break;

            }

        }
    }
}
