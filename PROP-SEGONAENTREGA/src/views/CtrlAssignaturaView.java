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
    private CtrlDomini ctrlDomini = CtrlDomini.getInstance();
    private boolean editmode = false;

    public void initialize(){
        ObservableList<String> plansEstudi = FXCollections.observableArrayList(CtrlDomini.getInstance().getLlistaPlansEstudis());
        combobox_plaest.setItems(plansEstudi);
    }

    public void setMainController(CtrlMainView c) {
        this.ctrlMainView = c;
    }

    //TODO FICS dis
    public void loadAssignatura(String nomAssignatura) {
        try {
            editmode = true;

            String json = ctrlDomini.consultarAssignatura(nomAssignatura);
            Map<String, Object> assignatura = new Gson().fromJson(json, Map.class);

            combobox_plaest.setValue(ctrlDomini.getPlaEstudisContains(nomAssignatura));
            text_nom.setText((String) assignatura.get("nom"));
            text_abbvr.setText((String) assignatura.get("abr"));
            text_descripcio.setText((String) assignatura.get("descripcio"));
            text_quadri.setText(String.valueOf((double)assignatura.get("quadrimestre")));

            //TODO ni ho comento perque no ho vull ni entendre, into the garbage
            //TODO god has abandoned us
            //TODO still easier than installing java 11
            //TODO LORD, FORGIVE ME FOR I HAVE SINNED
            //TODO almenos tenemos salud
            //TODO el TDD rula better than this
            Map m = (Map) assignatura.get("grups");
            text_numgrups.setText(String.valueOf(m.size()));
            String key = (String) m.keySet().iterator().next();
            Map grup = (Map) m.get(key);
            text_capacitat.setText(String.valueOf(((Double) grup.get("capacitat")).intValue())); //max cerdaco btw
            Map m2 = (Map) grup.get("subgrups");
            text_numsubgrups.setText((String.valueOf(m2.size())));
            //TODO end of sida

        } catch (NotFoundException e) {
            alert("This should never show :)");
        }
    }

    public void saveChanges(){
        //USE INPUTVERIFIER CLASS

        String nomAbr = text_abbvr.getText(); //TODO posar al domini
        String descripcio = text_descripcio.getText();
        String nomAssig = text_nom.getText(); //TODO check not null
        int quadrimestre = Integer.parseInt(text_quadri.getText()); //TODO check it contains a number
        String plaEstudis = combobox_plaest.getValue();
        int numgrups = Integer.parseInt(text_numgrups.getText());
        int capacitat = Integer.parseInt(text_capacitat.getText());
        int numsubgrups = Integer.parseInt(text_numsubgrups.getText());

        try { //TODO verify all inputs before inserting
            CtrlDomini c = CtrlDomini.getInstance();
            if (editmode) c.esborrarAssignatura(nomAssig);
            c.crearAssignatura(nomAssig, quadrimestre, descripcio, nomAbr);
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
