package views;

import com.google.gson.Gson;
import controllers.CtrlDomini;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;

public class CtrlAssignaturaView {

    @FXML
    Button savebutton = new Button();
    @FXML
    Button cancelbutton = new Button();
    @FXML
    TextField text_nom = new TextField();
    @FXML
    TextField text_abbvr = new TextField();
    @FXML
    TextField text_quadri = new TextField();
    @FXML
    ChoiceBox<String> combobox_plaest = new ChoiceBox<>();
    @FXML
    TextArea text_descripcio = new TextArea();
    @FXML
    TextField text_numgrups = new TextField();
    @FXML
    TextField text_capacitat = new TextField();
    @FXML
    TextField text_numsubgrups = new TextField();


    private CtrlMainView ctrlMainView;

    public void initialize(){
        ObservableList<String> plansEstudi = FXCollections.observableArrayList(CtrlDomini.getInstance().getLlistaPlansEstudis());
        combobox_plaest.setItems(plansEstudi);
    }

    public void setMainController(CtrlMainView c) {
        this.ctrlMainView = c;
    }

    //TODO finish dis
    public void loadAssignatura(String nomAssignatura) {
        try {
            String json = CtrlDomini.getInstance().consultarAssignatura(nomAssignatura);

            Map<String, Object> assignatura = new Gson().fromJson(json, Map.class);

            text_nom.setText((String) assignatura.get("nom"));

            Map m = (Map) assignatura.get("grups");
            text_numgrups.setText(String.valueOf(m.size()));

        } catch (NotFoundException e) {
            alert("This should never show :)");
        }
    }

    public void saveChanges(){
        //USE INPUTVERIFIER CLASS

        //String nomAbr = text_abbvr.getText(); //TODO posar al domini
        //String descripcio = text_descripcio.getText();

        String nomAssig = text_nom.getText(); //TODO check not null
        int quadrimestre = Integer.parseInt(text_quadri.getText()); //TODO check it contains a number
        String plaEstudis = combobox_plaest.getValue();
        int numgrups = Integer.parseInt(text_numgrups.getText());
        int capacitat = Integer.parseInt(text_capacitat.getText());
        int numsubgrups = Integer.parseInt(text_numsubgrups.getText());

        try { //TODO verify all inputs before inserting
            CtrlDomini c = CtrlDomini.getInstance();
            c.crearAssignatura(nomAssig, quadrimestre);
            c.modificarGrups(nomAssig, numgrups, capacitat, numsubgrups);
            c.afegirAssignaturaPla(plaEstudis, nomAssig);

            //TODO not implemented yet
            //c.modificaInformacioTeoria(nomAssig, 0,0,null);
            //c.modificaInformacioLab(nomAssig, 0,0,null);
            //for all correquisits, afegirlos
            exit();

        } catch (RestriccioIntegritatException | NotFoundException e) {
            alert(e.getMessage());
        }
    }

    public void exit(){
        ctrlMainView.reloadList();
        Stage stage = (Stage) cancelbutton.getScene().getWindow();
        stage.close();
    }

    public void alert(String s) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(s);
        a.setHeaderText("HUUURRRRRRRRRR");
        a.show();
    }

    public void disableEditFields() {
        text_nom.setDisable(true);
        combobox_plaest.setDisable(true);
    }
}
