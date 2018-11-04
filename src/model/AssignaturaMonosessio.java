package model;


public class AssignaturaMonosessio {

    private Assignatura assig;
    private InfoSessions sessio;
    private int valor;

    public AssignaturaMonosessio(Assignatura assig, InfoSessions sessio, int valor){
        this.assig = assig;
        this.sessio = sessio;
        this.valor = valor;
    }


    public Assignatura getAssig(){
        return assig;
    }
    public InfoSessions getSessio(){
        return  sessio;
    }
    public int getValor(){
        return valor;
    }
    public void setAssig(Assignatura assig){
        this.assig = assig;
    }
    public void setSessio(InfoSessions sessio){
        this.sessio = sessio;
    }
    public void setValor(int valor){
        this.valor = valor;
    }
}
