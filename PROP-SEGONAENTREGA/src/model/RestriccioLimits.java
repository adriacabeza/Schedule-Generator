/**
 * @author Antoni Rambla
 */
package model;

public class RestriccioLimits extends RestriccioUnaria {

    /**
     * Constructora de restricció límits
     */
    public RestriccioLimits() {
        super(10);
    }

    /**
     * Retorna si es possible realitzar una assignació comprovant els límits de la franja horària on es pot fer classe
     * @param posaula posició que indica una aula
     * @param dia dia
     * @param hora hora
     * @param assig assignatura
     * @param duracio duració d'una assignació
     * @param aula aula
     * @param horari horari
     * @return retorna true si és pot efectuar l'assignació segons aquesta restricció o false si no és pot
     */
    @Override
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
