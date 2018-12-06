package model;

public class RestriccioCapacitatAula extends  RestriccioUnaria {
    /**
     * Es crea una restricció
     *
     * @param id identificador de la restricció
     */
    public RestriccioCapacitatAula(int id) {
        super(10);
    }

    @Override
    public boolean isAble(int posaula, int dia, int hora, SessioGrup assig, int duracio, Aula aula, Assignacio[][][] horari) {
            return aula.getCapacitat() >= assig.getGrup().getCapacitat();
    }

}
