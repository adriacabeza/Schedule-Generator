package views;

import com.google.gson.Gson;
import controllers.CtrlDomini;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.FormValidation;

import java.util.Map;

public class CtrlPlaEstudisView {
    @FXML
    TextField text_titulacio = new TextField();
    @FXML
    TextField text_any = new TextField();
    @FXML
    TextArea text_descripcio = new TextArea();
    @FXML
    CheckBox checkbox_obsolet = new CheckBox();

    @FXML
    Button cancel_button = new Button();
    @FXML
    Button save_button = new Button();

    /***** OTHER CONTROLLER VARIABLES *****/
    private CtrlMainView ctrlMainView;
    private CtrlDomini ctrlDomini = CtrlDomini.getInstance();
    private boolean editmode = false;

    /**
     * Init function
     */
    public void initialize() {
        //carregar assignatures etc
    }


    /**
     * Carrega la informació complerta d'un pla d'estudis per mostrar-lo en la interficie i dona accés al mode d'edició dels paràmetres permesos
     *
     * @param plaEst nom del pla d'estudis que hem seleccionat per consultar
     */
    public void loadPlaEstudis(String plaEst) {
        editmode = true;
        String json = null;
        try {
            json = ctrlDomini.consultarPlaEsudis(plaEst);
            Map<String, Object> plaEstudis = new Gson().fromJson(json, Map.class);

            String nom = (String) plaEstudis.get("nomTitulacio");
            if (nom != null && !nom.isEmpty()) {
                text_titulacio.setText(nom);
            } else {
                alert("No es pot mostrar un pla d'estudis no identificat");
            }

            Double any = (Double) plaEstudis.get("any");
            if (any != null) {
                text_any.setText(String.valueOf(any.intValue()));
            }

            String descripcio = (String) plaEstudis.get("descripcio");
            if (descripcio != null && !descripcio.isEmpty()) {
                text_descripcio.setText(descripcio);
            }

            boolean obsolet = (boolean) plaEstudis.get("obsolet");
            checkbox_obsolet.setSelected(obsolet);


        } catch (NotFoundException e) {
            alert(e.getMessage());
            exit();
        }
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
     * Guarda totes les modificacions fetes en un pla d'estudis o en guarda un de nou
     */
    public void saveChanges() {
        int numerrors = verifyFields();
        if (numerrors > 0) {
            if (numerrors == 1) alert("Hi ha " + numerrors + " errors en el formulari.");
            else alert("Hi han " + numerrors + " errors en el formulari.");
            return;
        }

        String titulacio = text_titulacio.getText();
        String descripcio = text_descripcio.getText();
        int any = Integer.parseInt(text_any.getText());
        boolean obsolet = checkbox_obsolet.isSelected();

        try {
            if (editmode) {
                ctrlDomini.setObsolet(titulacio, true);
                ctrlDomini.esborrarPlaEstudis(titulacio);
                ctrlDomini.crearPlaEstudis(titulacio, any, descripcio);
                ctrlDomini.setObsolet(titulacio, obsolet);
            } else ctrlDomini.crearPlaEstudis(titulacio, any, descripcio);
            exit();
        } catch (NotFoundException | RestriccioIntegritatException e) {
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
     * Deshabilita els camps restringits en la creació d'un pla d'estudis
     */
    public void disableCreateFields() {
        checkbox_obsolet.setSelected(false);
        checkbox_obsolet.setDisable(true);
    }

    /**
     * Bloqueja l'edició dels paràmetres no modificables en plans d'estudi ja creats
     */
    public void disableEditFields() {
        text_titulacio.setDisable(true);
    }

    /**
     * Verifica els camps del formulari i notifica del nombre d'errors que conté
     *
     * @return nombre d'errors
     */
    private int verifyFields() {
        FormValidation formvalidator = new FormValidation();
        int errorcount = 0;

        if (!formvalidator.validateStringSpace(text_titulacio.getText())) {
            errorcount++;
            text_titulacio.setBorder(formvalidator.errorBorder);
        } else {
            text_titulacio.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateStringSpaceAllowEmpty(text_descripcio.getText())) {
            errorcount++;
            text_descripcio.setBorder(formvalidator.errorBorder);
        } else {
            text_descripcio.setBorder(formvalidator.okBorder);
        }

        if (!formvalidator.validateNumber(text_any.getText())) {
            errorcount++;
            text_any.setBorder(formvalidator.errorBorder);
        } else {
            text_any.setBorder(formvalidator.okBorder);
        }
        return errorcount;
    }
}
