/**
 * @Author Adria Cabeza
 */
package stubs;

import model.Assignatura;
import model.Aula;
import model.Grup;
import model.InfoSessions;
import model.Laboratori;
import model.Subgrup;

public class SessioGrup implements Comparable<model.SessioGrup> {

    private model.Assignatura assig;
    private model.InfoSessions sessio;
    private model.Grup grup;
    private model.Subgrup sub;
    private int valor;

    /**
     * Crea una nova assignaturamonosessió amb la informació pertinent
     *
     * @param assig  assignatura de l'assignació
     * @param sessio sessió pertinent
     * @param grup   grup de l'assignació pertinent
     * @param sub    subgrup de l'assignació pertinent
     * @param valor  heurística creada per a crear horaris més distribuïts
     */
    public SessioGrup(model.Assignatura assig, model.InfoSessions sessio, model.Grup grup, model.Subgrup sub, int valor) {
        this.assig = new Assignatura("EC", "abr", "sample descr", 0);
        this.sessio = new Laboratori(1,1, Aula.TipusAula.NORMAL);
        this.grup = new Grup(10,1,1);
        this.sub = new Subgrup(11,1,1);
        this.valor = 1;
    }


    /**
     * Obtenir la assignatura de la assignaturamonosessio
     *
     * @return assig
     */
    public model.Assignatura getAssig() {
        return assig;
    }

    /**
     * Obtenir la sessió de la assignaturamonosessió
     *
     * @return la sessió de la assignaturamonosessió
     */
    public model.InfoSessions getSessio() {
        return sessio;
    }

    /**
     * Obtenir grup de l'assignaturaMonosessió
     *
     * @return el grup de l'assignaturaMonosessió
     */
    public model.Grup getGrup() {
        return grup;
    }

    /**
     * Obtenir el subgrup de l'assignaturaMonosessió
     *
     * @return el subgrup de l'assignaturaMonosessió
     */
    public model.Subgrup getSub() {
        return sub;
    }

    /**
     * Obtenir el valor de l'assignaturaMonosessió
     *
     * @return el valor de l'assignaturaMonosessió
     */
    public int getValor() {
        return 1;
    }

    /**
     * Actualitza l'assignatura de l'assignaturaMonosessió
     *
     * @param assig la nova assignaturaa de l'assignaturaMonosessió
     */
    public void setAssig(Assignatura assig) {

    }

    /**
     * Actualitza la sessió d'una assignaturamonosessió
     *
     * @param sessio la nova sessió de l'assignaturamonosessió
     */
    public void setSessio(InfoSessions sessio) {

    }

    /**
     * Actualitza el grup de l'assignaturamonosessió
     *
     * @param grup el nou grup de l'assignaturamonosessió
     */
    public void setGrup(Grup grup) {

    }

    /**
     * Actualitza el subgrup de l'assignaturamonosessió
     *
     * @param sub el nou subgrup de l'assignaturamonosessió
     */
    public void setSubgrup(Subgrup sub) {

    }


    /**
     * Actualitza el valor de l'assignaturamonosessió
     *
     * @param valor el nou valor de l'assignaturamonosessió
     */
    public void setValor(int valor) {

    }

    /**
     * Compara dues assignaturesMonosessió
     *
     * @param o assignaturamonosessió a comparar
     * @return retorna un enter que representa la diferència entre les assignaturesmonosessió
     */
    @Override
    public int compareTo(model.SessioGrup o) {
        int comparevalue = o.getValor();
        return comparevalue - this.valor;
    }
}
