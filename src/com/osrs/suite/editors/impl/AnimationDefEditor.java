package com.osrs.suite.editors.impl;

import com.alex.io.InputStream;
import com.osrs.suite.cache.definitions.AnimationDefinition;
import com.osrs.suite.editors.Editor;
import com.osrs.suite.ui.InputField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.*;

/**
 * Created by Allen Kinzalow on 3/14/2015.
 */
public class AnimationDefEditor extends Editor {

    final HashMap<Integer, AnimationDefinition> animDefMap = new HashMap<Integer, AnimationDefinition>();
    AnimationDefinition current;

    final Label idLabel = new Label("");
    final InputField skeletonLengths = new InputField("Skeleton Lengths: ", 140, 20);
    final InputField skeletonIDs = new InputField("Skeleton IDs: ", 140, 20);
    final InputField loopDelay = new InputField("Loop Delay: ", 140, 20);
    final InputField flowControl = new InputField("Flow Control: ", 140, 20);
    final InputField oneSquare = new InputField("One Square: ", 140, 20);
    final InputField forcedPriority = new InputField("Forced Priority: ", 140, 20);
    final InputField leftHandItem = new InputField("Left Hand Item: ", 140, 20);
    final InputField rightHandItem = new InputField("Right Hand Item: ", 140, 20);
    final InputField skeletonStep = new InputField("Skeleton Step: ", 140, 20);
    final InputField resetWhenWalk = new InputField("Reset When Walk: ", 140, 20);
    final InputField priority = new InputField("Priority: ", 140, 20);
    final InputField delayType = new InputField("Delay Type: ", 140, 20);
    final InputField subSkeletonIDs = new InputField("Sub-Skeleton IDs: ", 140, 20);
    final InputField anIntArray2126 = new InputField("anIntArray2126: ", 140, 20);

    @Override
    public void setupEditor(Tab tab) {

        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(25);
        container.setPadding(new Insets(35, 0, 0, 100));

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        final ListView<Number> defList = new ListView<Number>();
        int listCount = this.store.getIndexes()[AnimationDefinition.INDEX_ID].getValidFilesCount(AnimationDefinition.ARCHIVE_ID);
        for (int number = 0; number < listCount; number++)
            defList.getItems().add(number);
        defList.setPrefWidth(140);
        defList.setPrefHeight(410);
        defList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        final AnimationDefEditor defEditor = this;

        final Label countLabel = new Label("Count: " + listCount);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(2);
        Button addButton = new Button("+");
        addButton.setMaxWidth(30);
        addButton.setMaxHeight(25);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                defList.getItems().add(defList.getItems().size());
                countLabel.setText("Count: " + defList.getItems().size() + "");
            }
        });
        Button deleteButton = new Button("-");
        deleteButton.setMaxWidth(30);
        deleteButton.setMaxHeight(25);
        buttonBox.getChildren().addAll(addButton, deleteButton);
        vbox.getChildren().addAll(countLabel, defList, buttonBox);

        VBox labels = new VBox();
        labels.setPadding(new Insets(50, 0, 0, 0));
        labels.setSpacing(4);
        VBox fields = new VBox();
        fields.setPadding(new Insets(50, 0, 0, 0));
        fields.setPrefWidth(300);
        labels.getChildren().add(new Label("Animation Def ID: "));
        fields.getChildren().add(idLabel);

        labels.getChildren().addAll(skeletonLengths.getLabel(), skeletonIDs.getLabel(), loopDelay.getLabel(), flowControl.getLabel(), oneSquare.getLabel(), forcedPriority.getLabel(),
                leftHandItem.getLabel(), rightHandItem.getLabel(), skeletonStep.getLabel(), resetWhenWalk.getLabel(), priority.getLabel(), delayType.getLabel(), subSkeletonIDs.getLabel(), anIntArray2126.getLabel());
        fields.getChildren().addAll(skeletonLengths.getTextField(), skeletonIDs.getTextField(), loopDelay.getTextField(), flowControl.getTextField(), oneSquare.getTextField(), forcedPriority.getTextField(),
                leftHandItem.getTextField(), rightHandItem.getTextField(), skeletonStep.getTextField(), resetWhenWalk.getTextField(), priority.getTextField(), delayType.getTextField(), subSkeletonIDs.getTextField(), anIntArray2126.getTextField());

        defList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int key = defList.getSelectionModel().getSelectedItem().intValue();
                if (animDefMap.containsKey(key))
                    defEditor.setCurrentAnimationDef(animDefMap.get(key));
                else {
                    byte[] defData = defEditor.getStore().getIndexes()[AnimationDefinition.INDEX_ID].getFile(AnimationDefinition.ARCHIVE_ID, key);
                    if (defData != null) {
                        AnimationDefinition def = new AnimationDefinition(key);
                        def.decode(new InputStream(defData));
                        animDefMap.put(key, def);
                        defEditor.setCurrentAnimationDef(def);
                        idLabel.setText(key + "");
                    }
                }
            }
        });

        VBox buttons = new VBox();
        buttons.setPadding(new Insets(10,0,10,0));
        buttons.setSpacing(3);
        Button save = new Button("Save");
        Button reset = new Button("Reset");
        buttons.getChildren().addAll(save,reset);
        fields.getChildren().add(buttons);

        container.getChildren().addAll(vbox, labels, fields);
        tab.setContent(container);
        tab.getContent().setLayoutX(200);
    }

    public void updateInputFields(AnimationDefinition definition) {
        String skeletonLengths = "";
        if(definition.skeletonLengths != null)
            for (int i : definition.skeletonLengths)
                skeletonLengths += i + ",";
        this.skeletonLengths.getTextField().setText(skeletonLengths);
        String skeletonIDsText = "";
        if(definition.skeletonIDs != null)
            for (int i : definition.skeletonIDs)
                skeletonIDsText += i + ",";
        skeletonIDs.getTextField().setText(skeletonIDsText);
        loopDelay.getTextField().setText(definition.loopDelay + "");
        String flowControlText = "";
        if(definition.animationFlowControl != null)
            for (int i : definition.animationFlowControl)
                flowControlText += i + ",";
        flowControl.getTextField().setText(flowControlText);
        oneSquare.getTextField().setText(definition.oneSquareAnimation + "");
        forcedPriority.getTextField().setText(definition.forcedPriority + "");
        leftHandItem.getTextField().setText(definition.leftHandItem + "");
        rightHandItem.getTextField().setText(definition.rightHandItem + "");
        skeletonStep.getTextField().setText(definition.skeletonStep + "");
        resetWhenWalk.getTextField().setText(definition.resetWhenWalk + "");
        priority.getTextField().setText(definition.priority + "");
        delayType.getTextField().setText(definition.delayType + "");
        String subSkeletonIDsText = "";
        if(definition.subSkeletonIDs != null)
            for (int i : definition.subSkeletonIDs)
                subSkeletonIDsText += i + ",";
        subSkeletonIDs.getTextField().setText(subSkeletonIDsText);
        String anIntArray2126Text = "";
        if(definition.anIntArray2126 != null)
            for (int i : definition.anIntArray2126)
                anIntArray2126Text += i + ",";
        anIntArray2126.getTextField().setText(anIntArray2126Text);
    }

    public void setCurrentAnimationDef(AnimationDefinition def) {
        this.current = def;
        this.updateInputFields(def);
    }
}

