package model;

public class RestriccioLimits extends RestriccioUnaria {
    /**
     * Es crea una restricció
     *
     * @param id identificador de la restricció
     */
    public RestriccioLimits() {
        super(10);
    }

    @Override //EN AQUEST NO HI HA PROBLEMA PQ HE CANVIAR ELS PARAMETRES DE RESTRICCIO UNARIA
    public boolean isAble(int posaula, int dia, int hora, SessioGrup assig, int duracio, Aula aula, Assignacio[][][] horari) {
        for (int i = 0; i < duracio; ++i) {
            if ((hora + i) >= 12) {
                return false;
            } else if (horari[hora + i][dia][posaula] != null) {
                return false;
            }
        }
        return true;
    }
}
