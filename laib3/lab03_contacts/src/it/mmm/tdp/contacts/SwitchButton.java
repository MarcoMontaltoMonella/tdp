package it.mmm.tdp.contacts;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;

public class SwitchButton extends Label
{
    private SimpleBooleanProperty switchedOn = new SimpleBooleanProperty(true);

    public SwitchButton()
    {
        Button switchBtn = new Button();
        switchBtn.setPrefWidth(30);
        switchBtn.setPadding(new Insets(5, 15, 5, 15));
        switchBtn.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent t)
            {
                switchedOn.set(!switchedOn.get());
            }
        });

        setGraphic(switchBtn);

        switchedOn.addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov,
                Boolean M, Boolean F)
            {
                if (M)
                {
                    setText("  M  ");
                    setStyle("-fx-background-color: blue;-fx-text-fill:white;");
                    setContentDisplay(ContentDisplay.RIGHT);
                }
                else
                {
                    setText("  F  ");
                    setStyle("-fx-background-color: pink;-fx-text-fill:white;");
                    setContentDisplay(ContentDisplay.LEFT);
                }
            }
        });

        switchedOn.set(false);
    }

    
    
    public SimpleBooleanProperty switchOnProperty() { return switchedOn; }
}