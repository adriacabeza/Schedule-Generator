package model;


public class AssignaturaMonosessio {

    private Assignatura assig;
    private InfoSessions sessio;
    private Grup grup;
    private Subgrup sub;
    private int valor;

    public AssignaturaMonosessio(Assignatura assig, InfoSessions sessio, Grup grup, Subgrup sub, int valor){
        this.assig = assig;
        this.sessio = sessio;
        this.grup = grup;
        this.sub = sub;
        this.valor = valor;
    }


    public Assignatura getAssig(){
        return assig;
    }
    public InfoSessions getSessio(){
        return  sessio;
    }
    public Grup getGrup () { return grup; }
    public Subgrup getSub(){ return  sub; }
    public int getValor(){
        return valor;
    }
    public void setAssig(Assignatura assig){
        this.assig = assig;
    }       //TODO: setters innecesarios?
    public void setSessio(InfoSessions sessio){
        this.sessio = sessio;
    }
    public void setGrup(Grup grup){
        this.grup = grup;
    }
    public void setSubgrup(Subgrup sub){
        this.sub = sub;
    }
    public void setValor(int valor){
        this.valor = valor;
    }
}
