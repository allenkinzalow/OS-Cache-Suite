package com.osrs.suite.editors.impl;

import com.alex.io.InputStream;
import com.osrs.suite.cache.FileRef;
import com.osrs.suite.cache.definitions.SpriteDefinition;
import com.osrs.suite.cache.renderable.RGBSprite;
import com.osrs.suite.editors.Editor;
import com.osrs.suite.ui.LabeledIdentifer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.swing.text.html.ListView;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Created by Allen Kinzalow on 3/15/2015.
 */
public class FileEditor extends Editor {

    private HashMap<TreeItem<Number>,FileRef> fileMap = new HashMap<TreeItem<Number>,FileRef>();
    private FileRef currentRef;

    private LabeledIdentifer indexID = new LabeledIdentifer("Index ID: " , "");
    private LabeledIdentifer archiveID = new LabeledIdentifer("Archive ID: " , "");
    private LabeledIdentifer fileID = new LabeledIdentifer("File ID: " , "");
    private LabeledIdentifer fileSize = new LabeledIdentifer("File Size: " , "");

    @Override
    public void setupEditor(final Tab tab) {

        final FileEditor editor = this;
        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(20);
        container.setPadding(new Insets(50, 0, 50, 0));
        int indexCount = this.store.getIndexes().length;
        final TreeItem<Number> treeRoot = new TreeItem<Number>(indexCount);
        for(int index = 0; index < indexCount; index++) {
            TreeItem<Number> indexRoot = new TreeItem<Number>(index);
            int archiveCount = this.store.getIndexes()[index].getValidArchivesCount();
            for(int archive = 0; archive < archiveCount; archive++) {
                TreeItem<Number> archiveRoot = new TreeItem<Number>(archive);
                int fileCount = this.store.getIndexes()[index].getValidFilesCount(archive);
                for(int file  = 0; file < fileCount; file++) {
                    TreeItem<Number> fileRoot = new TreeItem<Number>(file);
                    FileRef ref = new FileRef(index,archive,file);
                    fileMap.put(fileRoot,ref);
                    archiveRoot.getChildren().add(fileRoot);
                }
                indexRoot.getChildren().add(archiveRoot);
            }
            treeRoot.getChildren().add(indexRoot);
        }

        final Button saveButton = new Button("Save");
        saveButton.setDisable(true);
        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(currentRef != null && !saveButton.isDisabled()) {
                    FileChooser fileChooser = new FileChooser();
                    File saveFile = fileChooser.showSaveDialog(editor.getStage());
                    try {
                        byte[] data = store.getIndexes()[currentRef.getIndexId()].getFile(currentRef.getArchiveId(), currentRef.getFileId());
                        DataOutputStream out = new DataOutputStream(new FileOutputStream(saveFile));
                        out.write(data);
                        out.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        final Button replaceButton = new Button("Replace");
        replaceButton.setDisable(true);
        final Button deleteButton = new Button("Delete");
        deleteButton.setDisable(true);


        final TreeView treeView = new TreeView();
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Number>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Number>> observableValue, TreeItem<Number> old, TreeItem<Number> next) {
                if(next == null || next.getChildren() == null)
                    return;
                if(next.getChildren().size() == 0) {
                    FileRef ref = fileMap.get(next);
                    if(ref != null) {
                        currentRef = ref;
                        System.out.println(ref);
                        indexID.getValue().setText("" + ref.getIndexId());
                        archiveID.getValue().setText("" + ref.getArchiveId());
                        fileID.getValue().setText("" + ref.getFileId());
                        byte[] data = store.getIndexes()[ref.getIndexId()].getFile(ref.getArchiveId(), ref.getFileId());
                        int fileS = data == null ? 0 : data.length;
                        fileSize.getValue().setText("" + fileS);
                        saveButton.setDisable(false);
                        replaceButton.setDisable(false);

                    }
                }
            }
        });
        treeView.setMaxWidth(145);
        treeView.setMaxHeight(430);
        treeView.setShowRoot(true);
        treeView.setRoot(treeRoot);
        treeRoot.setExpanded(true);

        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(indexID.getContainer(),archiveID.getContainer(),fileID.getContainer(), fileSize.getContainer(), saveButton, replaceButton, deleteButton);

        container.getChildren().addAll(treeView,vbox);

        tab.setContent(container);

    }

}
