package views;

import com.google.gson.Gson;
import controllers.CtrlDomini;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Slot;
import utils.FormValidation;

import java.util.ArrayList;
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
        slotname.prefWidthProperty().bind(horari.widthProperty().divide(8));
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
        horari_container.setCenter(horari);

        data = FXCollections.observableArrayList();
        horari.setItems(data);
    }

    public void loadHorari(String horarijson) {
        this.horarijson = horarijson;
    }

    public void handleAssigChange(String assig){
        data.setAll(generateSlotsFromJson(horarijson, assig, null));
    }

    public void handleAulaChange(String aula){
        data.setAll(generateSlotsFromJson(horarijson, null, aula));
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
     * @param horariJson L'horari en JSON
     * @param abbvr      L'abreviació del nom de la assignatura
     * @param aula       El nom de l'aula
     * @return Un Array de Slot
     */
    public ArrayList<Slot> generateSlotsFromJson(String horariJson, String abbvr, String aula) {
        boolean AssigMode;

        if (abbvr != null && !abbvr.equals("")) {
            AssigMode = true;
        } else if (aula != null && !aula.equals("")) {
            AssigMode = false;
        } else {
            throw new IllegalArgumentException("Abbvr o Aula han de ser not null");
        }
        List horari = new Gson().fromJson(horariJson, List.class);
        ArrayList<Slot> slots = new ArrayList<>();
        for (int i = 8; i < 21; i++) {
            slots.add(new Slot(i + ":00h"));
        }

        for (Object slot : horari) {
            Map m = (Map) slot;
            String abreviacio = (String) m.get("assignatura");
            String aulaslot = (String) m.get("aula");
            if (AssigMode) {
                if (abbvr.equalsIgnoreCase(abreviacio)) {
                    Double numslot = (Double) m.get("hora");
                    String dia = (String) m.get("dia");
                    Double grup = (Double) m.get("grup");
                    String value = Slot.formatSlotText(abreviacio, String.valueOf(grup.intValue()), aulaslot);
                    slots.get(numslot.intValue()).setDia(dia, value);
                }
            } else {
                if (aula.equalsIgnoreCase(aulaslot)) {
                    Double numslot = (Double) m.get("hora");
                    String dia = (String) m.get("dia");
                    Double grup = (Double) m.get("grup");
                    String value = Slot.formatSlotText(abreviacio, String.valueOf(grup.intValue()), aulaslot);
                    slots.get(numslot.intValue()).setDia(dia, value);
                }
            }
        }
        return slots;
    }
}