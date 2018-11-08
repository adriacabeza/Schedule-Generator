package model;

public class RestriccioCorrequisit extends Restriccions {

    private int id;
    private boolean active;

    public RestriccioCorrequisit() {
        this.id = 1;
        this.active = true;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public boolean isable(Assignacio[][][] horari, int hora, int dia, int i,Assignatura assig) {
          for (int i = 0; i < aules.size(); ++i) {
            if (horari[hora][dia][i].getAssignatura().getCorrequisits().contains(assig)) return false;
  }
            if (horari[hora][dia][i].getAssignatura().getQuadrimestre() == assig.getQuadrimestre()) return false;
        }
    }
}
