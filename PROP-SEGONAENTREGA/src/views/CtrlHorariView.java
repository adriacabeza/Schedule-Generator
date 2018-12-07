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

    /**
     * Init function
     */
    public void initialize() {

    }

    public void loadHorari(){
        ObservableList<Slot> data = FXCollections.observableArrayList();
        Slot s = new Slot("8 - 10", "AS\nTest\ntest", "AC\nTest\ntest", "AD\nTest\ntest", "AE\nTest\ntest", "AQ\nTest\ntest");
        Slot s1 = new Slot("10 - 12","AS\nTest\ntest", "-", "AD\nTest\ntest", "AE\nTest\ntest", "AQ\nTest\ntest");
        Slot s2 = new Slot("12 - 14","AS\nTest\ntest", "AC\nTest\ntest", "AD\nTest\ntest", "AE\nTest\ntest", "AQ\nTest\ntest");
        Slot s3 = new Slot("14 - 16","AS\nTest\ntest", "AC\nTest\ntest", "AD\nTest\ntest", "AE\nTest\ntest", "AQ\nTest\ntest");

        data.addAll(s, s1, s2, s3);

        TableView<Slot> horari = new TableView<>();
        horari.getSelectionModel().setCellSelectionEnabled(true);
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
        horari.setItems(data);

        horari_container.setCenter(horari);
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

}
