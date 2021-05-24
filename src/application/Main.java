package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	private MenuController menu;
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
			Parent root = fxmlLoader.load();
			menu = fxmlLoader.getController();
			setMenu(fxmlLoader.getController());
			Scene scene= new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/resources/MainMenu.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("computer.png")));
			primaryStage.setTitle("cyk algorithm");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	public MenuController getMenu() {
		return menu;
	}

	public void setMenu(MenuController menu) {
		this.menu = menu;
	}
}
