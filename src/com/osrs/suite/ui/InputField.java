package com.osrs.suite.ui;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.xml.soap.Text;

/**
 * Created by Allen Kinzalow on 3/14/2015.
 */
public class InputField {

    Label label;
    TextField textField;

    public InputField(String label, int textWidth, int textHeight) {
        this.label = new Label(label);
        this.textField = new TextField();
        textField.setMaxSize(textWidth, textHeight);
    }

    public Label getLabel() {
        return label;
    }

    public TextField getTextField() {
        return textField;
    }

}
