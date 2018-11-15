package model;

public class RestriccioAulaDia extends Restriccions {

    private int dia;
    private Aula aula;

    //TODO: porque guardar una aula entera en restricció aula i no un identificador?

    /**
     * Crea una restricció on es comprova si es pot realitzar una assignació en una determinada aula en un determinat dia
     *
     * @param dia dia a comprovar
     */
    public RestriccioAulaDia(int dia, Aula aula) {
        super(6);
        this.dia = dia;
        this.aula = aula;
    }

    /**
     * Retorna si es possible realitzar una assignació en una determinada aula en un determinat dia
     *
     * @param aula aula que es comprova
     * @param dia  dia que es comprova
     * @return true si es pot realitzar l'assignació
     */
    public boolean isable(Aula aula, int dia) {
        if (this.dia == dia && aula.getKey() == this.aula.getKey()) return false;
        return true;
    }

    public int getDia(){return dia;}
    public Aula getAula(){return aula;}

}

