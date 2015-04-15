package ese05_iscritticorsiDB;
	
import ese05_iscritticorsiDB.model.iscrittiCorsiModel;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("iscrittiCorsiFXML.fxml"));
			BorderPane root = (BorderPane) loader.load();
			
			iscrittiCorsiController controller = loader.getController();
			
			Scene scene = new Scene(root);
			iscrittiCorsiModel model = new iscrittiCorsiModel();
			controller.setModel(model);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
