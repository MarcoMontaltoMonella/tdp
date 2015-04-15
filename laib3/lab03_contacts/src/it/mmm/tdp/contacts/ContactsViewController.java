package it.mmm.tdp.contacts;

import it.mmm.tdp.contacts.model.ContactsDB;
import it.mmm.tdp.contacts.model.DuplicateContactException;
import it.mmm.tdp.contacts.model.Person;
import it.mmm.tdp.contacts.model.Person.Gender;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.xml.internal.bind.v2.runtime.output.ForkXmlOutput;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;

public class ContactsViewController {

	private ContactsDB model;
	
	Desktop desktop= Desktop.getDesktop();
	
	Stage primaryStage;

	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ScrollPane scrollPane;
    
    @FXML
    private Label displayPhone;
    
    @FXML
    private Button buttonEdit;
    
    @FXML
    private Button buttonApplyUP;
    
    @FXML
    private Button buttonDelete;
    
    @FXML
    private Button buttonApplyDOWN;
    
    @FXML
    private RadioButton radioSurname;

    @FXML
    private RadioButton radioName;

    @FXML
    private Label displaySurname;

    @FXML
    private Label displayName;

    @FXML
    private TextField idUP;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField labelPhone;

    @FXML
    private TextField idDOWN;

    @FXML
    private ImageView displayImage;

    @FXML
    private Label displayBirth;

    @FXML
    private Label displayMessages;

    @FXML
    private GridPane gridPaneUP;

    @FXML
    private Accordion accordionMain;

    @FXML
    private Label displayID;

    @FXML
    private TextField labelName;

    @FXML
    private TextField labelSurname;

    @FXML
    private Button fileButton;
    
    private FileChooser fileChooser;

    private String tmpPicAbsolutePath = null;

