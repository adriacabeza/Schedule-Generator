package views;

import controllers.CtrlDomini;
import controllers.CtrlIO;
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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

    public void initialize(){
        mostraInici();
        controladorDomini = CtrlDomini.getInstance();
        int i = controladorDomini.carrega(); //TODO error check
        System.out.println(i);
    }

    @FXML
    void mostraLlistaPlans() {
        state = 1;
        list_content.setVisible(true);
        specific_content.setVisible(false);
        welcome_content.setVisible(false);

        list_tab_title.setText("Llista de plans d'estudi");

        ObservableList<String> plans = FXCollections.observableArrayList(controladorDomini.getLlistaPlansEstudis());
        list_view.setItems(plans);

        /*Button modify_button = new Button();
        modify_button.setText("Modificar");
        modify_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!llistaPlans.getSelectionModel().isEmpty()) {
                    // Obre la finestra de modificar
                }
            }
        });*/
    }

    @FXML
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
    void mostraHorari() {
        state = 0; //TODO might change later
    }

    @FXML
    void mostraInici() {
        state = 0;
        list_content.setVisible(false);
        specific_content.setVisible(false);
        welcome_content.setVisible(true);
    }

    @FXML
    void handlePlusButton(){
        switch (state){
            case 1:
                //afegeix un pla nou
                    //obrir una nova finestra per tal de que l'usuari introdueixi les dades necessaries i cridar al ctrlD etc.
                break;
            case 2:
                //afegeix aula
                    //same
                break;
            case 3:
                //afegeix assignatura
                    //same
                break;
            default:
                //should display an error
                break;
        }
    }

}
