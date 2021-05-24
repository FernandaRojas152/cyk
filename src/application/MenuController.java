package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MenuController {

    @FXML
    private BorderPane border_pane;

    @FXML
    private Pane pane;

    @FXML
    private HBox buttonsPanel_HBox;

    @FXML
    private Tab CFGTab;

    @FXML
    private VBox CFGPane;

    @FXML
    private TextField txtW;

    @FXML
    private Tab resultTab;

    @FXML
    private VBox CYKPane;

    @FXML
    void addRow(ActionEvent event) {

    }

    @FXML
    void addString(ActionEvent event) {

    }
    
    @FXML
    void clean(ActionEvent event) {

    }

    @FXML
    void runCYK(ActionEvent event) {

    }

}

