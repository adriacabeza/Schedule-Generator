package model;

import exceptions.NotFoundException;

import java.util.ArrayList;

public class Horari {

    private String fromInt2dia(int dia) {
        if (dia == 0) return "Dilluns";
        else if (dia == 1) return "Dimarts";
        else if (dia == 2) return "Dimecres;";
        else if (dia == 3) return "Dijous";
        else return "Divendres";

    }

    private int fromdia2int(String dia) {
        switch (dia) {
            case "Dilluns":
                return 0;
            case "Dimarts":
                return 1;
            case "Dimecres":
                return 2;
            case "Dijous":
                return 3;
            default:
                return 4;
        }
    }


    private int gethora(int hora) {
        if (hora == 0) return 8;
        else if (hora == 1) return 9;
        else if (hora == 2) return 10;
        else if (hora == 3) return 11;
        else if (hora == 4) return 12;
        else if (hora == 5) return 13;
        else if (hora == 6) return 14;
        else if (hora == 7) return 15;
        else if (hora == 8) return 16;
        else if (hora == 9) return 17;
        else if (hora == 10) return 18;
        else return 19;
    }

    private boolean comprovar_restricciones_teoria(int aula1, Grup grup, int dia, int hora, Assignatura assig, int duracio) {
        Aula aula = aules2.get(aula1);
        if (aula.getCapacitat() <= grup.getCapacitat()) {
            System.out.println("La capacitat és insuficient");
            return false;
        }
        for (int i = 0; i < duracio; ++i) {
            if ((hora + i) >= 12){
                System.out.println("Se pasa del horario");
                return false;
            }
            else if (horari[hora + i][dia][aula1] != null) {
                System.out.println("Con la assignatura "+assig.getNom()+" fallo.");
                System.out.println("Ya está puesta la hora "+ (hora+i) + ", el dia "+ fromInt2dia(dia));
                return false;
            }
        }
        return true;
    }


    private boolean comprovar_restricciones_lab(int aula1, Subgrup subgrup, int dia, int hora, Assignatura assig, int duracio) {
        Aula aula = aules2.get(aula1);
        if (aula.getCapacitat() <= subgrup.getCapacitat()) {
            System.out.println("La capacitat és insuficient");
            return false;
        }
        for (int i = 0; i < duracio; ++i) {
            if ((hora + i) >= 12){
                System.out.println("Se pasa del horario");
                return false;
            }
            else if (horari[hora + i][dia][aula1] != null) {
                System.out.println("Con la assignatura "+assig.getNom()+" fallo.");
                System.out.println("Ya está puesta la hora "+ (hora+i) + ", el dia "+ fromInt2dia(dia));
                return false;
            }
        }
        return true;
    }



    private boolean creaHorari(int i, Assignacio[][][] horari) {

        if (i == (mishmash.size())) return true;
        int duracio = mishmash.get(i).getSessio().getDuracioSessions();
        boolean teoria = (mishmash.get(i).getSessio().getClass() == Teoria.class);
        for (int l = 0; l < 5; ++l) {
            for (int m = 0; m < 12; ++m) {
                for (int k = 0; k < aules2.size(); ++k) {
                    if (horari[m][l][k] == null) {
                        if (teoria) {
                            if (comprovar_restricciones_teoria(k, mishmash.get(i).getGrup(), l, m, mishmash.get(i).getAssig(), duracio)) {
                                for (int z = 0; z < duracio; ++z) {
                                    horari[m + z][l][k] = new AssignacioT(fromInt2dia(l), m + z, aules2.get(k), mishmash.get(i).getSessio().gettAula(), mishmash.get(i).getAssig(), mishmash.get(i).getGrup());
                                    System.out.println(mishmash.get(i).getAssig().getNom() + " ficada a les " + gethora(m + z) + " el " + fromInt2dia(l));
                                }
                                if (creaHorari(i + 1, horari)) return true;
                                else {
                                    //no se ha podido hacer, borramos lo que hemos puesto
                                    for (int z = 0; z < duracio; ++z) {
                                        horari[m + z][l][k] = null;
                                    }
                                }
                            }
                        } else {
                            if (comprovar_restricciones_lab(k, mishmash.get(i).getSub(), l, m, mishmash.get(i).getAssig(), duracio)) {
                                for (int z = 0; z < duracio; ++z) {
                                    horari[m + z][l][k] = new AssignacioL(fromInt2dia(l), m + z, aules2.get(k), mishmash.get(i).getSessio().gettAula(), mishmash.get(i).getAssig(), mishmash.get(i).getSub());
                                    System.out.println(mishmash.get(i).getAssig().getNom() + " ficada a les " + gethora(m + z) + " el " + fromInt2dia(l));
                                }
                                if (creaHorari(i + 1, horari)) return true;
                                else {
                                    //no se ha podido hacer, borramos lo que hemos puesto
                                    for (int z = 0; z < duracio; ++z) {
                                        horari[m + z][l][k] = null;
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }

        System.out.println("no se ha podido hacer el horario de esta manera, let's backtrack");
        return false;

    }


    public boolean generaHorari() {
        assignatures2 = new ArrayList<>(assignatures.values());
        aules2 = new ArrayList<>(aules.values());
        horari = new Assignacio[12][5][aules2.size()];

        try {
            mishmash = mishmash(assignatures2);
            ordena_mishamash();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return creaHorari(0, horari);
    }



}
