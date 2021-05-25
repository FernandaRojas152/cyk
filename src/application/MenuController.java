package application;

import java.util.NoSuchElementException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.CYK;

public class MenuController {
	
	public static final String LAMBDA = "λ";

	private CYK CYK;
	
	@FXML
    private TabPane tabPane;

	@FXML
	private BorderPane border_pane;

    @FXML
    private Pane pane;

    @FXML
    private HBox buttonsPane;

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
    void initialize() {
    	CFGTab.setOnSelectionChanged( event -> buttonsPane.setVisible(true));
        resultTab.setOnSelectionChanged( event -> buttonsPane.setVisible(false));

        createNewRow();
    }

    /**
     * Adds a production to the grammar
     */
    @FXML
    void addRow(ActionEvent event) {
    	createNewRow();
    }

    /**
     * Adds a new string to verify if it is produced by the grammar.
     */
    @FXML
    void addString(ActionEvent event) {
    	 TextInputDialog tid = new TextInputDialog();
         tid.setHeaderText(null);
         tid.setTitle("String");
         tid.setContentText("Introduce a value for w");
         Optional<String> str = tid.showAndWait();

         try{
             if(!str.get().isEmpty()){
                 txtW.setText(str.get());
             }

         }catch (NoSuchElementException e){

         }
         txtW.setAlignment(Pos.CENTER);
    }
    
    /**
     * cleans fields 
     */
    @FXML
    void clean(ActionEvent event) {
    	CFGPane.getChildren().clear();
        CYKPane.getChildren().clear();
       
        txtW.setText("");
        this.CYK = null;
        
        createNewRow();
        resultTab.setDisable(true);
    }

    /**
     * Method that executes the CYK algorithm.
     */
    @FXML
    void runCYK(ActionEvent event) {
		
    	CYKPane.getChildren().clear();
    	
        this.CYK = new CYK(grammarToMatrix(), txtW.getText().length());
        this.CYK.addValueToMap();
        this.CYK.addFirstColumn(txtW.getText());
        this.CYK.CYKAlgorthm(txtW.getText());
        
        showMessage();
        generateMatrix();
        
    	resultTab.setDisable(false);
        tabPane.getSelectionModel().select(resultTab);
        
    }
    
    /**
     * Method that creates  TextFields to add productions of the grammar.
     */
    private void createNewRow(){

        HBox hBox = new HBox();
        CFGPane.getChildren().add(hBox);

        hBox.prefWidth(685d);
        hBox.prefHeight(50d);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(3d);

        TextField txt = new TextField();
        hBox.getChildren().add(txt);
        txt.setPrefSize(80d, 30d);
        txt.setAlignment(Pos.CENTER);
        txt.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        txt.setFont(Font.font("System", FontWeight.BOLD, 14d));

        TextField txt1 = new TextField();
        hBox.getChildren().add(txt1);
        txt1.setPrefSize(80d, 30d);
        txt1.setAlignment(Pos.CENTER);
        txt1.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        txt1.setFont(Font.font("System", FontWeight.BOLD, 14d));
        txt1.setText("->");
        txt1.setEditable(false);

        TextField textField3 = new TextField();
        hBox.getChildren().add(textField3);
        textField3.setPrefSize(505d, 30d);
        textField3.setAlignment(Pos.CENTER_LEFT);
        textField3.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        textField3.setFont(Font.font("System", 14d));

        txt.setOnKeyPressed(event -> {
            if(event.getCode()== KeyCode.ENTER && !txt.getText().equals("")){
                textField3.setText(textField3.getText() + LAMBDA);
            }
        });
    }
    
    /**
     * Method that is responsible that converts the productions, to a matrix
     * @return array to determine if it produces the string entered by the user
     */
    private String[][] grammarToMatrix() {
    	String[][] grammar = new String[CFGPane.getChildren().size()][2];
    	
    	for (int i = 0; i < CFGPane.getChildren().size(); i++) {
			HBox hBox = (HBox) CFGPane.getChildren().get(i);
			
			TextField textField1 = (TextField) hBox.getChildren().get(0);
			TextField textField2 = (TextField) hBox.getChildren().get(2);
			
			grammar[i][0] = textField1.getText();
			grammar[i][1] = textField2.getText();
		}
    	return grammar;
    }
    
    /**
     * message confirming or not the string was produced by the grammar.
     */
	private void showMessage() {

		TextField textField = new TextField();
		CYKPane.getChildren().add(textField);

		textField.setAlignment(Pos.CENTER);
		textField.setEditable(false);
		textField.setPrefSize(698, 30d);

		textField.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		textField.setFont(Font.font("System", FontWeight.BOLD, 14));

		if (this.CYK.containsString()) {
			textField.setText("La cadena ingresada es generada por la GIC");
			textField.setBackground(
					new Background(new BackgroundFill(Color.valueOf("#c6f6f5"), CornerRadii.EMPTY, Insets.EMPTY)));

		} else {
			textField.setText("La cadena ingresada no es generada por la GIC");
			textField.setBackground(
					new Background(new BackgroundFill(Color.valueOf("#FF5555"), CornerRadii.EMPTY, Insets.EMPTY)));
		}
	}
	
	 /**
     * Method that creates the J indices.
     */
	private void generateMatrix() {
    	HBox hBox = new HBox();
    	VBox vBox = new VBox();
    	
    	hBox.setSpacing(3d);
    	hBox.setAlignment(Pos.CENTER);
    	vBox.getChildren().add(hBox);
    	
    	Label label = new Label();
    	label.setPrefHeight(30);
		label.setPrefWidth(40);
		hBox.getChildren().add(label);
    	
    	for (int i = 0; i < txtW.getText().length(); i++) {
    		Label label2 = new Label();
    		label2.setAlignment(Pos.CENTER);
    		label2.setPrefHeight(30);
    		label2.setPrefWidth(40);
			
    		label2.setText("j= " + (i+1));
			hBox.getChildren().add(label2);
		}
    	printMatrix(vBox);
    }
	
	/**
	 * Result CYK algorithm.
	 */
	private void printMatrix(VBox vBox) {
		GridPane gridPane = new GridPane();
		String[][] matrixResult = this.CYK.getResultMatrix();

		gridPane.setHgap(3);
		gridPane.setVgap(3);
		gridPane.setAlignment(Pos.CENTER);

		for (int i = 0; i < matrixResult.length; i++) {

			Label label = new Label("i= " + (i + 1));
			label.setAlignment(Pos.CENTER);
			label.setPrefHeight(30);
			label.setPrefWidth(40);
			gridPane.add(label, 0, (i + 1));

			for (int j = 0; j < matrixResult[0].length; j++) {

				Label label2 = new Label();
				label2.setAlignment(Pos.CENTER);
				label2.setPrefHeight(30);
				label2.setPrefWidth(40);
				label2.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
						BorderWidths.DEFAULT)));

				if (matrixResult[i][j] != null) {
					label2.setText("{" + matrixResult[i][j] + "}");
				} else {
					label2.setText("");
				}
				gridPane.add(label2, (j + 1), (i + 1));
			}
		}
		vBox.getChildren().add(gridPane);
		this.CYKPane.getChildren().add(vBox);
	}

}
