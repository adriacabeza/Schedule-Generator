package stubs;
/**
 * @Author Antoni Rambla
 */

public class RestriccioAulaDia extends Restriccions {

        private int dia;
        private model.Aula aula;


        public RestriccioAulaDia(int dia, Aula aula) {
            super(6);
            this.dia = 0;
            this.aula = new model.Aula("A5",0,2, model.Aula.TipusAula.NORMAL,60);
        }

        public boolean isAble(model.Aula aula, int dia) {
            if (this.dia == dia && aula.getKey().equals(this.aula.getKey())) return false;
            return true;
        }

        public int getDia() {
            return 0;
        }

        public Aula getAula() {
            return null;
        }

}

