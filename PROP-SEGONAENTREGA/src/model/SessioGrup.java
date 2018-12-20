/**
 * @Author Antoni Rambla
 */

package model;


public class SessioGrup implements Comparable<SessioGrup> {

    private Assignatura assig;
    private InfoSessions sessio;
    private Grup grup;
    private Subgrup sub;
    private int valor;

    /**
     * Crea una nova sessioGrup amb la informació pertinent
     *
     * @param assig  assignatura de l'assignació
     * @param sessio sessió pertinent
     * @param grup   grup de l'assignació pertinent
     * @param sub    subgrup de l'assignació pertinent
     * @param valor  heurística creada per a crear horaris més distribuïts
     */
    public SessioGrup(Assignatura assig, InfoSessions sessio, Grup grup, Subgrup sub, int valor) {
        this.assig = assig;
        this.sessio = sessio;
        this.grup = grup;
        this.sub = sub;
        this.valor = valor;
    }


    /**
     * Obtenir la assignatura de la sessioGrup
     *
     * @return assig
     */
    public Assignatura getAssig() {
        return assig;
    }

    /**
     * Obtenir la sessió de la sessioGrup
     *
     * @return la sessió de la sessioGrup
     */
    public InfoSessions getSessio() {
        return sessio;
    }

    /**
     * Obtenir grup de la sessioGrup
     *
     * @return el grup de la sessioGrup
     */
    public Grup getGrup() {
        return grup;
    }

    /**
     * Obtenir el subgrup de la sessioGrup
     *
     * @return el subgrup de la sessioGrup
     */
    public Subgrup getSub() {
        return sub;
    }

    /**
     * Obtenir el valor de la sessioGrup
     *
     * @return el valor de la sessioGrup
     */
    public int getValor() {
        return valor;
    }

    /**
     * Actualitza l'assignatura de la sessioGrup
     *
     * @param assig la nova assignaturaa de la sessioGrup
     */
    public void setAssig(Assignatura assig) {
        this.assig = assig;
    }

    /**
     * Actualitza la sessió d'una sessioGrup
     *
     * @param sessio la nova sessió de la sessioGrup
     */
    public void setSessio(InfoSessions sessio) {
        this.sessio = sessio;
    }

    /**
     * Actualitza el grup de la sessioGrup
     *
     * @param grup el nou grup de la sessioGrup
     */
    public void setGrup(Grup grup) {
        this.grup = grup;
    }

    /**
     * Actualitza el subgrup de la sessioGrup
     *
     * @param sub el nou subgrup de la sessioGrup
     */
    public void setSubgrup(Subgrup sub) {
        this.sub = sub;
    }


    /**
     * Actualitza el valor de la sessioGrup
     *
     * @param valor el nou valor de la sessioGrup
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * Compara dues assignaturesMonosessió
     *
     * @param o sessioGrup a comparar
     * @return retorna un enter que representa la diferència entre les assignaturesmonosessió
     */
    @Override
    public int compareTo(SessioGrup o) {
        int comparevalue = o.getValor();
        return comparevalue - this.valor;
    }
}
