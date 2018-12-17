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
import utils.FormValidation;

import java.io.IOException;
import java.util.Map;

public class CtrlAulaView {

    /***** AULA EDIT/CREATE *****/
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

    /***** AULA DISPLAY *****/
    @FXML
    Label label_title = new Label();
    @FXML
    Label label_edifici = new Label();
    @FXML
    Label label_planta = new Label();
    @FXML
    Label label_aula = new Label();
    @FXML
    Label label_capacitat = new Label();
    @FXML
    Label label_tipus_aula = new Label();

    /***** OTHER CONTROLLER VARIABLES *****/
    private CtrlMainView ctrlMainView;
    private CtrlDomini ctrlDomini = CtrlDomini.getInstance();
    private boolean editmode = false;
    String key = "";

    public void initialize() {
        ObservableList<String> tAula = FXCollections.observableArrayList();
        tAula.add("normal");
        tAula.add("pcs");
        tAula.add("laboratori");
        choice_tipus_aula.setItems(tAula);
    }

    /**
     * Carrega la informació complerta d'una aula per mostrar-la en la interficie
     *
     * @param key nom de l'aula que hem seleccionat per consultar
     */
    public void displayAula(String key) {                                                       //TODO check if this might have errors
        this.key = key;
        String json = null;
        try {
            json = ctrlDomini.consultarAula(key);
            Map<String, Object> aula = new Gson().fromJson(json, Map.class);
            label_title.setText(key.toUpperCase());
            label_edifici.setText((String) aula.get("edifici"));
            label_planta.setText(String.valueOf(((Double) aula.get("planta")).intValue()));
            label_aula.setText(String.valueOf(((Double) aula.get("aula")).intValue()));
            label_capacitat.setText(String.valueOf(((Double) aula.get("capacitat")).intValue()));
            label_tipus_aula.setText((String) aula.get("tAula"));
        } catch (NotFoundException e) {
            alert(e.getMessage());
            exit();
        }
    }

    /**
     * Carrega la informació complerta d'una aula per mostrar-la en la interficie i dona accés al mode d'edició dels paràmetres permesos
     *
     * @param key nom de l'aula que hem seleccionat per consultar
     */
    public void loadAula(String key) {
        editmode = true;
        this.key = key;
        String json = null;
        try {
            json = ctrlDomini.consultarAula(key);
            Map<String, Object> aula = new Gson().fromJson(json, Map.class);
            text_edifici.setText((String) aula.get("edifici"));
            text_planta.setText(String.valueOf(((Double) aula.get("planta")).intValue()));
            text_aula.setText(String.valueOf(((Double) aula.get("aula")).intValue()));
            text_capacitat.setText(String.valueOf(((Double) aula.get("capacitat")).intValue()));
            choice_tipus_aula.setValue((String) aula.get("tAula"));
        } catch (NotFoundException e) {
            alert(e.getMessage());
            exit();
        }
    }

    /**
     * Binding amb el controlador principal
     *
     * @param c
     */
    public void setMainController(CtrlMainView c) {
        this.ctrlMainView = c;
    }

    /**
     * Guarda totes les modificacions fetes en una aula o en guarda una de nova
     */
    public void saveChanges() {
        int numerrors = verifyFields();
        if (numerrors > 0) {
            if (numerrors == 1) alert("Hi ha " + numerrors + " errors en el formulari.");
            else alert("Hi han " + numerrors + " errors en el formulari.");
            return;
        }

        String edifici = text_edifici.getText();
        int planta = Integer.parseInt(text_planta.getText());
        int aula = Integer.parseInt(text_aula.getText());
        int capacitat = Integer.parseInt(text_capacitat.getText());
        String tipusAula = choice_tipus_aula.getValue();

        try {
            if (editmode) ctrlDomini.modificarAula(key, capacitat, tipusAula);
            else ctrlDomini.creaAula(edifici, planta, aula, capacitat, tipusAula);
            exit();
        } catch (NotFoundException | IOException | RestriccioIntegritatException e) {
            alert(e.getMessage());
        }
    }

    /**
     * Tanca la vista i propaga els canvis fets a la vista principal
     */
    public void exit() {
        ctrlMainView.reloadList();
        Stage stage = (Stage) cancel_button.getScene().getWindow();
        stage.close();
    }

    /**
     * Mostra un pop-up amb un missatge d'error si s'en dona un
     *
     * @param s missatge d'error a mostrar
     */
    private void alert(String s) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(s);
        a.setHeaderText("Hi ha hagut un error");
        a.show();
    }

    /**
     * Bloqueja l'edició dels paràmetres no modificables en aules ja creades
     */
    public void disableEditFields() {
        text_edifici.setDisable(true);
        text_planta.setDisable(true);
        text_aula.setDisable(true);
    }

    /**
     * Verifica els camps del formulari i notifica del nombre d'errors que conté
     *
     * @return nombre d'errors
     */
    private int verifyFields() {
        FormValidation formvalidator = new FormValidation();
        int errorcount = 0;

        if (!formvalidator.validateAlphanumeric(text_edifici.getText())) {
            errorcount++;
            text_edifici.setBorder(formvalidator.errorBorder);
        } else {
            text_edifici.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateNumber(text_planta.getText())) {
            errorcount++;
            text_planta.setBorder(formvalidator.errorBorder);
        } else {
            text_planta.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateNumber(text_aula.getText())) {
            errorcount++;
            text_aula.setBorder(formvalidator.errorBorder);
        } else {
            text_aula.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateNumber(text_capacitat.getText())) {
            errorcount++;
            text_capacitat.setBorder(formvalidator.errorBorder);
        } else {
            text_capacitat.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateStringNoSpace(choice_tipus_aula.getValue())) {
            errorcount++;
            choice_tipus_aula.setBorder(formvalidator.errorBorder);
        } else {
            choice_tipus_aula.setBorder(formvalidator.okBorder);
        }
        return errorcount;
    }
}

