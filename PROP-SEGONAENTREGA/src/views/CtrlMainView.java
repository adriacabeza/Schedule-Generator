package views;

import controllers.CtrlDomini;
import controllers.GestorDisc;
import exceptions.NotFoundException;
import exceptions.RestriccioIntegritatException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Slot;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static javafx.application.Platform.exit;


public class CtrlMainView {

    /* Welcome */
    @FXML
    BorderPane welcome_content = new BorderPane();

    /* List */
    @FXML
    BorderPane list_content = new BorderPane();
    @FXML
    Label list_tab_title = new Label();
    @FXML
    Button plus_button = new Button();
    @FXML
    Button edit_button = new Button();
    @FXML
    Button delete_button = new Button();
    @FXML
    BorderPane list_inner_content = new BorderPane();
    @FXML
    ListView<String> list_view = new ListView<>();

    /* Specific */
    @FXML
    BorderPane specific_content = new BorderPane();
    @FXML
    Label specific_tab_title = new Label();
    @FXML
    BorderPane specific_inner_content = new BorderPane();


    private int state;
    private CtrlDomini controladorDomini;

    /**
     * Carrega la pantalla principal i les dades dels fitxers JSON
     */
    public void initialize() {
        mostraInici();
        controladorDomini = CtrlDomini.getInstance();

        try {
            boolean defaultOk = controladorDomini.carrega();
            if (defaultOk) {
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setContentText("S'han carregat els arxius per defecte correctament");
                a.showAndWait();
            } else {
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                Text text = new Text( "No s'ha pogut carregar les dades per defecte. Si us plau, indica la carpeta on trobar les dades");
                text.setWrappingWidth(200);
                a.getDialogPane().setPadding(new Insets(20,20,20,20));
                a.getDialogPane().setContent(text);
                Optional<ButtonType> result = a.showAndWait();

                if (result.get() == ButtonType.CANCEL) {
                    exit();
                    return;
                }

                DirectoryChooser dirChooser = new DirectoryChooser();
                dirChooser.setTitle("Escollir directori de dades");
                dirChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                File dir = dirChooser.showDialog(a.getOwner());
                if (dir == null) {
                    exit();
                    return;
                }

                controladorDomini.setDataDirectory(dir);
                int foundData = controladorDomini.carregaBusca();

                a = new Alert(Alert.AlertType.INFORMATION);

                String textD = "";
                if (foundData > 0 ) {
                    textD = "S'ha carregat \n";
                    if (foundData % 3 == 0) textD = textD.concat(" - assignatures \n");
                    if (foundData % 5 == 0) textD = textD.concat(" - aules \n");
                    if (foundData % 7 == 0) textD = textD.concat(" - plans d'estudi ");
                }
                else {
                    textD = "No s'ha carregat cap fitxer. \nEs fara servir el directori 'data'";
                }

                a.setContentText(textD);
                a.showAndWait();
            }
        } catch (IOException e) {

        }

        delete_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!list_view.getSelectionModel().isEmpty()) {
                    handleDelete(list_view.getSelectionModel().getSelectedItem());
                }
            }
        });


        edit_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!list_view.getSelectionModel().isEmpty()) {
                    handleModify(list_view.getSelectionModel().getSelectedItem());
                }
            }
        });

        plus_button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                handleAdd();
            }
        });

        list_view.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem(String s, boolean empty) {
                super.updateItem(s, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(s);
                    setOnMouseClicked(mouseClickedEvent -> {
                        if (mouseClickedEvent.getButton().equals(MouseButton.PRIMARY) && mouseClickedEvent.getClickCount() == 2) {
                            handleDisplay(s);
                        }
                    });
                }
            }
        });
    }

    /**
     * Esborra un element del tipus de llista concret segons de la pantalla on es troba
     *
     * @param item identificador del item a esborrar
     */
    private void handleDelete(String item) {
        switch (state) {
            case 1:
                try {
                    controladorDomini.esborrarPlaEstudis(item);
                } catch (NotFoundException e) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Pla no trobat");
                    a.setContentText("Error intern");
                    a.show();
                } catch (RestriccioIntegritatException e) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Pla no obsolet");
                    a.setContentText("No es pot esborrar un plà no obsolet");
                    a.show();
                } catch (IOException e) {
                    alert(e.getMessage());
                }
                break;
            case 2:
                try {
                    controladorDomini.esborrarAula(item);
                } catch (NotFoundException e) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Aula no trobada");
                    a.setContentText("Error intern");
                } catch (IOException e) {
                    alert(e.getMessage());
                }
                break;
            case 3:
                try {
                    try {
                        controladorDomini.esborrarAssignatura(item);
                    } catch (IOException e) {
                        alert(e.getMessage());
                    }
                } catch (NotFoundException e) {
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setTitle("Assignatura no trobada");
                    a.setContentText("Error intern");
                }
                break;
            default:
                break;
        }
        reloadList();
    }

    /**
     * Accedeix a modificar un element X segons l'estat on es trobi la pantalla
     *
     * @param item item concret a modificar, de tipus X
     */
    private void handleModify(String item) {
        switch (state) {
            case 1:
                try {
                    modificarPlaEstudis(item); //TODO same try catch
                } catch (IOException e) {
                    alert(e.getMessage());
                }
                break;
            case 2:
                try {
                    modificarAula(item);
                } catch (IOException e) {
                    alert(e.getMessage());
                }
                break;
            case 3:
                try {
                    modificarAssignatura(item);
                } catch (IOException e) {
                    alert(e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    /**
     * Accedeix a consultar un element X segons l'estat on es trobi la pantalla
     *
     * @param item item concret a consultar, de tipus X
     */
    private void handleDisplay(String item) {
        switch (state) {
            case 1:
                try {
                    consultarPlaEstudis(item); //TODO same try catch
                } catch (IOException e) {
                    alert(e.getMessage());
                }
                break;
            case 2:
                try {
                    consultarAula(item);
                } catch (IOException e) {
                    alert(e.getMessage());
                }
                break;
            case 3:
                try {
                    consultarAssignatura(item);
                } catch (IOException e) {
                    alert(e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    /**
     * Accedeix a afegir un element de tipus X segons l'estat on es trobi la pantalla
     */
    private void handleAdd() {
        switch (state) {
            case 1:
                try {
                    crearPlaEstudis();
                } catch (IOException e) {
                    alert(e.getMessage());
                }
                break;
            case 2:
                try {
                    crearAula();
                } catch (IOException e) {
                    alert(e.getMessage());
                }
                break;
            case 3:
                try {
                    crearAssignatura();
                } catch (IOException e) {
                    alert(e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    /**
     * Mostra la pantalla de llista de plans d'estudi amb l'opció d'esborrar-ne, afegir-ne i modificar-ne
     */
    public void mostraLlistaPlans() {
        state = 1;
        list_content.setVisible(true);
        specific_content.setVisible(false);
        welcome_content.setVisible(false);

        list_tab_title.setText("Llista de plans d'estudi");

        ObservableList<String> plans = FXCollections.observableArrayList(controladorDomini.getLlistaPlansEstudis());
        list_view.setItems(plans);
    }

    /**
     *Mostra la pantalla de llista d'aules amb l'opció d'esborrar-ne, afegir-ne i modificar-ne
     */
    public void mostraLlistaAules() {
        state = 2;
        list_content.setVisible(true);
        specific_content.setVisible(false);
        welcome_content.setVisible(false);

        list_tab_title.setText("Llista d'aules");

        ObservableList<String> aules = FXCollections.observableArrayList(controladorDomini.getLlistaAules());
        list_view.setItems(aules);
    }

    /**
     * Mostra la pantalla de llista d'assignatures amb l'opció d'esborrar-ne, afegir-ne i modificar-ne
     */
    public void mostraLlistaAssignatures() {
        state = 3;
        list_content.setVisible(true);
        specific_content.setVisible(false);
        welcome_content.setVisible(false);

        list_tab_title.setText("Llista d'assignatures");

        ObservableList<String> assignatures = FXCollections.observableArrayList(controladorDomini.getLlistaAssignatures());
        list_view.setItems(assignatures);
    }

    /**
     * Mostra la pantalla de gestió d'horaris, amb les opcions de carregar-ne un de disc, crear-ne un de nou o modificar-ne
     */
    public void mostraHorari() {
        state = 4;
        list_content.setVisible(false);
        specific_content.setVisible(true);
        welcome_content.setVisible(false);

        specific_tab_title.setText("Generador i visualitzador d'horaris");
    }

    /**
     * Funció que es crida en fer click al boto de "Carregar Horari"
     *
     * @throws IOException si no es troba el fitxer fxml
     */
    public void handleLoadHorari() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Carregar horari JSON");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showOpenDialog(this.list_view.getScene().getWindow());
        if (file != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("horariDisplay.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.setTitle("Consulta horari");

            CtrlHorariView c = loader.getController();
            c.setMainController(this);
            if (!c.loadHorari(controladorDomini.llegeixHorari(file.getAbsolutePath()))) {
                alert("El fitxer sembla no ser valid. Revisa'l.");
                return;
            }
            stage.show();
        }
    }

    /**
     * Funció que es crida en fer click al boto de "Generar Horari"
     *
     * @throws IOException si no es troba el fitxer fxml
     */
    public void handleGenerateHorari() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("horariForm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Generar i editar Horari");
        stage.show();

        CtrlHorariView c = loader.getController();
        c.setMainController(this);
        c.setGenerateMode();
    }

    /**
     * Mostra la pantalla inicial
     */
    public void mostraInici() {
        state = 0;
        list_content.setVisible(false);
        specific_content.setVisible(false);
        welcome_content.setVisible(true);
    }

    /**
     * Actualitza l'informació de la llista mostrada per pantalla
     */
    void reloadList() {
        switch (state) {
            case 1:
                mostraLlistaPlans();
                break;
            case 2:
                mostraLlistaAules();
                break;
            case 3:
                mostraLlistaAssignatures();
                break;
            case 4:
                mostraHorari();
            default:
                break;
        }
    }

    /**
     * Obre la finestra per crear una nova assignatura amb tots els camps buits
     *
     * @throws IOException quan hi ha un error obrint la nova finestra
     */
    private void crearAssignatura() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("assignaturaForm.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Crear nova assignatura");
        stage.show();

        CtrlAssignaturaView ca = loader.getController();
        ca.setMainController(this);
    }

    /**
     * Obre la finestra per modificar una assignatura amb els camps disponibles
     *
     * @param nomAssignatura nom de l'assignatura a modificar
     * @throws IOException quan hi ha un error obrint la nova finestra
     */
    void modificarAssignatura(String nomAssignatura) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("assignaturaForm.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Modificar assignatura: " + nomAssignatura);
        stage.show();

        CtrlAssignaturaView ca = loader.getController();
        ca.loadAssignatura(nomAssignatura);
        ca.setMainController(this);
    }

    /**
     * Obre la finestra per consultar una assignatura
     *
     * @param nomAssignatura nom de l'assignatura a consultar
     * @throws IOException quan hi ha un error obrint la nova finestra
     */
    private void consultarAssignatura(String nomAssignatura) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("assignaturaDisplay.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Consultar assignatura: " + nomAssignatura);
        stage.show();
        CtrlAssignaturaView ca = loader.getController();
        ca.displayAssignatura(nomAssignatura);
        ca.setMainController(this);
    }

    /**
     * Obre la finestra per crear una aula amb tots els camps buits
     *
     * @throws IOException quan hi ha un error obrint la nova finestra
     */
    private void crearAula() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("aulaForm.fxml"));
        Parent root = null;
        root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Crear aula");
        stage.show();

        CtrlAulaView c = loader.getController();
        c.setMainController(this);
    }

    /**
     * Obre la finestra per modificar una aula en concret amb els camps disponibles
     *
     * @param nomAula nom de l'aula a modificar
     * @throws IOException quan hi ha un error obrint la nova finestra
     */
    private void modificarAula(String nomAula) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("aulaForm.fxml"));
        Parent root = null;
        root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Modificar aula: " + nomAula);
        stage.show();

        CtrlAulaView c = loader.getController();
        c.setMainController(this);
        c.loadAula(nomAula);
        c.disableEditFields();
    }

    /**
     * Obre la finestra per visualitzar una aula concreta
     *
     * @param nomAula nom de l'aula que es vol visualitzar
     * @throws IOException quan hi ha un error obrint la nova finestra
     */
    private void consultarAula(String nomAula) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("aulaDisplay.fxml"));
        Parent root = null;
        root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Consultar aula: " + nomAula);
        stage.show();

        CtrlAulaView c = loader.getController();
        c.setMainController(this);
        c.displayAula(nomAula);
    }

    /**
     * Obre la finestra per tal de crear un nou pla d'estudis
     *
     * @throws IOException quan hi ha un error obrint la nova finestra
     */
    private void crearPlaEstudis() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("plaEstudisForm.fxml"));
        Parent root = null;
        root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Crear Pla d'estudis");
        stage.show();

        CtrlPlaEstudisView c = loader.getController();
        c.setMainController(this);
        c.disableCreateFields();
    }

    /**
     * Obre la vista per veure i habilitar l'edició d'un plà d'estudis en concret. Només es podran editar els camps habilitats
     *
     * @param nomPla nom del pla d'estudis que es vol modificar
     * @throws IOException quan hi ha un error obrint la nova finestra
     */
    private void modificarPlaEstudis(String nomPla) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("plaEstudisForm.fxml"));
        Parent root = null;
        root = loader.load();
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.setTitle("Modificar Pla d'estudis: " + nomPla);
        stage.show();

        CtrlPlaEstudisView c = loader.getController();
        c.setMainController(this);
        c.disableEditFields();
        c.loadPlaEstudis(nomPla);
    }

    /**
     * Obre la vista per consultar un pla d'estudis en concret
     *
     * @param nomPla nom del pla d'estudis seleccionat
     * @throws IOException quan hi ha un error obrint la nova finestra
     */
    private void consultarPlaEstudis(String nomPla) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("plaEstudisDisplay.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.setTitle("Consultar assignatura: " + nomPla);
        stage.setTitle(nomPla);
        stage.setResizable(false);
        stage.show();


        CtrlPlaEstudisView ca = loader.getController();
        ca.displayPlaEstudis(nomPla);
    }

    private void alert(String s) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(s);
        a.setHeaderText("Hi ha hagut un error");
        a.show();
    }
}
