package it.mmm.tdp.hangman;

import it.mmm.tdp.hangman.model.Model;
import it.mmm.tdp.hangman.model.RepeatedCharException;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class HangmanViewController {

	private Model model;
	private boolean oneFlip1 = true, oneFlip2 = true;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Line stickArmRight;

    @FXML
    private Line stickRightLeg;

    @FXML
    private Line ropeTop;
    
    @FXML
    private Button startButton;

    @FXML
    private Circle statusCircle;

    @FXML
    private Circle stickRightEye;

    @FXML
    private Line stickArmLeft;

    @FXML
    private Circle stickLeftEye;

    @FXML
    private TextField displayHiddenWord;

    @FXML
    private Label numMistakes;

    @FXML
    private Line stickBody;

    @FXML
    private TextField displayMessages;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Circle stickHead;

    @FXML
    private ComboBox<String> alphabetComboBox;

    @FXML
    private Arc stickMouth;

    @FXML
    private Line ropeHang2;

    @FXML
    private Line ropeHang1;

    @FXML
    private Line stickLeftLeg;
    
    @FXML
    private PasswordField secretWord;
    
    @FXML
    private Label youDied;

    public void setCircleStatus(){
    	if(model.isPlaying())
    		this.statusCircle.setFill(Paint.valueOf("#00FF00"));
    	else
    		this.statusCircle.setFill(Paint.valueOf("#FF0000"));
    }


    @FXML
    void doStart(ActionEvent event) {
    	oneFlip1 = oneFlip2 = true;
    	if(model.isPlaying() && model.getNumberOfErrors()!=8){
    		//RESTARTING
    			model.reset();
    			hideStickman.run();
    			model.startGame();
    			this.displayMessages.setText("");
    			this.secretWord.setText("");
    			this.secretWord.setDisable(false);
    			this.alphabetComboBox.setDisable(true);
    			this.startButton.setText("Start");
    			this.displayHiddenWord.setText("");
    			this.displayHiddenWord.setDisable(true);
    			model.endGame();
    			this.numMistakes.setText("0");
    			this.youDied.setVisible(false);
    			setCircleStatus();
    			this.progressBar.setProgress(0.0);
    	}else{
    		if(!this.secretWord.getText().equals("") && model.getNumberOfErrors()!=8){
    			//STARTING FIRST TIME
    			this.youDied.setVisible(false);
    			model.setSecretWord(this.secretWord.getText());
    			hideStickman.run();
    			model.startGame();
    			setCircleStatus();
    			toggleBtnsON.run();
    			this.secretWord.setDisable(true);
    			this.numMistakes.setText("0");
    			this.startButton.setText("Restart");
    			this.displayHiddenWord.setText(model.getHiddenSecretWord());
    			this.progressBar.setProgress(0.0);
    		}else
    			if(model.getNumberOfErrors()!=8)
    				this.displayMessages.setText("Insert the secret word first!");
    	}
    	
    	if(model.getNumberOfErrors()==8 && model.getFinalWord().compareTo(model.getSuperSecretWord())==0){
    		
//			hideStickman.run();
//			model.startGame();
//			this.displayMessages.setText("");
    		this.displayMessages.setText(model.getSuperSecretWord());
			this.secretWord.setText("");
			this.secretWord.setDisable(false);
			this.alphabetComboBox.setDisable(true);
			this.startButton.setText("Start");
			this.displayHiddenWord.setText("");
			this.displayHiddenWord.setDisable(true);
			this.numMistakes.setText("0");
			this.youDied.setVisible(true);
			setCircleStatus();
			this.progressBar.setProgress(0.0);
			model.reset();
    	}
    	
    	if(model.getNumberOfErrors()==8 && model.getFinalWord().compareTo(model.getSuperSecretWord())!=0){
			this.secretWord.setText("");
			this.secretWord.setDisable(false);
			this.alphabetComboBox.setDisable(true);
			this.startButton.setText("Start");
			this.displayHiddenWord.setText("");
			this.displayHiddenWord.setDisable(true);
			this.numMistakes.setText("0");
			this.youDied.setVisible(true);
			setCircleStatus();
			this.progressBar.setProgress(0.0);
			model.reset();
    	}
    	
    		
    }
    
    @FXML
    void doChar(ActionEvent event){
    	
    	
    	
    	
    	this.displayMessages.setText("");
    	try {
			model.guessChar(alphabetComboBox.getValue());
		} catch (RepeatedCharException e) {
			this.displayMessages.setText("Char already inserted!");
		}
    	this.numMistakes.setText(""+model.getNumberOfErrors());
    	this.displayHiddenWord.setText(model.getHiddenSecretWord());
    	drawingStickMan.run();
    	if(model.getFinalWord().compareTo(model.getSuperSecretWord())==0){
    		System.out.println("WIN!");
    		winningStickman.run();
    		model.setNumberOfErrors(8);
    	}
    }

    public void setModel(Model model) {
    	this.model = model;
    }
    
    

    @FXML
    void initialize() {
        assert stickArmRight != null : "fx:id=\"stickArmRight\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert stickRightLeg != null : "fx:id=\"stickRightLeg\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert ropeTop != null : "fx:id=\"ropeTop\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert statusCircle != null : "fx:id=\"statusCircle\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert stickRightEye != null : "fx:id=\"stickRightEye\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert stickArmLeft != null : "fx:id=\"stickArmLeft\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert stickLeftEye != null : "fx:id=\"stickLeftEye\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert displayHiddenWord != null : "fx:id=\"displayHiddenWord\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert numMistakes != null : "fx:id=\"numMistakes\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert stickBody != null : "fx:id=\"stickBody\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert displayMessages != null : "fx:id=\"displayMessages\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert progressBar != null : "fx:id=\"progressBar\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert stickHead != null : "fx:id=\"stickHead\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert alphabetComboBox != null : "fx:id=\"alphabetComboBox\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert stickMouth != null : "fx:id=\"stickMouth\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert ropeHang2 != null : "fx:id=\"ropeHang2\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert ropeHang1 != null : "fx:id=\"ropeHang1\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        assert stickLeftLeg != null : "fx:id=\"stickLeftLeg\" was not injected: check your FXML file 'hangmanFXML.fxml'.";
        
        
        this.alphabetComboBox.setItems(FXCollections.observableArrayList("a","b","c","d","e","f","g","h","i","j",
        		"k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
        		"0","1","2","3","4","5","6","7","8","9"));

        alphabetComboBox.setTooltip(new Tooltip("Choose a letter"));
    }
    
    Runnable toggleBtnsON = () -> {
    	alphabetComboBox.setDisable(false);
    	progressBar.setDisable(false);
    	displayHiddenWord.setDisable(false);
//    	displayMessages.setDisable(false);
	};
	
	Runnable toggleBtnsOFF = () -> {
		alphabetComboBox.setDisable(true);
    	progressBar.setDisable(true);
    	displayHiddenWord.setDisable(true);
//    	displayMessages.setDisable(true);
	};
	
	Runnable hideStickman = () -> {
		this.stickHead.setVisible(false);
		this.stickLeftEye.setVisible(false);
		this.stickRightEye.setVisible(false);
		this.stickMouth.setVisible(false);
		this.stickBody.setVisible(false);
		this.stickArmLeft.setVisible(false);
		this.stickArmRight.setVisible(false);
		this.stickLeftLeg.setVisible(false);
		this.stickRightLeg.setVisible(false);
		this.ropeTop.setVisible(false);
		this.ropeHang1.setVisible(false);
		this.ropeHang2.setVisible(false);
	};
	
	Runnable winningStickman = () -> {
		hideStickman.run();
		this.alphabetComboBox.setDisable(true);
		this.stickHead.setVisible(true);
		this.stickLeftEye.setVisible(true);
		this.stickRightEye.setVisible(true);
		this.stickMouth.setVisible(true);
		this.stickMouth.getTransforms().clear();
		this.stickMouth.getTransforms().add(new Rotate(180, this.stickMouth.getCenterX(), this.stickMouth.getCenterY()-5));
		this.stickBody.setVisible(true);
		this.stickArmLeft.setVisible(true);
		this.stickArmRight.setVisible(true);
		this.stickLeftLeg.setVisible(true);
		this.stickRightLeg.setVisible(true);
	};
	
	Runnable drawingStickMan = () -> {
		int x = model.getNumberOfErrors();
		switch (x) {
		case 1:
			this.stickHead.setVisible(true);
			this.stickLeftEye.setVisible(true);
			this.stickRightEye.setVisible(true);
			this.progressBar.setProgress((double)1/8);
			break;
		case 2:
			this.stickMouth.setVisible(true);
			if(oneFlip1){
				this.stickMouth.getTransforms().clear();
				this.stickMouth.getTransforms().add(new Rotate(180, this.stickMouth.getCenterX(), this.stickMouth.getCenterY()-5));
				oneFlip1=false;
			}
			this.progressBar.setProgress((double)2/8);
			break;
		case 3:
			this.ropeTop.setVisible(true);
			this.ropeHang1.setVisible(true);
			this.ropeHang2.setVisible(true);
			
			if(oneFlip2){
				this.stickMouth.getTransforms().clear();
//				this.stickMouth.getTransforms().add(new Rotate(180, this.stickMouth.getCenterX(), this.stickMouth.getCenterY()-5));
				oneFlip2 = false;
			}
			this.progressBar.setProgress((double)3/8);
			break;
		case 4:
			this.stickBody.setVisible(true);
			this.progressBar.setProgress((double)4/8);
			break;
		case 5:
			this.stickArmLeft.setVisible(true);
			this.progressBar.setProgress((double)5/8);
			break;
		case 6:
			this.stickArmRight.setVisible(true);
			this.progressBar.setProgress((double)6/8);
			break;
		case 7:
			this.stickLeftLeg.setVisible(true);
			this.progressBar.setProgress((double)7/8);
			break;
		case 8:
			this.stickRightLeg.setVisible(true);
			this.youDied.setVisible(true);
			this.alphabetComboBox.setDisable(true);
			this.progressBar.setProgress((double)8/8);
			toggleBtnsOFF.run();
			model.endGame();
//			model.reset();
			setCircleStatus();
			this.displayHiddenWord.setText("");
			this.displayMessages.setText(model.getSuperSecretWord());
			doStart(new ActionEvent());
			break;

		default: 
			break;
		}
	};
}
	 
