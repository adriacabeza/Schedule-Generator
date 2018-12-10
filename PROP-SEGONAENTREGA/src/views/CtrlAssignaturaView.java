package views;

import com.google.gson.Gson;
import controllers.CtrlDomini;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.FormValidation;

import java.util.List;
import java.util.Map;

public class CtrlAssignaturaView {

    /************* FORM ****************/
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
    @FXML
    CheckBox checkbox_teo = new CheckBox();
    @FXML
    CheckBox checkbox_lab = new CheckBox();
    @FXML
    TextField text_teo_ns = new TextField();
    @FXML
    TextField text_lab_ns = new TextField();
    @FXML
    TextField text_teo_ds = new TextField();
    @FXML
    TextField text_lab_ds = new TextField();
    @FXML
    ChoiceBox<String> choice_teo_ta = new ChoiceBox<>();
    @FXML
    ChoiceBox<String> choice_lab_ta = new ChoiceBox<>();
    @FXML
    ListView<String> list_correquisits = new ListView<>();
    @FXML
    Button button_elimina = new Button();
    @FXML
    ChoiceBox<String> choice_assig = new ChoiceBox<>();
    @FXML
    Button button_afegeix = new Button();

    /****** DISPLAY *******/

    /******** OTHER *******/
    private CtrlMainView ctrlMainView;
    private CtrlDomini ctrlDomini = CtrlDomini.getInstance();
    private boolean editmode = false;
    private ObservableList<String> candidates_correquisit;
    private ObservableList<String> llista_correquisits;

    /**
     * Init function
     */
    public void initialize() {
        ObservableList<String> plansEstudi = FXCollections.observableArrayList(ctrlDomini.getLlistaPlansEstudis());
        combobox_plaest.setItems(plansEstudi);

        llista_correquisits = FXCollections.observableArrayList();
        ObservableList<String> tAula = FXCollections.observableArrayList();
        tAula.add("normal");
        tAula.add("pcs");
        tAula.add("laboratori");
        choice_teo_ta.setItems(tAula);
        choice_lab_ta.setItems(tAula);
    }

    /**
     * Binding amb el controlador principal
     *
     * @param c main controller
     */
    public void setMainController(CtrlMainView c) {
        this.ctrlMainView = c;
    }


    /**
     * Carrega la informació complerta d'una assignatura per mostrar-la en la interficie i dona accés al mode d'edició dels paràmetres permesos
     *
     * @param nomAssignatura nom de l'assignatura que hem seleccionat per consultar
     */
    public void loadAssignatura(String nomAssignatura) {
        try {
            editmode = true;

            String json = ctrlDomini.consultarAssignatura(nomAssignatura);
            Map<String, Object> assignatura = new Gson().fromJson(json, Map.class);

            String plaest = ctrlDomini.getPlaEstudisContains(nomAssignatura);
            if (plaest != null && !plaest.isEmpty()) {
                combobox_plaest.setValue(plaest);
            }

            String nom = (String) assignatura.get("nom");
            if (nom != null && !nom.isEmpty()) {
                text_nom.setText(nom);
            } else {
                alert("No es pot mostrar una assignatura no identificada");
            }

            String abbvr = (String) assignatura.get("abr");
            if (abbvr != null && !abbvr.isEmpty()) {
                text_abbvr.setText(abbvr);
            }

            String descripcio = (String) assignatura.get("descripcio");
            if (descripcio != null && !descripcio.isEmpty()) {
                text_descripcio.setText(descripcio);
            }

            Double quadrimestre = (Double) assignatura.get("quadrimestre");
            if (quadrimestre != null) {
                text_quadri.setText(String.valueOf(quadrimestre.intValue()));
            }

            Map grups = (Map) assignatura.get("grups");
            int numgrups = grups.size();
            text_numgrups.setText(String.valueOf(numgrups));

            if (numgrups > 0) {
                Map firstgroup = (Map) grups.get(grups.keySet().iterator().next());
                Double capacitat = (Double) firstgroup.get("capacitat");
                text_capacitat.setText(String.valueOf(capacitat.intValue()));
                Map subgrups = (Map) firstgroup.get("subgrups");
                int numsubgrups = subgrups.size();
                text_numsubgrups.setText(String.valueOf(numsubgrups));
            }
            text_descripcio.setWrapText(true);

            Map teoria = (Map) assignatura.get("teoria");
            if (teoria != null) {
                Double numSessions = (Double) teoria.get("numSessions");
                text_teo_ns.setText(String.valueOf(numSessions.intValue()));
                Double duracioSessions = (Double) teoria.get("duracioSessions");
                text_teo_ds.setText(String.valueOf(duracioSessions.intValue()));
                String tipusAula = (String) teoria.get("tAula");
                choice_teo_ta.setValue(tipusAula);
            }

            Map laboratori = (Map) assignatura.get("laboratori");
            if (laboratori != null) {
                Double numSessions = (Double) laboratori.get("numSessions");
                text_lab_ns.setText(String.valueOf(numSessions.intValue()));
                Double duracioSessions = (Double) laboratori.get("duracioSessions");
                text_lab_ds.setText(String.valueOf(duracioSessions.intValue()));
                String tipusAula = (String) laboratori.get("tAula");
                choice_lab_ta.setValue(tipusAula);
            }

            List<String> correquisits = (List) assignatura.get("correquisit");
            System.out.println(correquisits);
            if (correquisits != null) {
                llista_correquisits.addAll(correquisits);
            }
            candidates_correquisit = FXCollections.observableArrayList(ctrlDomini.correquisitsPossibles(nomAssignatura));
            choice_assig.setItems(candidates_correquisit);
            list_correquisits.setItems(llista_correquisits);

        } catch (NotFoundException e) {
            alert("No existeix l'assignatura");
            exit();
        }
    }

