package model;

public class RestriccioCapacitatAula extends  RestriccioUnaria {
    /**
     * Es crea una restricció on mirem si un grup cap a una aula
     *
     */
    public RestriccioCapacitatAula() {
        super(10);
    }
    /**
     * Retorna si en la aula cap el grup que volem comprovar
     *
     * @param aula aula que es comprova
     * @param assig sessioGrup del qual mirem si el seu grup cap a la aula
     * @return true si es pot realitzar l'assignació
     */
    @Override
    public boolean isAble(int posaula, int dia, int hora, SessioGrup assig, int duracio, Aula aula, Assignacio[][][] horari) {
            return aula.getCapacitat() >= assig.getGrup().getCapacitat();
    }

}
