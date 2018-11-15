package model;

public class RestriccioAulaHora extends Restriccions {

    private int dia;
    private int hora;
    private Aula aula;

    /**
     * Crea una restricció on es comprova si es pot realitzar una assignació en una determinada aula en una determinada hora d'un determinat dia
     *
     * @param dia  dia a comprovar
     * @param hora hora a comprovar
     * @param aula aula a comprovar
     */
    public RestriccioAulaHora(int dia, int hora, Aula aula) {
        super(7);
        this.dia = dia;
        this.hora = hora;
        this.aula = aula;
    }

    /**
     * Retorna si es possible realitzar una assignació en una determinada aula en una determinada hora d'un determinat dia
     *
     * @param aula aula que es comprova
     * @param dia  dia que es comprova
     * @param hora hora que es comprova
     * @return true si es pot realitzar l'assignació
     */
    public boolean isable(Aula aula, int dia, int hora) {
        if (aula.getKey() == this.aula.getKey() && dia == this.dia && hora == this.hora) return false;
        return true;
    }

    public int getDia(){return dia;}
    public int getHora(){return hora;}
    public Aula getAula(){return aula;}

}
