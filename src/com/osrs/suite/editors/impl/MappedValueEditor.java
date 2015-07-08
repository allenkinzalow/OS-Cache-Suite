package com.osrs.suite.editors.impl;

import com.alex.io.InputStream;
import com.osrs.suite.cache.definitions.MappedValueDefinition;
import com.osrs.suite.cache.definitions.SpriteDefinition;
import com.osrs.suite.cache.renderable.RGBSprite;
import com.osrs.suite.editors.Editor;
import com.osrs.suite.ui.InputField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;

import java.util.HashMap;

/**
 * Created by Allen Kinzalow on 6/18/2015.
 */
public class MappedValueEditor extends Editor {

    HashMap<TreeItem<Number>,MappedValueDefinition> valueMap = new HashMap<>();

    final InputField mapKey = new InputField("Map Key: ", 60, 20);
    final InputField mapValue = new InputField("Map Value: ", 60, 20);

    @Override
    public void setupEditor(Tab tab) {
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(25);
        container.setPadding(new Insets(0, 0, 0, 100));

        //System.out.println("Test: " + store.getIndexes()[8].getArchiveId("mod_icons"));

        final TreeItem<Number> treeRoot = new TreeItem<Number>(8);
        int mapValueCount = this.store.getIndexes()[MappedValueDefinition.INDEX_ID].getValidFilesCount(MappedValueDefinition.ARCHIVE_ID);
        for(int mapValue = 0; mapValue < mapValueCount; mapValue++) {
            TreeItem<Number> valueRoot = new TreeItem<Number>(mapValue);
            byte[] data = store.getIndexes()[MappedValueDefinition.INDEX_ID].getFile(MappedValueDefinition.ARCHIVE_ID,mapValue);
            if(data != null) {
                MappedValueDefinition mappedValueDefinition = new MappedValueDefinition(mapValue);
                mappedValueDefinition.decode(new InputStream(data));
                mappedValueDefinition.printDefinition();
                for(int valueIndex = 0; valueIndex < mappedValueDefinition.csMapValueCount; valueIndex++) {
                    TreeItem<Number> valueBranch = new TreeItem<Number>(valueIndex);
                    valueMap.put(valueBranch,mappedValueDefinition);
                    valueRoot.getChildren().add(valueBranch);
                }
            }
            treeRoot.getChildren().add(valueRoot);
        }

        final TreeView treeView = new TreeView();
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Number>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Number>> observableValue, TreeItem<Number> old, TreeItem<Number> next) {
                if(next == null || next.getChildren() == null)
                    return;
                if(next.getChildren().size() == 0) {
                    MappedValueDefinition def = valueMap.get(next);
                    if(def != null) {
                        int value = next.getValue().intValue();
                        mapKey.getTextField().setText(def.csMapIdentifier[value] + "");
                        mapValue.getTextField().setText((def.csMapStringValues != null ? def.csMapStringValues[value] : def.csMapIntValues[value]) + "");
                    }
                }
            }
        });
        treeView.setMaxWidth(145);
        treeView.setMaxHeight(430);
        treeView.setShowRoot(true);
        treeView.setRoot(treeRoot);
        treeRoot.setExpanded(true);
    }

}
