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

    public boolean isAble(Aula aula, SessioGrup assig){
        return aula.getCapacitat() >= assig.getGrup().getCapacitat();
    }
}
