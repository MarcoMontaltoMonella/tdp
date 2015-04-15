package it.mmm.tdp.contacts;
	
import it.mmm.tdp.contacts.model.ContactsDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ContactsFXML.fxml"));
			BorderPane root = (BorderPane) loader.load();
			
			
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			ContactsViewController controller = loader.getController();
			ContactsDB model = new ContactsDB();
			controller.setModel(model);
			controller.getPrimaryStage(primaryStage);
			
			SwitchButton genderSwitch = new SwitchButton();
			
			
			//lookup
			GridPane grid = (GridPane) root.lookup("#gridPaneUP");// (GridPane) root.getChildren().get(0);
			grid.add(genderSwitch, 1, 4);
			controller.getSwitchButton(genderSwitch);
			
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
