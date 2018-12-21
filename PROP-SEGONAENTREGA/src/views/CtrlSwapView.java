package views;

import controllers.CtrlDomini;
import exceptions.NotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.util.HashMap;

public class CtrlSwapView {

    private CtrlHorariView horariView;

    // Slot 1
    @FXML
    Label label_dia_1 = new Label();
    @FXML
    Label label_hora_1 = new Label();
    @FXML
    Label label_assig_1 = new Label();
    @FXML
    Label label_grup_1 = new Label();
    @FXML
    Label label_aula_1 = new Label();

    // Slot 2
    @FXML
    Label label_dia_2 = new Label();
    @FXML
    Label label_hora_2 = new Label();
    @FXML
    Label label_assig_2 = new Label();
    @FXML
    Label label_grup_2 = new Label();
    @FXML
    Label label_aula_2 = new Label();

    private HashMap<String, String> slot_1_map;
    private HashMap<String, String> slot_2_map;

    /**
     * Init function
     */
    public void initialize() {

    }

    /**
     * Obté les dades del slot seleccionat i les fa servir com a Slot 1
     *
     * @throws NotFoundException si no s'ha trobat l'assignatura del slot
     */
    @FXML
    private void handleSelecciona1() throws NotFoundException {
        HashMap<String, String> slot = horariView.getCurrentSlot();
        if (slot != null) {
            slot_1_map = slot;
            populateLabels();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Aquest slot no conté dades valides");
        }
    }

    /**
     * Obté les dades del slot seleccionat i les fa servir com a Slot 2
     *
     * @throws NotFoundException si no s'ha trobat l'assignatura del slot
     */
    @FXML
    private void handleSelecciona2() throws NotFoundException {
        HashMap<String, String> slot = horariView.getCurrentSlot();
        if (slot != null) {
            slot_2_map = slot;
            populateLabels();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Aquest slot no conté dades valides");
        }
    }

    /**
     * Actualitza les Labels amb les dades noves dels slots
     */
    private void populateLabels() {
        if (slot_1_map != null) {
            label_dia_1.setText(slot_1_map.get("dia"));
            label_hora_1.setText(slot_1_map.get("hora"));
            label_assig_1.setText(slot_1_map.get("assignatura"));
            label_grup_1.setText(slot_1_map.get("grup"));
            label_aula_1.setText(slot_1_map.get("aula"));
        }
        if (slot_2_map != null) {
            label_dia_2.setText(slot_2_map.get("dia"));
            label_hora_2.setText(slot_2_map.get("hora"));
            label_assig_2.setText(slot_2_map.get("assignatura"));
            label_grup_2.setText(slot_2_map.get("grup"));
            label_aula_2.setText(slot_2_map.get("aula"));
        }
    }

    /**
     * Fa un reset de les variables i la interface
     */
    @FXML
    private void handleReset() {
        slot_1_map = null;
        slot_2_map = null;
        label_dia_1.setText("-");
        label_hora_1.setText("-");
        label_assig_1.setText("-");
        label_grup_1.setText("-");
        label_aula_1.setText("-");
        label_dia_2.setText("-");
        label_hora_2.setText("-");
        label_assig_2.setText("-");
        label_grup_2.setText("-");
        label_aula_2.setText("-");
    }

    /**
     * Propaga els canvis al Controlador de Domini
     */
    @FXML
    private void handleAccepta() {
        if (slot_2_map != null && slot_1_map != null) {
            boolean swapDone = CtrlDomini.getInstance().intercanviaSlots(slot_1_map, slot_2_map);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            if (swapDone) {
                a.setContentText("Intercanvi realitzat satisfactoriament");
                horariView.loadHorari(CtrlDomini.getInstance().getHorariJson());
                horariView.handleAssigAulaChange("", "");
            } else {
                a.setContentText("Intercanvi no realitzat. Comprova les restriccions.");
            }
            a.showAndWait();
            Stage stage = (Stage) label_assig_1.getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Guarda una instancia del Controlador de Vista de Horari
     * @param c CtrlHorariView
     */
    void setCtrlHorariView(CtrlHorariView c) {
        this.horariView = c;
    }

}