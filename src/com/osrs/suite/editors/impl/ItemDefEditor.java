package com.osrs.suite.editors.impl;

import com.alex.io.InputStream;
import com.osrs.suite.cache.definitions.AnimationDefinition;
import com.osrs.suite.cache.definitions.ItemDefinition;
import com.osrs.suite.editors.Editor;
import com.osrs.suite.ui.InputField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;

/**
 * Created by Allen Kinzalow on 3/14/2015.
 */
public class ItemDefEditor extends Editor {

    final HashMap<Integer, ItemDefinition> itemDefMap = new HashMap<Integer, ItemDefinition>();
    ItemDefinition current;

    final Label idLabel = new Label("");
    final InputField inventoryModel = new InputField("Inv/Ground Model: ", 140, 20);
    final InputField itemName = new InputField("Name: ", 140, 20);
    final InputField zoom2d = new InputField("Zoom2d: ", 140, 20);
    final InputField xrot2d = new InputField("XRotation2d: ", 140, 20);
    final InputField yrot2d = new InputField("YRotation2d: ", 140, 20);
    final InputField xOffset2d = new InputField("XOffset2d: ", 140, 20);
    final InputField yOffset2d = new InputField("YOffset2d: ", 140, 20);
    final InputField itemStackable = new InputField("Stackable: ", 140, 20);
    final InputField itemCost = new InputField("Cost: ", 140, 20);
    final InputField itemMembers = new InputField("Members: ", 140, 20);
    final InputField maleModel0 = new InputField("Male Model 0: ", 140, 20);
    final InputField maleOffset = new InputField("Male Offset: ", 140, 20);
    final InputField maleModel1 = new InputField("Male Model 1: ", 140, 20);
    final InputField femaleModel0 = new InputField("Female Model 0: ", 140, 20);
    final InputField femaleOffset = new InputField("Female Offset: ", 140, 20);
    final InputField femaleModel1 = new InputField("Female Model 1: ", 140, 20);
    final InputField groundOptions = new InputField("Ground Options: ", 140, 20);
    final InputField interOptions = new InputField("Interface Options: ", 140, 20);
    final InputField colorFind = new InputField("Color Search: ", 140, 20);
    final InputField colorReplace = new InputField("Color Replace: ", 140, 20);
    final InputField textureFind = new InputField("Texture Search: ", 140, 20);
    final InputField textureReplace = new InputField("Texture Replace: ", 140, 20);
    final InputField maleModel2 = new InputField("Male Model 2: ", 140, 20);
    final InputField femaleModel2 = new InputField("femaleModel2: ", 140, 20);
    final InputField maleHeadModel = new InputField("Male Head Model 0: ", 140, 20);
    final InputField femaleHeadModel = new InputField("Female Head Model 0: ", 140, 20);
    final InputField maleHeadModel1 = new InputField("Male Head Model 1: ", 140, 20);
    final InputField femaleHeadModel1 = new InputField("Female Head Model 1: ", 140, 20);
    final InputField zrot2d = new InputField("ZRotation2d: ", 140, 20);
    final InputField notedID = new InputField("Noted ID: ", 140, 20);
    final InputField notedTemplate = new InputField("Noted Template: ", 140, 20);
    final InputField newCountID = new InputField("Count ID's: ", 140, 20);
    final InputField newCountAmount = new InputField("Count Amounts: ", 140, 20);
    final InputField resizeX = new InputField("ResizeX: ", 140, 20);
    final InputField resizeY = new InputField("ResizeY: ", 140, 20);
    final InputField resizeZ = new InputField("ResizeZ: ", 140, 20);
    final InputField ambient = new InputField("Ambient: ", 140, 20);
    final InputField contrast = new InputField("Constrast: ", 140, 20);
    final InputField team = new InputField("Team: ", 140, 20);


