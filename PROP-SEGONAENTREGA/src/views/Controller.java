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

    public void initialize(){
        mostraInici();
        controladorDomini = CtrlDomini.getInstance();
        int i = controladorDomini.carrega(); //TODO error check
        System.out.println(i);

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

    private void handleDelete(String item) {
        switch (state){
            case 1:
                try {
                    controladorDomini.esborrarPlaEstudis(item);
                    reloadList();
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
                //controladorDomini.esborrarAula(item);
                break;
            case 3:
                //controladorDomini.esborrarAssignatura(item);
                break;
            default:
                break;
        }
    }

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

    void reloadList(){
        switch (state) {
            case 1: mostraLlistaPlans(); break;
            case 2: mostraLlistaAules(); break;
            case 3: mostraLlistaAssignatures(); break;
            default: break;
        }
    }
}
