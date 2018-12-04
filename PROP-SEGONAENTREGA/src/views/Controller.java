package views;

import controllers.CtrlDomini;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

import java.io.IOException;


public class Controller {

    /* Welcome */
    @FXML
    BorderPane welcome_content = new BorderPane();

    /* List */
    @FXML
    BorderPane list_content = new BorderPane();
    @FXML
    Label list_tab_title = new Label();
    @FXML
    Button plus_button = new Button();
    @FXML
    Button edit_button = new Button();
    @FXML
    Button delete_button = new Button();
    @FXML
    BorderPane list_inner_content = new BorderPane();
    @FXML
    ListView<String> list_view = new ListView();

    /* Specific */
    @FXML
    BorderPane specific_content = new BorderPane();
    @FXML
    Label specific_tab_title = new Label();
    @FXML
    BorderPane specific_inner_content = new BorderPane();


    private int state;
    private CtrlDomini controladorDomini;

    /**
     * Carrega la pantalla principal i les dades dels fitxers JSON
     */
    public void initialize(){
        mostraInici();
        controladorDomini = CtrlDomini.getInstance();

        try {
            controladorDomini.carrega(); //TODO error check
        } catch (IOException e) {
            //TODO afegir la opcio de carregar manualment o de tornar a intentar, mostrant a l'usuari quin arxiu no s'ha pogut carregar
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Error al carregar el fitxer");
            a.setContentText("No s'ha trobat el fitxer");
            a.show();
        }

        delete_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!list_view.getSelectionModel().isEmpty()) {
                    handleDelete(list_view.getSelectionModel().getSelectedItem());
                }
            }
        });

        edit_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!list_view.getSelectionModel().isEmpty()) {
                    handleModify(list_view.getSelectionModel().getSelectedItem());
                }
            }
        });

        plus_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleAdd();
            }
        });
    }

    /**
     * Esborra un element del tipus de llista concret segons de la pantalla on es troba
     * @param item identificador del item a esborrar
     */
    private void handleDelete(String item) {
        switch (state){
            case 1:
                try {
                    controladorDomini.esborrarPlaEstudis(item);
                } catch (NotFoundException e) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Pla no trobat");
                    a.setContentText("Error intern");
                    a.show();
                } catch (RestriccioIntegritatException e) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Pla no obsolet");
                    a.setContentText("No es pot esborrar un plà no obsolet");
                    a.show();
                }
                break;
            case 2:
                try {
                    controladorDomini.esborrarAula(item);
                } catch (NotFoundException e) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Aula no trobada");
                    a.setContentText("Error intern");
                }
                break;
            case 3:
                try {
                    controladorDomini.esborrarAssignatura(item);
                } catch (NotFoundException e) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Assignatura no trobada");
                    a.setContentText("Error intern");
                }
                break;
            default:
                break;
        }
        reloadList();
    }

    /**
     * Accedeix a modificar un element X segons l'estat on es trobi la pantalla
     * @param item item concret a modificar, de tipus X
     */
    private void handleModify(String item) {
        switch (state){
            case 1:
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("State 1 Modify");
                a.show();
                //controladorDomini.esborrarPlaEstudis(item);
                break;
            case 2:
                //controladorDomini.esborrarAula(item);
                break;
            case 3:
                //controladorDomini.esborrarAssignatura(item);
                break;
            default:
                break;
        }
    }

    /**
     * Accedeix a afegir un element de tipus X segons l'estat on es trobi la pantalla
     */
    private void handleAdd() {
        switch (state){
            case 1:
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("State 1 Add");
                a.show();
                //controladorDomini.esborrarPlaEstudis(item);
                break;
            case 2:
                //controladorDomini.esborrarAula(item);
                break;
            case 3:
                //controladorDomini.esborrarAssignatura(item);
                break;
            default:
                break;
        }
    }

    @FXML
    /**
     * Mostra la pantalla de llista de plans d'estudi amb l'opció d'esborrar-ne, afegir-ne i modificar-ne
    */
    void mostraLlistaPlans() {
        state = 1;
        list_content.setVisible(true);
        specific_content.setVisible(false);
        welcome_content.setVisible(false);

        list_tab_title.setText("Llista de plans d'estudi");

        ObservableList<String> plans = FXCollections.observableArrayList(controladorDomini.getLlistaPlansEstudis());
        list_view.setItems(plans);
    }

    @FXML
    /**
     *Mostra la pantalla de llista d'aules amb l'opció d'esborrar-ne, afegir-ne i modificar-ne
     */
    void mostraLlistaAules() {
        state = 2;
        list_content.setVisible(true);
        specific_content.setVisible(false);
        welcome_content.setVisible(false);

        list_tab_title.setText("Llista d'aules");

        ObservableList<String> aules = FXCollections.observableArrayList(controladorDomini.getLlistaAules());
        list_view.setItems(aules);
    }

    @FXML
    /**
     * Mostra la pantalla de llista d'assignatures amb l'opció d'esborrar-ne, afegir-ne i modificar-ne
     */
    void mostraLlistaAssignatures() {
        state = 3;
        list_content.setVisible(true);
        specific_content.setVisible(false);
        welcome_content.setVisible(false);

        list_tab_title.setText("Llista d'assignatures");

        ObservableList<String> assignatures = FXCollections.observableArrayList(controladorDomini.getLlistaAssignatures());
        list_view.setItems(assignatures);
    }

    @FXML
    /**
     * Mostra la pantalla de gestió d'horaris, amb les opcions de carregar-ne un de disc, crear-ne un de nou o modificar-ne
     */
    void mostraHorari() {
        state = 0; //TODO might change later
    }

    @FXML
    /**
     * Mostra la pantalla inicial
     */
    void mostraInici() {
        state = 0;
        list_content.setVisible(false);
        specific_content.setVisible(false);
        welcome_content.setVisible(true);
    }

    /**
     * Actualitza l'informació de la llista mostrada per pantalla
     */
    void reloadList(){
        switch (state) {
            case 1: mostraLlistaPlans(); break;
            case 2: mostraLlistaAules(); break;
            case 3: mostraLlistaAssignatures(); break;
            default: break;
        }
    }

    //pantalla per crear una assignatura, tots els camps buits
    void crearAssignatura(){

    }

    //pantalla per modificar una assignatura, mostra tota la info anterior i accepta camps a modificar
    void modificarAssignatura(String nomAssignatura){

    }

    //pantalla per mostrar tota la informació d'una assignatura
    void consultarAssignatura(String nomAssignatura){

    }

    //pantalla per crear una aula, tots els camps buits
    void crearAula(){

    }

    //pantalla per modificar una aula, mostra tota la info anterior i accepta camps a modificar
    void modificarAula(String nomAula){

    }

    //pantalla per mostrar tota la informació d'una aula
    void consultarAula(String nomAula){

    }

    //pantalla per crear un pla d'estudis, tots els camps buits
    void crearPlaEstudis(){

    }

    //pantalla per modificar una assignatura, mostra tota la info anterior i accepta camps a modificar
    void modificarPlaEstudis(String nomPla){

    }

    //pantalla per mostrar tota la informació d'una assignatura
    void consultarPlaEstudis(String nomPla){

    }

    //TODO tot lo relatiu a horari, mostrar-los, editar-los, carregar de fitxer, etc
}
