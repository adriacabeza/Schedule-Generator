package views;

import com.google.gson.Gson;
import controllers.CtrlDomini;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Slot;
import utils.FormValidation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CtrlHorariView {

    private CtrlMainView ctrlMainView;
    private CtrlDomini ctrlDomini = CtrlDomini.getInstance();
    private boolean editmode = false;

    @FXML
    ChoiceBox choice_aula = new ChoiceBox();
    @FXML
    ChoiceBox choice_assig = new ChoiceBox();
    @FXML
    VBox restriccions_container = new VBox();
    @FXML
    Button cancel_button = new Button();
    @FXML
    Button generate_button = new Button();
    @FXML
    BorderPane horari_container = new BorderPane();

    TableView<Slot> horari;
    String horarijson;
    ObservableList<Slot> data;

    /**
     * Init function
     */
    public void initialize() {
        // Table creation
        horari = new TableView<>();
        horari.getSelectionModel().setCellSelectionEnabled(true);
        // Column creation
        TableColumn slotname = new TableColumn("Hora");
        slotname.prefWidthProperty().bind(horari.widthProperty().divide(7.8));
        slotname.setCellValueFactory(new PropertyValueFactory<Slot, String>("slotname"));
        TableColumn monday = new TableColumn("Dilluns");
        monday.prefWidthProperty().bind(horari.widthProperty().divide(5.8));
        monday.setCellValueFactory(new PropertyValueFactory<Slot, String>("dilluns"));
        TableColumn tuesday = new TableColumn("Dimarts");
        tuesday.prefWidthProperty().bind(horari.widthProperty().divide(5.8));
        tuesday.setCellValueFactory(new PropertyValueFactory<Slot, String>("dimarts"));
        TableColumn wednesday = new TableColumn("Dimecres");
        wednesday.prefWidthProperty().bind(horari.widthProperty().divide(5.8));
        wednesday.setCellValueFactory(new PropertyValueFactory<Slot, String>("dimecres"));
        TableColumn thursday = new TableColumn("Dijous");
        thursday.prefWidthProperty().bind(horari.widthProperty().divide(5.8));
        thursday.setCellValueFactory(new PropertyValueFactory<Slot, String>("dijous"));
        TableColumn friday = new TableColumn("Divendres");
        friday.prefWidthProperty().bind(horari.widthProperty().divide(5.8));
        friday.setCellValueFactory(new PropertyValueFactory<Slot, String>("divendres"));

        horari.getColumns().addAll(slotname, monday, tuesday, wednesday, thursday, friday);

        horari_container.setCenter(new Label("Escull una Assignatura o una Aula"));

        data = FXCollections.observableArrayList();
        horari.setItems(data);

        ArrayList<String> assignaturesArray = new ArrayList<>();
        assignaturesArray.add("");
        assignaturesArray.addAll(ctrlDomini.getLlistaAssignatures());
        ObservableList<String> assigs = FXCollections.observableArrayList(assignaturesArray);

        ArrayList<String> aulesArray = new ArrayList<>();
        aulesArray.add("");
        aulesArray.addAll(ctrlDomini.getLlistaAules());
        ObservableList<String> aules = FXCollections.observableArrayList(aulesArray);

        choice_aula.setItems(aules);
        choice_assig.setItems(assigs);

        choice_aula.getSelectionModel().select(0);
        choice_assig.getSelectionModel().select(0);

        choice_aula.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                try {
                    String aula = aules.get(t1.intValue());
                    String assignaturachoice = (String) choice_assig.getSelectionModel().getSelectedItem();
                    String assignatura;
                    if (assignaturachoice.equalsIgnoreCase("")) {
                        assignatura = assignaturachoice;
                    } else {
                        assignatura = ctrlDomini.obtenirAbreviacioAssig(assignaturachoice);
                    }
                    handleAssigAulaChange(assignatura, aula);
                } catch (NotFoundException e) {
                    alert(e.getMessage());
                }
            }
        });
        choice_assig.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                try {
                    String choice = assigs.get(t1.intValue());
                    String assignatura;
                    if (choice.equalsIgnoreCase("")) {
                        assignatura = choice;
                    } else {
                        assignatura = ctrlDomini.obtenirAbreviacioAssig(choice);
                    }
                    String aula = (String) choice_aula.getSelectionModel().getSelectedItem();
                    handleAssigAulaChange(assignatura, aula);
                } catch (NotFoundException e) {
                    alert(e.getMessage());
                }
            }
        });
    }

    public void loadHorari(String horarijson) {
        this.horarijson = horarijson;
    }

    private void handleAssigAulaChange(String assig, String aula) {
        horari_container.setCenter(horari);
        data.setAll(generateSlotsFromJson(assig, aula));
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
    public void alert(String s) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(s);
        a.setHeaderText("Hi ha hagut un error");
        a.show();
    }

    /**
     * Retorna un array de Slot a partir de un Json que conté Horaris
     *
     * @param abbvr      L'abreviació del nom de la assignatura
     * @param aula       El nom de l'aula
     * @return Un Array de Slot
     */

    public ArrayList<Slot> generateSlotsFromJson(String abbvr, String aula) {
        /*
          Mode can be:
          0: Aula nomes
          1: Assignatura nomes
          2: Assignatura i aula
         */
        int mode;

        // Detection of setup
        if (abbvr != null && aula != null) {
            if (abbvr.equalsIgnoreCase("") && !aula.equalsIgnoreCase("")) {
                mode = 0;
            } else if (!abbvr.equalsIgnoreCase("") && aula.equalsIgnoreCase("")) {
                mode = 1;
            } else {
                mode = 2;
            }
        } else {
            throw new IllegalArgumentException("Abreviació o aula es null");
        }
        // Obté l'horari a partir del json
        List horari = new Gson().fromJson(horarijson, List.class);
        // Crea 12 Slots
        ArrayList<Slot> slots = new ArrayList<>();
        for (int i = 8; i < 21; i++) {
            slots.add(new Slot(i + ":00h"));
        }


        // Conversion start
        for (Object slot : horari) {
            Map m = (Map) slot;
            String abreviacio = (String) m.get("assignatura");
            String aulaslot = (String) m.get("aula");
            // Aula només
            if (mode == 0) {
                if (aula.equalsIgnoreCase(aulaslot)) {
                    Double numslot = (Double) m.get("hora");
                    String dia = (String) m.get("dia");
                    Double grup = (Double) m.get("grup");
                    String value = Slot.formatSimpleSlotText(abreviacio, String.valueOf(grup.intValue()), aulaslot);
                    slots.get(numslot.intValue()).setDia(dia, value);
                }
            }
            // Assignatura només
            if (mode == 1) {

                if (abbvr.equalsIgnoreCase(abreviacio)) {
                    Double numslot = (Double) m.get("hora");
                    String dia = (String) m.get("dia");
                    Double grup = (Double) m.get("grup");
                    String value = Slot.formatSimpleSlotText(abreviacio, String.valueOf(grup.intValue()), aulaslot);
                    slots.get(numslot.intValue()).setDia(dia, value);
                }
            }
            // Aula i assignatura
            if (mode == 2) {
                if (abbvr.equalsIgnoreCase(abreviacio) && aula.equalsIgnoreCase(aulaslot)) {
                    Double numslot = (Double) m.get("hora");
                    String dia = (String) m.get("dia");
                    Double grup = (Double) m.get("grup");
                    String value = Slot.formatSimpleSlotText(abreviacio, String.valueOf(grup.intValue()), aulaslot);
                    slots.get(numslot.intValue()).setDia(dia, value);
                }
            }
        }
        return slots;
    }
}