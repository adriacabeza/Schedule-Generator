package model;

public class RestriccioAula extends Restriccions {

    /**
     * Crea una restricció on es comproven la capacitat i tipus d'una aula
     */
    public RestriccioAula() {
        super(3);

    }

    /**
     * Retorna si és possible assignar una determinada aula a un grup segons la capacitat i tipus
     *
     * @param aula  aula que es comprova
     * @param assig assignatura que es comprova
     * @return true si es pot realitzar la assignació a l'aula
     */
    public boolean isable(Aula aula, AssignaturaMonosessio assig) {
      //  return true;
      return (aula.getTipusAula() == assig.getSessio().gettAula());
    }


}
