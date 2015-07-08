package com.osrs.suite.ui;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Created by Allen Kinzalow on 3/21/2015.
 */
public class LabeledIdentifer {

    private Label identifer;
    private Label value;

    public LabeledIdentifer(String id, String val) {
        this.identifer = new Label(id);
        this.value = new Label(val);
    }

    public Label getIdentifer() {
        return identifer;
    }

    public Label getValue() {
        return value;
    }

    public HBox getContainer() {
        HBox container = new HBox();
        container.setSpacing(7);
        container.getChildren().addAll(identifer,value);
        return container;
    }

}