    @Override
    public void setupEditor(Tab tab) {

        final ItemDefEditor defEditor = this;
        VBox outsideContainer = new VBox();
        outsideContainer.setAlignment(Pos.CENTER);

        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(25);
        container.setPadding(new Insets(0, 0, 0, 100));

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        final ListView<Number> defList = new ListView<Number>();
        int listCount = this.store.getIndexes()[ItemDefinition.INDEX_ID].getValidFilesCount(ItemDefinition.ARCHIVE_ID);
        for (int number = 0; number < listCount; number++)
            defList.getItems().add(number);
        defList.setPrefWidth(140);
        defList.setPrefHeight(410);
        defList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
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

        ScrollPane sPane = new ScrollPane();
        sPane.setPrefWidth(400);
        sPane.setMaxHeight(400);
        sPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        HBox scrollHBox = new HBox();
        scrollHBox.setAlignment(Pos.CENTER);
        VBox labels = new VBox();
        labels.setPadding(new Insets(10, 0, 0, 50));
        labels.setSpacing(4);
        VBox fields = new VBox();
        fields.setPadding(new Insets(10, 0, 0, 50));
        //fields.setPrefWidth(300);
        labels.getChildren().add(new Label("Item Def ID: "));
        fields.getChildren().add(idLabel);

        labels.getChildren().addAll(inventoryModel.getLabel(), itemName.getLabel(), zoom2d.getLabel(), xrot2d.getLabel(), yrot2d.getLabel(), xOffset2d.getLabel(),
                yOffset2d.getLabel(), itemStackable.getLabel(), itemCost.getLabel(), itemMembers.getLabel(), maleModel0.getLabel(), maleOffset.getLabel(), maleModel1.getLabel(), femaleModel0.getLabel(),
                femaleOffset.getLabel(),femaleModel1.getLabel(),groundOptions.getLabel(),interOptions.getLabel(),colorFind.getLabel(),colorReplace.getLabel(),textureFind.getLabel(), textureReplace.getLabel(),
                maleModel2.getLabel(),femaleModel2.getLabel(),maleHeadModel.getLabel(),femaleHeadModel.getLabel(),maleHeadModel1.getLabel(),femaleHeadModel1.getLabel(),zrot2d.getLabel(),notedID.getLabel(),
                notedTemplate.getLabel(),newCountID.getLabel(),newCountAmount.getLabel(),resizeX.getLabel(),resizeY.getLabel(),resizeZ.getLabel(),ambient.getLabel(),contrast.getLabel(),team.getLabel());
        fields.getChildren().addAll(inventoryModel.getTextField(), itemName.getTextField(), zoom2d.getTextField(), xrot2d.getTextField(), yrot2d.getTextField(), xOffset2d.getTextField(),
                yOffset2d.getTextField(), itemStackable.getTextField(), itemCost.getTextField(), itemMembers.getTextField(), maleModel0.getTextField(), maleOffset.getTextField(), maleModel1.getTextField(), femaleModel0.getTextField(),
                femaleOffset.getTextField(),femaleModel1.getTextField(),groundOptions.getTextField(),interOptions.getTextField(),colorFind.getTextField(),colorReplace.getTextField(),textureFind.getTextField(), textureReplace.getTextField(),
                maleModel2.getTextField(),femaleModel2.getTextField(),maleHeadModel.getTextField(),femaleHeadModel.getTextField(),maleHeadModel1.getTextField(),femaleHeadModel1.getTextField(),zrot2d.getTextField(),notedID.getTextField(),
                notedTemplate.getTextField(),newCountID.getTextField(),newCountAmount.getTextField(),resizeX.getTextField(),resizeY.getTextField(),resizeZ.getTextField(),ambient.getTextField(),contrast.getTextField(),team.getTextField());

        defList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                int key = defList.getSelectionModel().getSelectedItem().intValue();
                if (itemDefMap.containsKey(key))
                    defEditor.setCurrentItemDef(itemDefMap.get(key));
                else {
                    byte[] defData = defEditor.getStore().getIndexes()[ItemDefinition.INDEX_ID].getFile(ItemDefinition.ARCHIVE_ID, key);
                    if (defData != null) {
                        ItemDefinition def = new ItemDefinition(key);
                        def.decode(new InputStream(defData));
                        itemDefMap.put(key, def);
                        defEditor.setCurrentItemDef(def);
                        idLabel.setText("" + key);
                    }
                }
            }
        });

        HBox inputH = new HBox();
        inputH.setAlignment(Pos.CENTER);
        inputH.setSpacing(10);
        final InputField search = new InputField("Item Search: ", 140, 20);
        Button searchButton = new Button("Search");
        searchButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getSource() == defList)
                    System.out.println("true - ");
                System.out.println(mouseEvent.getButton().toString());
                String name = search.getTextField().getText();
                int foundIndex = -1;
                for(int index = 0; index < defEditor.getStore().getIndexes()[ItemDefinition.INDEX_ID].getValidFilesCount(ItemDefinition.ARCHIVE_ID); index++) {
                    ItemDefinition definition = new ItemDefinition(index);
                    definition.decode(new InputStream(defEditor.getStore().getIndexes()[ItemDefinition.INDEX_ID].getFile(ItemDefinition.ARCHIVE_ID,index)));
                    if(definition.name.equalsIgnoreCase(name)) {
                        foundIndex = index;
                        System.out.println("Found index: " + foundIndex);
                        itemDefMap.put(foundIndex, definition);
                        defEditor.setCurrentItemDef(definition);
                        idLabel.setText("" + foundIndex);
                        defList.getSelectionModel().select(foundIndex);
                        defList.scrollTo(foundIndex);
                        break;
                    }
                }
            }
        });
        inputH.getChildren().addAll(search.getLabel(), search.getTextField(), searchButton);

        VBox buttons = new VBox();
        buttons.setPadding(new Insets(10,0,10,0));
        buttons.setSpacing(3);
        Button save = new Button("Save");
        Button reset = new Button("Reset");
        buttons.getChildren().addAll(save,reset);
        fields.getChildren().add(buttons);

        scrollHBox.getChildren().addAll(labels, fields);
        sPane.setContent(scrollHBox);

        container.getChildren().addAll(vbox,sPane);

        outsideContainer.getChildren().addAll(inputH, container);

        tab.setContent(outsideContainer);

    }

    public void setCurrentItemDef(ItemDefinition current) {
        this.current = current;
        this.updateInputFields(current);
    }

    public void updateInputFields(ItemDefinition current) {
        inventoryModel.getTextField().setText(current.inventoryModel + "");
        itemName.getTextField().setText(current.name + "");
        zoom2d.getTextField().setText(current.zoom2d + "");
        xrot2d.getTextField().setText(current.xan2d + "");
        yrot2d.getTextField().setText(current.yan2d + "");
        xOffset2d.getTextField().setText(current.xOffset2d + "");
        yOffset2d.getTextField().setText(current.yOffset2d + "");
        itemStackable.getTextField().setText(current.stackable + "");
        itemCost.getTextField().setText(current.cost + "");
        itemMembers.getTextField().setText(current.members + "");
        maleModel0.getTextField().setText(current.maleModel0 + "");
        maleOffset.getTextField().setText(current.maleOffset + "");
        maleModel1.getTextField().setText(current.maleModel1 + "");
        femaleModel0.getTextField().setText(current.femaleModel0 + "");
        femaleOffset.getTextField().setText(current.femaleOffset + "");
        femaleModel1.getTextField().setText(current.femaleModel1 + "");
        if(current.options != null) {
            String groundOptionsString = "";
            for(String option : current.options)
                groundOptionsString += option + ",";
            groundOptions.getTextField().setText(groundOptionsString);
        }
        if(current.options != null) {
            String interfaceOptionsString = "";
            for(String option : current.interfaceOptions)
                interfaceOptionsString += option + ",";
            interOptions.getTextField().setText(interfaceOptionsString);
        }
        if(current.colorFind != null) {
            String colorString = "";
            for(int color : current.colorFind)
                colorString += color + ",";
            colorFind.getTextField().setText(colorString);
        }
        if(current.colorReplace != null) {
            String colorString = "";
            for(int color : current.colorReplace)
                colorString += color + ",";
            colorReplace.getTextField().setText(colorString);
        }
        if(current.textureFind != null) {
            String textureString = "";
            for(int texture : current.textureFind)
                textureString += texture + ",";
            textureFind.getTextField().setText(textureString);
        }
        if(current.textureReplace != null) {
            String textureString = "";
            for(int texture : current.textureReplace)
                textureString += texture + ",";
            textureReplace.getTextField().setText(textureString);
        }
        maleModel2.getTextField().setText(current.maleModel2 + "");
        femaleModel2.getTextField().setText(current.femaleModel2 + "");
        maleHeadModel.getTextField().setText(current.maleHeadModel + "");
        femaleHeadModel.getTextField().setText(current.femaleHeadModel + "");
        maleHeadModel1.getTextField().setText(current.maleHeadModel2 + "");
        femaleHeadModel1.getTextField().setText(current.femaleHeadModel2 + "");
        zrot2d.getTextField().setText(current.zan2d + "");
        notedID.getTextField().setText(current.notedID + "");
        notedTemplate.getTextField().setText(current.notedTemplate + "");
        newCountID.getTextField().setText(current.countObj + "");
        newCountAmount.getTextField().setText(current.countCo + "");
        resizeX.getTextField().setText(current.resizeX + "");
        resizeY.getTextField().setText(current.resizeY + "");
        resizeZ.getTextField().setText(current.resizeZ + "");
        ambient.getTextField().setText(current.ambient + "");
        contrast.getTextField().setText(current.contrast + "");
        team.getTextField().setText(current.team + "");
    }

}
