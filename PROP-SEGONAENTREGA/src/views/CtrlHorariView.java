package views;

import com.google.gson.Gson;
import controllers.CtrlDomini;
import exceptions.NotFoundException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Slot;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CtrlHorariView {

    private CtrlMainView ctrlMainView;
    private CtrlDomini ctrlDomini = CtrlDomini.getInstance();
    private boolean generatemode = false;
    private Stage swapWindow;

    @FXML
    ChoiceBox<String> choice_aula = new ChoiceBox<>();
    @FXML
    ChoiceBox<String> choice_assig = new ChoiceBox<>();
    @FXML
    VBox restriccions_container = new VBox();
    @FXML
    Button cancel_button = new Button();
    @FXML
    Button generate_button = new Button();
    @FXML
    BorderPane horari_container = new BorderPane();

    @FXML
    ChoiceBox<String> rmt_assig = new ChoiceBox<>();
    @FXML
    ChoiceBox<String> rmt_matitarda = new ChoiceBox<>();

    @FXML
    ChoiceBox<String> rdah_dia = new ChoiceBox<>();
    @FXML
    ChoiceBox<String> rdah_hora = new ChoiceBox<>();
    @FXML
    ChoiceBox<String> rdah_aula = new ChoiceBox<>();

    @FXML
    ChoiceBox<String> rad_dia = new ChoiceBox<>();
    @FXML
    ChoiceBox<String> rad_aula = new ChoiceBox<>();

    @FXML
    CheckBox rc_checkbox = new CheckBox();
    @FXML
    CheckBox rgt_checkbox = new CheckBox();

    private TableView<Slot> horariTable;
    private String horarijson;
    private ObservableList<Slot> dataTable;
    private ObservableList<String> llistaAules;
    private ObservableList<String> llistaAssignatures;
    private ObservableList<String> llistaDies;
    private ObservableList<String> llistaSlots;
    private ObservableList<String> llistaMatiTarda;

    private ArrayList<HashMap<String, String>> rmt;
    private ArrayList<HashMap<String, String>> rdah;
    private ArrayList<HashMap<String, String>> rad;


    /**
     * Init function
     */
    public void initialize() {

        // Table creation
        horariTable = new TableView<>();
        horariTable.getSelectionModel().setCellSelectionEnabled(true);

        // Column creation
        TableColumn slotname = new TableColumn("Hora");
        slotname.prefWidthProperty().bind(horariTable.widthProperty().divide(7.15));
        slotname.setCellValueFactory(new PropertyValueFactory<Slot, String>("slotname"));
        TableColumn monday = new TableColumn("Dilluns");
        monday.prefWidthProperty().bind(horariTable.widthProperty().divide(6));
        monday.setCellValueFactory(new PropertyValueFactory<Slot, String>("dilluns"));
        TableColumn tuesday = new TableColumn("Dimarts");
        tuesday.prefWidthProperty().bind(horariTable.widthProperty().divide(6));
        tuesday.setCellValueFactory(new PropertyValueFactory<Slot, String>("dimarts"));
        TableColumn wednesday = new TableColumn("Dimecres");
        wednesday.prefWidthProperty().bind(horariTable.widthProperty().divide(6));
        wednesday.setCellValueFactory(new PropertyValueFactory<Slot, String>("dimecres"));
        TableColumn thursday = new TableColumn("Dijous");
        thursday.prefWidthProperty().bind(horariTable.widthProperty().divide(6));
        thursday.setCellValueFactory(new PropertyValueFactory<Slot, String>("dijous"));
        TableColumn friday = new TableColumn("Divendres");
        friday.prefWidthProperty().bind(horariTable.widthProperty().divide(6));
        friday.setCellValueFactory(new PropertyValueFactory<Slot, String>("divendres"));

        // Add columns to the table
        horariTable.getColumns().addAll(slotname, monday, tuesday, wednesday, thursday, friday);

        // Set starting state
        horari_container.setCenter(new Label("Escull una Assignatura o una Aula"));

        // Initialize observableArrayList and set as table source
        dataTable = FXCollections.observableArrayList();
        horariTable.setItems(dataTable);

        // Load Assignatures from CtrlDomini to populate choicebox
        ArrayList<String> assignaturesArray = new ArrayList<>();
        assignaturesArray.add(""); // Add empty value
        assignaturesArray.addAll(ctrlDomini.getLlistaAssignatures());
        llistaAssignatures = FXCollections.observableArrayList(assignaturesArray);

        // Load Aules from CtrlDomini to populate choicebox
        ArrayList<String> aulesArray = new ArrayList<>();
        aulesArray.add(""); // Add empty value
        aulesArray.addAll(ctrlDomini.getLlistaAules());
        llistaAules = FXCollections.observableArrayList(aulesArray);

        // Populate ChoiceBoxes
        choice_aula.setItems(llistaAules);
        choice_assig.setItems(llistaAssignatures);

        // Preselect empty choice
        choice_aula.getSelectionModel().select(0);
        choice_assig.getSelectionModel().select(0);

        // Add onChangeListeners for ChoiceBoxes
        choice_aula.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                try {
                    String aula = llistaAules.get(t1.intValue());
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
                    String choice = llistaAssignatures.get(t1.intValue());
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

    void setGenerateMode() {
        generatemode = true;
        swapWindow = new Stage();
        choice_assig.setDisable(true);
        choice_aula.setDisable(true);
        // Set starting state
        horari_container.setCenter(new Label("Escull les restriccions adients i genera un horari"));

        ArrayList<String> matitarda = new ArrayList<>(Arrays.asList("", "Mati", "Tarda"));
        llistaMatiTarda = FXCollections.observableArrayList(matitarda);
        rmt_assig.setItems(llistaAssignatures);
        rmt_assig.getSelectionModel().select(0);
        rmt_matitarda.setItems(llistaMatiTarda);
        rmt_matitarda.getSelectionModel().select(0);

        ArrayList<String> dies = new ArrayList<>(Arrays.asList("", "Dilluns", "Dimarts", "Dimecres", "Dijous", "Divendres"));
        llistaDies = FXCollections.observableArrayList(dies);
        rdah_aula.setItems(llistaAules);
        rdah_aula.getSelectionModel().select(0);
        rdah_dia.setItems(llistaDies);
        rdah_dia.getSelectionModel().select(0);
        ArrayList<String> slots = new ArrayList<>(Arrays.asList("", "8h - 9h", "9h - 10h", "10h - 11h",
                "11h - 12h", "12h - 13h", "13h - 14h", "14h - 15h", "15h - 16h", "16h - 17h", "17h - 18h",
                "18h - 19h", "19h - 20h", "20h - 21h"));
        llistaSlots = FXCollections.observableArrayList(slots);
        rdah_hora.setItems(llistaSlots);
        rdah_hora.getSelectionModel().select(0);

        rad_aula.setItems(llistaAules);
        rad_aula.getSelectionModel().select(0);
        rad_dia.setItems(llistaDies);
        rad_dia.getSelectionModel().select(0);

        rmt = new ArrayList<>();
        rdah = new ArrayList<>();
        rad = new ArrayList<>();
    }

    /**
     * Carrega un horariTable a partir d'un String que contingui dades en format JSON
     *
     * @param horarijson L'horariTable en format JSON dins d'un String
     * @return True si ha carregat un arxiu valid, i false si es invalid
     */
    boolean loadHorari(String horarijson) {
        this.horarijson = horarijson;
        List horari;
        try {
            horari = new Gson().fromJson(horarijson, List.class);
            Map m = (Map) horari.get(0);
            String test = (String) m.get("assignatura");
            return test != null;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * Funció que es crida en canviar l'eleccio dins d'una de les ChoiceBoxes, i que actualitza l'horariTable
     *
     * @param assig Abreviació de l'assignatura
     * @param aula  Aula
     */
    private void handleAssigAulaChange(String assig, String aula) {
        // Set table as content after the first change in the ChoiceBoxes
        horari_container.setCenter(horariTable);
        // Set Slots in the table according to the choice
        dataTable.setAll(generateSlotsFromJson(assig, aula));
    }

    /**
     * Binding amb el controlador principal
     *
     * @param c main controller
     */
    void setMainController(CtrlMainView c) {
        this.ctrlMainView = c;
    }

    @FXML
    private void handleAddRMT() {
        String assignatura = rmt_assig.getValue();
        String matitarda = rmt_matitarda.getValue();
        if (assignatura.equalsIgnoreCase("") || matitarda.equalsIgnoreCase("")) {
            alert("Has de seleccionar els dos parametres");
        } else {
            HashMap<String, String> restriccio = new HashMap<>();
            restriccio.put("assignatura", assignatura);
            restriccio.put("matitarda", matitarda);
            rmt.add(restriccio);
            rmt_assig.getSelectionModel().select(0);
            rmt_matitarda.getSelectionModel().select(0);
        }
    }

    @FXML
    private void handleViewRMT() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restriccioDisplay.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Restriccions Mati-Tarda");
        stage.show();

        CtrlRestriccionsView c = loader.getController();
        c.setRestriccions(rmt);
    }

    @FXML
    private void handleAddRDAH() {
        String dia = rdah_dia.getValue();
        String aula = rdah_aula.getValue();
        String hora = String.valueOf(rdah_hora.getSelectionModel().getSelectedIndex() - 1);
        if (dia.equalsIgnoreCase("") || aula.equalsIgnoreCase("") || hora.equalsIgnoreCase("")) {
            alert("Has de seleccionar els tres parametres");
        } else {
            HashMap<String, String> restriccio = new HashMap<>();
            restriccio.put("dia", dia);
            restriccio.put("aula", aula);
            restriccio.put("hora", hora);
            rdah.add(restriccio);
            rdah_dia.getSelectionModel().select(0);
            rdah_hora.getSelectionModel().select(0);
            rdah_aula.getSelectionModel().select(0);
        }
    }

    @FXML
    private void handleViewRDAH() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restriccioDisplay.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Restriccions Dia-Aula-Hora");
        stage.show();

        CtrlRestriccionsView c = loader.getController();
        c.setRestriccions(rdah);
    }

    @FXML
    private void handleAddRAD() {
        String aula = rad_aula.getValue();
        String dia = rad_dia.getValue();
        if (aula.equalsIgnoreCase("") || dia.equalsIgnoreCase("")) {
            alert("Has de seleccionar els dos parametres");
        } else {
            HashMap<String, String> restriccio = new HashMap<>();
            restriccio.put("aula", aula);
            restriccio.put("dia", dia);
            rad.add(restriccio);
            rad_dia.getSelectionModel().select(0);
            rad_aula.getSelectionModel().select(0);
        }
    }

    @FXML
    private void handleViewRAD() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("restriccioDisplay.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Restriccions Aula-Dia");
        stage.show();

        CtrlRestriccionsView c = loader.getController();
        c.setRestriccions(rad);
    }

    @FXML
    private void handleGenerar() {
        loadHorari(ctrlDomini.generaHorari(rmt, rdah, rad, rc_checkbox.isSelected(), rgt_checkbox.isSelected()));
    }

    /**
     * Tanca la vista i propaga els canvis fets a la vista principal
     */
    public void exit() {
        ctrlMainView.reloadList();
        Stage stage = (Stage) cancel_button.getScene().getWindow();
        stage.close();
    }

    //TODO make this function into an interface, as it is shared between all views.

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
     * Retorna un array de Slots a partir de un Json que conté Horaris
     *
     * @param abbvr L'abreviació del nom de la assignatura
     * @param aula  El nom de l'aula
     * @return Un Array de Slot
     */

    private ArrayList<Slot> generateSlotsFromJson(String abbvr, String aula) {
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
        // Obté l'horariTable a partir del json
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
                    slots.get(numslot.intValue()).setDia(dia, abreviacio, String.valueOf(grup.intValue()), aulaslot);
                }
            }
            // Assignatura només
            if (mode == 1) {
                if (abbvr.equalsIgnoreCase(abreviacio)) {
                    Double numslot = (Double) m.get("hora");
                    String dia = (String) m.get("dia");
                    Double grup = (Double) m.get("grup");
                    slots.get(numslot.intValue()).setDia(dia, abreviacio, String.valueOf(grup.intValue()), aulaslot);
                }
            }
            // Aula i assignatura
            if (mode == 2) {
                if (abbvr.equalsIgnoreCase(abreviacio) && aula.equalsIgnoreCase(aulaslot)) {
                    Double numslot = (Double) m.get("hora");
                    String dia = (String) m.get("dia");
                    Double grup = (Double) m.get("grup");
                    slots.get(numslot.intValue()).setDia(dia, abreviacio, String.valueOf(grup.intValue()), aulaslot);
                }
            }
        }
        return slots;
    }

    @FXML
    private void handleCanviSlots() throws IOException {
        if(!swapWindow.isShowing()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("swapForm.fxml"));
            Parent root = loader.load();

            swapWindow.setScene(new Scene(root));
            swapWindow.setResizable(false);
            swapWindow.setTitle("Restriccions Dia-Aula-Hora");
            swapWindow.show();

            CtrlSwapView c = loader.getController();
            c.setCtrlHorariView(this);
        }else{
            // Bring the window to front
            swapWindow.setAlwaysOnTop(true);
            swapWindow.setAlwaysOnTop(false);
        }
    }

    /**
     * TODO Fix this and set it in the Slot class after everything works properly
     *
     * @return
     */
    HashMap<String, String> getCurrentSlot() {
        TableView.TableViewSelectionModel selected = horariTable.getSelectionModel();
        // Check if there is something selected
        if(selected.getSelectedIndex() != -1){
            TablePosition tablePosition = (TablePosition) selected.getSelectedCells().get(0);
            String text = (String) tablePosition.getTableColumn().getCellData(tablePosition.getRow());
            String[] splittext = text.split("\n");
            // Check if the cell is empty, as minimum length will be 2 for any occupied slot
            if (splittext.length > 1) {
                String assig = splittext[0];
                String[] grups = Arrays.copyOfRange(splittext, 1, splittext.length);
                List<String> grupsList = Arrays.asList(grups);
                ChoiceDialog<String> dialog = new ChoiceDialog<>(grupsList.get(0), grupsList);
                dialog.setTitle("Elecció de grup");
                dialog.setHeaderText("Falta escollir un grup!");
                dialog.setContentText("Grup: ");
                Optional<String> result = dialog.showAndWait();
                // If the user selected a group, continue
                if (result.isPresent()) {
                    HashMap<String, String> grupAula = Slot.grupAulaExtractor(result.get());
                    String dia = llistaDies.get(tablePosition.getColumn());
                    String slot = String.valueOf(tablePosition.getRow());
                    HashMap<String, String> output = new HashMap<>();
                    output.putAll(grupAula);
                    output.put("assignatura", assig);
                    output.put("dia", dia);
                    output.put("hora", slot);
                }
            }
        }
        return null;
    }
}
