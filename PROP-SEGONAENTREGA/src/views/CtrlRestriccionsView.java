package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class CtrlRestriccionsView {

    @FXML
    ListView<String> llistaRestriccions =  new ListView<>();
    @FXML
    Button restriccions_elimina = new Button();

    private ArrayList<HashMap<String, String>> restriccions;
    private ObservableList<String> restriccionsList;


    /**
     * Init function
     */
    public void initialize() {

    }

    public void setRestriccions(ArrayList<HashMap<String, String>> res){
        this.restriccions = res;

        restriccionsList = FXCollections.observableArrayList();
        for(HashMap<String, String> restriccio : res){
            StringBuilder sb = new StringBuilder();
            for (String key: restriccio.keySet()){
                if(sb.length() == 0){
                    sb.append(key).append(": ").append(restriccio.get(key));
                }else{
                    sb.append("\n").append(key).append(": ").append(restriccio.get(key));
                }
            }
            restriccionsList.add(sb.toString());
        }
        llistaRestriccions.setItems(restriccionsList);
    }

    @FXML
    private void handleElimina(){
        int selected = llistaRestriccions.getSelectionModel().getSelectedIndex();
        if(selected == -1) return;
        restriccionsList.remove(selected);
        restriccions.remove(selected);
    }

}