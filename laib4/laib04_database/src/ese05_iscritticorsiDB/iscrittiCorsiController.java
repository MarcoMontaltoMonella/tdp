package ese05_iscritticorsiDB;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ese05_iscritticorsiDB.model.Course;
import ese05_iscritticorsiDB.model.iscrittiCorsiModel;

public class iscrittiCorsiController {

	private iscrittiCorsiModel model;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea displayResult;

    @FXML
    private TextField userInput;

    @FXML
    void doLookup(ActionEvent event) {
    	displayResult.clear();
    	String result = "";
    	for(Course s : model.getCoursesOf(Integer.parseInt(userInput.getText()))){
    		result += s.toString()+"\n";
    	}
    	displayResult.setText(result);
    }
    
    /**
     * @param model
     */
    public void setModel(iscrittiCorsiModel model) {
    	this.model = model;
    }

    
    @FXML
    void initialize() {
        assert displayResult != null : "fx:id=\"displayResult\" was not injected: check your FXML file 'iscrittiCorsiFXML.fxml'.";
        assert userInput != null : "fx:id=\"userInput\" was not injected: check your FXML file 'iscrittiCorsiFXML.fxml'.";

    }
}
