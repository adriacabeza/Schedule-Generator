package views;

import com.google.gson.Gson;
import controllers.CtrlDomini;
import exceptions.NotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Map;

public class CtrlAulaView {
    @FXML
    Button save_button = new Button();
    @FXML
    Button cancel_button = new Button();
    @FXML
    TextField text_edifici = new TextField();
    @FXML
    TextField text_planta = new TextField();
    @FXML
    TextField text_aula = new TextField();
    @FXML
    TextField text_capacitat = new TextField();
    @FXML
    ChoiceBox<String> choice_tipus_aula = new ChoiceBox<>();

    private CtrlMainView ctrlMainView;
    private CtrlDomini ctrlDomini = CtrlDomini.getInstance();
    private boolean editmode = false;

    public void initialize(){

    }

    public void loadAula(String key) {
        /*editmode = true;
        String json = null;
        try {
            //json = ctrlDomini.consultarAula(key);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> assignatura = new Gson().fromJson(json, Map.class);
*/

    }

    public void setMainController(CtrlMainView c) {
        this.ctrlMainView = c;
    }

    public void saveChanges(){
    }

    public void exit(){

    }

    public void alert(String s) {

    }

    public void disableEditFields() {

    }
}

