package views;

import controllers.CtrlDomini;
import exceptions.NotFoundException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class CtrlSwapView {

    CtrlHorariView horariView;

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

    HashMap<String, String> slot_1_map;
    HashMap<String, String> slot_2_map;

    /**
     * Init function
     */
    public void initialize() {

    }

    @FXML
    private void handleSelecciona1() throws NotFoundException {
        /*Alert prompt = new Alert(Alert.AlertType.CONFIRMATION);
        prompt.setContentText("Selecciona un slot a l'horari i prem \"OK\"");
        Optional<ButtonType> result = prompt.showAndWait();
        if (result.get() == ButtonType.OK) {*/
        HashMap<String, String> slot = horariView.getCurrentSlot();
        if (slot != null) {
            slot_1_map = slot;
            populateLabels();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Aquest slot no conté dades valides");
        }
        //}
    }

    @FXML
    private void handleSelecciona2() throws NotFoundException {
        /*Alert prompt = new Alert(Alert.AlertType.CONFIRMATION);
        prompt.setContentText("Selecciona un slot a l'horari i prem \"OK\"");
        Optional<ButtonType> result = prompt.showAndWait();
        if (result.get() == ButtonType.OK) {*/
        HashMap<String, String> slot = horariView.getCurrentSlot();
        if (slot != null) {
            slot_2_map = slot;
            populateLabels();
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("Aquest slot no conté dades valides");
        }

        //}
    }

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

    @FXML
    private void handleAccepta() {
        //TODO check nulls
        boolean swapDone = CtrlDomini.getInstance().intercanviaSlots(slot_1_map, slot_2_map);
        //TODO handle results
    }

    public void setCtrlHorariView(CtrlHorariView c) {
        this.horariView = c;
    }

}