	private SwitchButton genderSwitch;
	
    
    @FXML
    void doAdd(ActionEvent event) {
    	if(!labelName.getText().equals("") && !labelSurname.getText().equals("")
    			&& !labelPhone.getText().equals("") && 
    			datePicker.getValue()!= null){
    		String birth = datePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE);
//    		System.out.println(birth);
    		
    		
    		Gender sex;
    		
    		if(genderSwitch.switchOnProperty().get())
        		sex = Gender.FEMALE;
        	else
        		sex = Gender.MALE;
    		
    		
    		
    		if(tmpPicAbsolutePath!=null){
    			//with image
//    			System.out.println(tmpPicAbsolutePath);
    			try {
					model.addPerson(new Person(labelName.getText(), labelSurname.getText(),
							birth, labelPhone.getText(), sex, tmpPicAbsolutePath));
//					System.out.println(tmpPicAbsolutePath);
					
				} catch (DuplicateContactException e) {
					
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText(e.getMessage());
					alert.setContentText("Careful with the next step!");
					alert.showAndWait();
					
					tmpPicAbsolutePath = null;
					return;
				}
    		}
    		else{
    			//without image
    			try {
					model.addPerson(new Person(labelName.getText(), labelSurname.getText(),
							birth, labelPhone.getText(), sex));
				} catch (DuplicateContactException e) {
					
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Warning Dialog");
					alert.setHeaderText(e.getMessage());
					alert.setContentText("Careful with the next step!");
					alert.showAndWait();
					
					tmpPicAbsolutePath = null;
					return;
				}
    		}
    		
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("Well done!");
			if(sex.equals(Gender.MALE))
				alert.setGraphic(new ImageView("userMan.png")); //this.getClass().getResource("res/userMan.png").toString()));
			else
				alert.setGraphic(new ImageView("userWoman.png")); //this.getClass().getResource("res/userWoman.png").toString()));
			alert.setContentText("Contact successfully added to the database!");
			alert.showAndWait();
    		
    	}else{
    		//missing fields
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Missing fields!");
			alert.setContentText("Careful with the next step!");
			alert.showAndWait();
    	}
    	//reset
    	tmpPicAbsolutePath = null;
    	labelName.setText("");
    	labelSurname.setText("");
    	labelPhone.setText("");
    	labelPhone.setText("");
    	datePicker.setValue(null);
    }
    

    @FXML
    void doSearch(ActionEvent event) {
    	accordionMain.getPanes().clear();
    	if(!model.isEmptyDB()){
    		if(datePicker.getValue()==null){
    			//without date
    			ArrayList<Person> result = model.searchPersons(labelName.getText(), labelSurname.getText(), "",
    					labelPhone.getText());
    			if(!result.isEmpty())		
    				displayQueryResult(result);
    			else{
    				Alert alert = new Alert(AlertType.INFORMATION);
    				alert.setTitle("Information Dialog");
    				alert.setHeaderText("No matches!");
    				alert.setContentText("Good luck with the next try!");
    				alert.showAndWait();
    			}
    		}
    		else{
    			//with date
    			ArrayList<Person> result = model.searchPersons(labelName.getText(), labelSurname.getText(), 
    					datePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE),
    					labelPhone.getText());
    			if(!result.isEmpty())		
    				displayQueryResult(result);
    			else{
    				Alert alert = new Alert(AlertType.INFORMATION);
    				alert.setTitle("Information Dialog");
    				alert.setHeaderText("No matches!");
    				alert.setContentText("Good luck with the next try!");
    				alert.showAndWait();
    			}
    		}
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning Dialog");
    		alert.setHeaderText("Empty database!");
    		alert.setContentText("Careful with the next step!");
    		alert.showAndWait();
    	}
    }


    /**
	 * @param result
	 */
	private void displayQueryResult(ArrayList<Person> result) {
		accordionMain.getPanes().clear();

		if(!model.isEmptyDB()){

			ArrayList<String> letters;

			if(radioName.isSelected()){
				//BY NAME
				letters = model.getCurrentLettersNames(result);
				Collections.sort(letters);

				for(String singleLetter : letters){

					ListView<Button> names = new ListView<>();
					ArrayList<Button> arrayLabels = new ArrayList<>();
					//adding all names into a listView and 
					//add an EventHandler to every element of the listView
					for(Person p : model.getPersonsNameStartingWith(singleLetter)){
						Button lab = new Button(p.getName() +" "+ p.getSurname());
						lab.setMinWidth(150.0);
						
						if(p.getSex().equals(Gender.MALE))
    						lab.setStyle("-fx-base: #BAFCFF;");
    					else
    						lab.setStyle("-fx-base: #FFD1F6;");


						lab.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								displayProfile(p);
							}
						});


						arrayLabels.add(lab);

					}

					ObservableList<Button> listOfLabels = FXCollections.observableArrayList(arrayLabels);

					names.setItems(listOfLabels);
					//set a TitledPane for each letter adding the listView to a tiltedPane
					TitledPane tp_tmp = new TitledPane(singleLetter, names);
					tp_tmp.setPrefHeight(Region.USE_COMPUTED_SIZE);
					tp_tmp.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);

					accordionMain.getPanes().add(tp_tmp);



				}

			}else{
				//BY SURNAME
				letters = model.getCurrentLettersSurnames(result);
				Collections.sort(letters);
				//copy of byNAME after this

				for(String singleLetter : letters){

					ListView<Button> names = new ListView<>();
					ArrayList<Button> arrayLabels = new ArrayList<>();
					//adding all names into a listView and 
					//add an EventHandler to every element of the listView
					for(Person p : model.getPersonsSurnameStartingWith(singleLetter)){
						Button lab = new Button(p.getSurname() +" "+ p.getName());
						lab.setMinWidth(150.0);
						
						if(p.getSex().equals(Gender.MALE))
    						lab.setStyle("-fx-base: #BAFCFF;");
    					else
    						lab.setStyle("-fx-base: #FFD1F6;");

						lab.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								displayProfile(p);
							}
						});


						arrayLabels.add(lab);
					}

					ObservableList<Button> listOfLabels = FXCollections.observableArrayList(arrayLabels);

					names.setItems(listOfLabels);
					//set a TitledPane for each letter adding the listView to a tiltedPane
					TitledPane tp_tmp = new TitledPane(singleLetter, names);
					tp_tmp.setPrefHeight(Region.USE_COMPUTED_SIZE);
					tp_tmp.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
					accordionMain.getPanes().add(tp_tmp);

				}
			}    		

		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Empty database!");
			alert.setContentText("Careful with the next step!");
			alert.showAndWait();
		}
	}


	@FXML
    void doEdit(ActionEvent event) {
    	idUP.setDisable(false);
    	buttonEdit.setDisable(true);
    	buttonApplyUP.setDisable(false);
    	genderSwitch.setDisable(true);
    }

    @FXML
    void doDelete(ActionEvent event) {
    	idDOWN.setDisable(false);
    	buttonDelete.setDisable(true);
    	buttonApplyDOWN.setDisable(false);
    }

    
    @FXML
    void doApplyEdit(ActionEvent event) {
    	boolean result = false; //true: GOOD, false: BAD
    	int id;
    	try {
    		id = Integer.parseInt(idUP.getText());
    		
    		Person p_original = model.getPerson(id);
    		if(p_original != null){
    			
    		
    		
    		String name = (labelName.getText().equals(p_original.getName()) || labelName.getText().equals(""))? p_original.getName(): labelName.getText();
    		String surname = (labelSurname.getText().equals(p_original.getSurname()) || labelSurname.getText().equals(""))? p_original.getSurname(): labelSurname.getText();
    		String phone = (labelPhone.getText().equals(p_original.getPhone()) || labelPhone.getText().equals(""))? p_original.getPhone(): labelPhone.getText();
    		String birth = (datePicker.getValue()!=null) ? datePicker.getValue().format(DateTimeFormatter.ISO_LOCAL_DATE) : p_original.getBirth();
    		Gender sex = p_original.getSex();
    		//SHOULD ADD --> get gender from switchButton
    		Person p_edited = null;
    		
    		if(tmpPicAbsolutePath==null){
    			//edit also the image
    			p_edited = new Person(name, surname, birth, phone, sex);
    		}else{
    			//not editing the image
    			p_edited = new Person(name, surname, birth, phone, sex, tmpPicAbsolutePath);
    		}
    		
			result = model.editPerson(id,p_edited);
			
    		}
			
			buttonApplyUP.setDisable(true);
			idUP.setDisable(true);
			buttonEdit.setDisable(false);
		} catch (NumberFormatException e) {
			displayMessages.setText("use integers!");
			idUP.setText("");
			buttonApplyUP.setDisable(true);
			idUP.setDisable(true);
			buttonEdit.setDisable(false);
			genderSwitch.setDisable(false);
			return;
		}
    	if(!result){
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("ID ["+id+"] does not exist!");
			alert.setContentText("Careful with the next step!");
			alert.showAndWait();
    	}else{
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("ID ["+id+"]!");
			alert.setContentText("Contact successfully edited!");
			alert.showAndWait();
    	}
    	
    	idUP.setText("");
    	tmpPicAbsolutePath = null;
    	labelName.setText("");
    	labelSurname.setText("");
    	labelPhone.setText("");
    	labelPhone.setText("");
    	datePicker.setValue(null);
    	genderSwitch.setDisable(false);
    }

    @FXML
    void doApplyDelete(ActionEvent event) {
    	try {
			int id = Integer.parseInt(idDOWN.getText());
			if(model.removePerson(id)){
				//succes
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information Dialog");
				alert.setHeaderText("ID ["+id+"]!");
				alert.setContentText("Contact successfully deleted!");
				alert.showAndWait();
				
			}else{
				//not present
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Warning Dialog");
				alert.setHeaderText("The database does not contain ["+id+"]");
				alert.setContentText("Careful with the next step!");
				alert.showAndWait();
			}
		} catch (NumberFormatException e) {
			displayMessages.setText("use integers!");
		}
    	finally
    	{
    		buttonApplyDOWN.setDisable(true);
    		idDOWN.setDisable(true);
    		buttonDelete.setDisable(false);
    		idDOWN.setText("");
		}
    }
    
    @FXML
    void doShowAll(ActionEvent event) {
    	//clean
    	accordionMain.getPanes().clear();
    	
    	if(!model.isEmptyDB()){
//    		System.out.println(model.getContacts().values());
    		
    		ArrayList<String> letters;
    		
    		if(radioName.isSelected()){
    			//BY NAME
    			letters = model.getCurrentLettersNames();
    			Collections.sort(letters);
    			
    			for(String singleLetter : letters){
    				
    				ListView<Button> names = new ListView<>();
    				ArrayList<Button> arrayLabels = new ArrayList<>();
    				//adding all names into a listView and 
    				//add an EventHandler to every element of the listView
    				for(Person p : model.getPersonsNameStartingWith(singleLetter)){
    					Button lab = new Button(p.getName() +" "+ p.getSurname());
    					if(p.getSex().equals(Gender.MALE))
    						lab.setStyle("-fx-base: #BAFCFF;");
    					else
    						lab.setStyle("-fx-base: #FFD1F6;");
    					
    					lab.setMinWidth(150.0);
    					
    					
    					lab.setOnAction(new EventHandler<ActionEvent>() {
    			            @Override
    			            public void handle(ActionEvent event) {
    			                displayProfile(p);
    			            }
    			        });
    					
    					
    					arrayLabels.add(lab);
    					
    				}
    				
    				ObservableList<Button> listOfLabels = FXCollections.observableArrayList(arrayLabels);
    				
    				names.setItems(listOfLabels);
    				//set a TitledPane for each letter adding the listView to a tiltedPane
    				TitledPane tp_tmp = new TitledPane(singleLetter, names);
    				tp_tmp.setPrefHeight(Region.USE_COMPUTED_SIZE);
    				tp_tmp.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
    				
    				accordionMain.getPanes().add(tp_tmp);
    				
    				
    			}
    			
    		}else{
				//BY SURNAME
				letters = model.getCurrentLettersSurnames();
				Collections.sort(letters);
				//copy of byNAME after this

				for(String singleLetter : letters){

					ListView<Button> names = new ListView<>();
					ArrayList<Button> arrayLabels = new ArrayList<>();
					//adding all names into a listView and 
					//add an EventHandler to every element of the listView
					for(Person p : model.getPersonsSurnameStartingWith(singleLetter)){
						Button lab = new Button(p.getSurname() +" "+ p.getName());
						lab.setMinWidth(150.0);
						
						if(p.getSex().equals(Gender.MALE))
    						lab.setStyle("-fx-base: #BAFCFF;");
    					else
    						lab.setStyle("-fx-base: #FFD1F6;");

						lab.setOnAction(new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								displayProfile(p);
							}
						});


						arrayLabels.add(lab);
					}

					ObservableList<Button> listOfLabels = FXCollections.observableArrayList(arrayLabels);

					names.setItems(listOfLabels);
					//set a TitledPane for each letter adding the listView to a tiltedPane
					TitledPane tp_tmp = new TitledPane(singleLetter, names);
					tp_tmp.setPrefHeight(Region.USE_COMPUTED_SIZE);
					tp_tmp.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
					accordionMain.getPanes().add(tp_tmp);

				}
			}    		
    		
    	}
    	else{
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning Dialog");
			alert.setHeaderText("Empty database!");
			alert.setContentText("Careful with the next step!");
			alert.showAndWait();
    	}
    }
    
    
    @FXML
    void doSortByName(ActionEvent event) {
    	doShowAll(new ActionEvent());
    }

    @FXML
    void doSortBySurname(ActionEvent event) {
    	doShowAll(new ActionEvent());
    }
    
    
    public void setModel(ContactsDB model) {
    	this.model = model;	
    }
    
    private void displayProfile(Person p){
    	if(p.hasPhoto()){
//    		System.out.println("ABSOLUTE URL: --> "+ p.getPhotoPath());
    		
    		displayImage.setImage(new Image("file:"+p.getPhotoPath()));
    	}else{
    		if(p.getSex().equals(Gender.MALE))
    			displayImage.setImage(new Image("userMan.png"));
    		else{
    			displayImage.setImage(new Image("userWoman.png"));
    		}
    	}
    	
    	displayID.setText("["+p.getId()+"]");
    	displayBirth.setText(p.getBirth());
    	displayName.setText(p.getName());
    	displaySurname.setText(p.getSurname());
    	displayPhone.setText(p.getPhone());
    }
    
    

    
	@FXML
    void initialize() {
        assert displayPhone != null : "fx:id=\"displayPhone\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert displaySurname != null : "fx:id=\"displaySurname\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert displayName != null : "fx:id=\"displayName\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert idUP != null : "fx:id=\"idUP\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert datePicker != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert labelPhone != null : "fx:id=\"labelPhone\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert idDOWN != null : "fx:id=\"idDOWN\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert displayImage != null : "fx:id=\"displayImage\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert displayBirth != null : "fx:id=\"displayBirth\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert displayMessages != null : "fx:id=\"displayMessages\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert gridPaneUP != null : "fx:id=\"gridPaneUP\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert accordionMain != null : "fx:id=\"accordionMain\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert displayID != null : "fx:id=\"displayID\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert labelName != null : "fx:id=\"labelName\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        assert labelSurname != null : "fx:id=\"labelSurname\" was not injected: check your FXML file 'ContactsFXML.fxml'.";
        
        //copyright
        displayMessages.setText("\u00a9"+" MMM - 2015");
        
        //delete lists
        accordionMain.getPanes().clear();
        
       //disable ID and applies
        idUP.setDisable(true);
        idDOWN.setDisable(true);
        buttonApplyUP.setDisable(true);
        buttonApplyDOWN.setDisable(true);
        
        //radioButtons
        final ToggleGroup radioGroup = new ToggleGroup();
        radioName.setSelected(true);
        radioName.setToggleGroup(radioGroup);
        radioSurname.setToggleGroup(radioGroup);
        
        
        //disable dates after today
        final Callback<DatePicker, DateCell> dayCellFactory =  new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                               
                                if (item.isAfter(
                                        LocalDate.now())
                                    ) {
                                        setDisable(true);
                                        setStyle("-fx-background-color: #ffc0cb;");
                                }   
                        }
                    };
                }
            };
            
        datePicker.setDayCellFactory(dayCellFactory);
           
        
        
        //starting from the top
        scrollPane.setVvalue(0);
        
        
        fileChooser = new FileChooser();
        
        configureFileChooser(fileChooser);
        
        fileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                	tmpPicAbsolutePath = file.getAbsolutePath();
                }
			}
		});
    }

	private static void configureFileChooser(
	        final FileChooser fileChooser) {      
	            fileChooser.setTitle("View Pictures");
	            fileChooser.setInitialDirectory(
	                new File("/Users/Marco/Desktop/Photoshop/pixel") //System.getProperty("user.home"))
	            );                 
	            fileChooser.getExtensionFilters().addAll(
	                new FileChooser.ExtensionFilter("All Images", "*.*"),
	                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
	                new FileChooser.ExtensionFilter("PNG", "*.png")
	            );
	    }
	
	
	public void getPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
		
	}
	
	@SuppressWarnings("unused")
	private void openFile(File file) {
		try {
			desktop.open(file);
		} catch (IOException ex) {
			Logger.getLogger(
					Main.class.getName()).log(
							Level.SEVERE, null, ex
							);
		}
	}


	/**
	 * @param genderSwitch
	 */
	public void getSwitchButton(SwitchButton genderSwitch) {
		this.genderSwitch = genderSwitch;
		
	}
}