    /**
     * Verifica els camps del formulari i notifica del nombre d'errors que conté
     *
     * @return nombre d'errors
     */
    private int verifyFields() {
        FormValidation formvalidator = new FormValidation();
        int errorcount = 0;

        if (!formvalidator.validateStringNoSpace(text_abbvr.getText())) {
            errorcount++;
            text_abbvr.setBorder(formvalidator.errorBorder);
        } else {
            text_abbvr.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateStringSpaceAllowEmpty(text_descripcio.getText())) {
            errorcount++;
            text_descripcio.setBorder(formvalidator.errorBorder);
        } else {
            text_descripcio.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateStringSpace(text_nom.getText())) {
            errorcount++;
            text_nom.setBorder(formvalidator.errorBorder);
        } else {
            text_nom.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateNumberAllowEmpty(text_quadri.getText())) {
            errorcount++;
            text_quadri.setBorder(formvalidator.errorBorder);
        } else {
            text_quadri.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateStringSpaceAllowEmpty(combobox_plaest.getValue())) {
            errorcount++;
            combobox_plaest.setBorder(formvalidator.errorBorder);
        } else {
            combobox_plaest.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateNumberAllowEmpty(text_numgrups.getText())) {
            errorcount++;
            text_numgrups.setBorder(formvalidator.errorBorder);
        } else {
            text_numgrups.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateNumberAllowEmpty(text_capacitat.getText())) {
            errorcount++;
            text_capacitat.setBorder(formvalidator.errorBorder);
        } else {
            text_capacitat.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateNumberAllowEmpty(text_numsubgrups.getText())) {
            errorcount++;
            text_numsubgrups.setBorder(formvalidator.errorBorder);
        } else {
            text_numsubgrups.setBorder(formvalidator.okBorder);
        }

        return errorcount;
    }

    /**
     * Guarda totes les modificacions fetes en una assignatura o en guarda una de nova
     */
    public void saveChanges() {
        int numerrors = verifyFields();
        if (numerrors > 0) {
            if (numerrors == 1) alert("Hi ha " + numerrors + " errors en el formulari.");
            else alert("Hi han " + numerrors + " errors en el formulari.");
            return;
        }

        String nomAbr = text_abbvr.getText();
        String descripcio = text_descripcio.getText();
        String nomAssig = text_nom.getText();
        String plaEstudis = combobox_plaest.getValue();
        int numgrups = Integer.parseInt(text_numgrups.getText());
        int capacitat = Integer.parseInt(text_capacitat.getText());
        int numsubgrups = Integer.parseInt(text_numsubgrups.getText());
        int quadrimestre = Integer.parseInt(text_quadri.getText());

        try {
            if (editmode) ctrlDomini.esborrarAssignatura(nomAssig);
            ctrlDomini.crearAssignatura(nomAssig, quadrimestre, descripcio, nomAbr);
            ctrlDomini.modificarGrups(nomAssig, numgrups, capacitat, numsubgrups);
            ctrlDomini.afegirAssignaturaPla(plaEstudis, nomAssig);

            //TODO not implemented yet
            //c.modificaInformacioTeoria(nomAssig, 0,0,null);
            //c.modificaInformacioLab(nomAssig, 0,0,null);
            //for all correquisits, afegirlos
            exit();

        } catch (RestriccioIntegritatException | NotFoundException e) {
            alert(e.getMessage());
        }
    }

    /**
     * Tanca la vista i propaga els canvis fets a la vista principal
     */
    public void exit() {
        ctrlMainView.reloadList();
        Stage stage = (Stage) cancelbutton.getScene().getWindow();
        stage.close();
    }

    /**
     * Mostra un pop-up amb un missatge d'error si s'en dona un
     *
     * @param s missatge d'error a mostrar
     */
    public void alert(String s) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(s);
        a.setHeaderText("Hi ha hagut un error");
        a.show();
    }

    /**
     * Bloqueja l'edició dels paràmetres no modificables en assignatures ja creades
     */
    public void disableEditFields() {
        text_nom.setDisable(true);
        combobox_plaest.setDisable(true);
    }
}
