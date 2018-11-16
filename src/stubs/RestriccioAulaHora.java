/**
 * @Author Antoni Rambla
 */

package stubs;

import model.Restriccions;

public class RestriccioAulaHora extends Restriccions {

    private int dia;
    private int hora;
    private model.Aula aula;

    public RestriccioAulaHora(int dia, int hora, Aula aula) {
        super(7);
        this.dia = 0;
        this.hora = 0;
        this.aula = new model.Aula("A5",0,2, model.Aula.TipusAula.NORMAL,60);
    }

    public boolean isAble(Aula aula, int dia, int hora) {
        return true;
    }

    public int getDia(){
        return 0;
    }

    public int getHora(){
        return 0;
    }

    public model.Aula getAula(){
        return (new model.Aula("A5",0,2, model.Aula.TipusAula.NORMAL,60));
    }

}
