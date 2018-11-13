package model;


public class AssignaturaMonosessio implements Comparable<AssignaturaMonosessio> {

    private Assignatura assig;
    private InfoSessions sessio;
    private Grup grup;
    private Subgrup sub;
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
    public AssignaturaMonosessio(Assignatura assig, InfoSessions sessio, Grup grup, Subgrup sub, int valor) {
        this.assig = assig;
        this.sessio = sessio;
        this.grup = grup;
        this.sub = sub;
        this.valor = valor;
    }


    /**
     * Obtenir la assignatura de la assignaturamonosessio
     *
     * @return assig
     */
    public Assignatura getAssig() {
        return assig;
    }

    /**
     * Obtenir la sessió de la assignaturamonosessió
     *
     * @return la sessió de la assignaturamonosessió
     */
    public InfoSessions getSessio() {
        return sessio;
    }

    /**
     * Obtenir grup de l'assignaturaMonosessió
     *
     * @return el grup de l'assignaturaMonosessió
     */
    public Grup getGrup() {
        return grup;
    }

    /**
     * Obtenir el subgrup de l'assignaturaMonosessió
     *
     * @return el subgrup de l'assignaturaMonosessió
     */
    public Subgrup getSub() {
        return sub;
    }

    /**
     * Obtenir el valor de l'assignaturaMonosessió
     *
     * @return el valor de l'assignaturaMonosessió
     */
    public int getValor() {
        return valor;
    }

    /**
     * Actualitza l'assignatura de l'assignaturaMonosessió
     *
     * @param assig la nova assignaturaa de l'assignaturaMonosessió
     */
    public void setAssig(Assignatura assig) {
        this.assig = assig;
    }

    /**
     * Actualitza la sessió d'una assignaturamonosessió
     *
     * @param sessio la nova sessió de l'assignaturamonosessió
     */
    public void setSessio(InfoSessions sessio) {
        this.sessio = sessio;
    }

    /**
     * Actualitza el grup de l'assignaturamonosessió
     *
     * @param grup el nou grup de l'assignaturamonosessió
     */
    public void setGrup(Grup grup) {
        this.grup = grup;
    }

    /**
     * Actualitza el subgrup de l'assignaturamonosessió
     *
     * @param sub el nou subgrup de l'assignaturamonosessió
     */
    public void setSubgrup(Subgrup sub) {
        this.sub = sub;
    }


    /**
     * Actualitza el valor de l'assignaturamonosessió
     *
     * @param valor el nou valor de l'assignaturamonosessió
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * Compara dues assignaturesMonosessió
     *
     * @param o assignaturamonosessió a comparar
     * @return retorna un enter que representa la diferència entre les assignaturesmonosessió
     */
    @Override
    public int compareTo(AssignaturaMonosessio o) {
        int comparevalue = o.getValor();
        return comparevalue - this.valor;
    }
}
