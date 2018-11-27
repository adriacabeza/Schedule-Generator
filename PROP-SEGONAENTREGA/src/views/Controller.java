package views;

import controllers.CtrlDomini;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Controller {

    @FXML
    Label tab_title = new Label();
    @FXML
    BorderPane content_tab = new BorderPane();
    @FXML
    BorderPane inner_content = new BorderPane();
    @FXML
    Button plus_button = new Button();

    CtrlDomini controladorDomini;

    public void initialize(){
        mostraInici();
        controladorDomini = CtrlDomini.getInstance();
    }

    @FXML
    void mostraLlistaPlans() {
        tab_title.setText("Llista de plans d'estudi");
        ListView<String> llistaPlans = new ListView<>();
        ObservableList<String> plans = FXCollections.observableArrayList("Pla1", "Pla2");
        llistaPlans.setItems(plans);
        inner_content.setCenter(llistaPlans);
    }

    @FXML
    void mostraLlistaAules() {
        tab_title.setText("Llista d'aules");
        inner_content.setCenter(null);
    }

    @FXML
    void mostraLlistaAssignatures() {
        tab_title.setText("Llista d'assignatures");
        inner_content.setCenter(null);
    }

    @FXML
    void mostraHorari() {

    }

    @FXML
    void mostraInici() {
        tab_title.setText("Inici");
        inner_content.setCenter(new Label("Benvinguts!"));
    }
}
