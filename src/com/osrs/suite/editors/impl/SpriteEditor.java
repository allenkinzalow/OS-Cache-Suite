package com.osrs.suite.editors.impl;

import com.alex.io.InputStream;
import com.osrs.suite.cache.definitions.SpriteDefinition;
import com.osrs.suite.cache.renderable.RGBSprite;
import com.osrs.suite.editors.Editor;
import com.osrs.suite.ui.InputField;
import com.osrs.suite.utilities.ImageUtilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Created by Allen Kinzalow on 3/15/2015.
 */
public class SpriteEditor extends Editor {

    HashMap<TreeItem<Number>,RGBSprite> spriteMap = new HashMap<TreeItem<Number>,RGBSprite>();

    final InputField maxWidth = new InputField("Max Width: ", 60, 20);
    final InputField maxHeight = new InputField("Max Height: ", 60, 20);
    final InputField offsetX = new InputField("Offset X: ", 60, 20);
    final InputField offsetY = new InputField("Offset Y: ", 60, 20);
    final InputField spriteWidth = new InputField("Sprite Width: ", 60, 20);
    final InputField spriteHeight = new InputField("Sprite Height: ", 60, 20);
    ImageView imageView;
    Image spriteImage;

    @Override
    public void setupEditor(Tab tab) {

        HBox container = new HBox();
        container.setAlignment(Pos.CENTER);
        container.setSpacing(25);
        container.setPadding(new Insets(0, 0, 0, 100));

        System.out.println("Test: " + store.getIndexes()[8].getArchiveId("mod_icons"));

        final TreeItem<Number> treeRoot = new TreeItem<Number>(8);
        int archiveCount = this.store.getIndexes()[SpriteDefinition.INDEX_ID].getValidArchivesCount();
        for(int archive = 0; archive < archiveCount; archive++) {
            TreeItem<Number> archiveRoot = new TreeItem<Number>(archive);
            int fileCount = this.store.getIndexes()[SpriteDefinition.INDEX_ID].getValidFilesCount(archive);
            if(fileCount > 1)
                System.out.println("Archive: " + archive);
            byte[] data = store.getIndexes()[SpriteDefinition.INDEX_ID].getFile(archive,0);
            if(data != null) {
                SpriteDefinition definition = new SpriteDefinition(0);
                definition.decode(new InputStream(data));
                for(int sprite = 0; sprite < SpriteDefinition.paletteChildCount; sprite++) {
                    TreeItem<Number> spriteBranch = new TreeItem<Number>(sprite);
                    RGBSprite rgbSprite = RGBSprite.getRGBSprite(sprite);
                    spriteMap.put(spriteBranch,rgbSprite);
                    archiveRoot.getChildren().add(spriteBranch);
                }
            }
            treeRoot.getChildren().add(archiveRoot);
        }

        final TreeView treeView = new TreeView();
        treeView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<Number>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<Number>> observableValue, TreeItem<Number> old, TreeItem<Number> next) {
                if(next == null || next.getChildren() == null)
                    return;
                if(next.getChildren().size() == 0) {
                    RGBSprite sprite = spriteMap.get(next);
                    if(sprite != null) {
                        updateInputFields(sprite);
                    }
                }
            }
        });
        treeView.setMaxWidth(145);
        treeView.setMaxHeight(430);
        treeView.setShowRoot(true);
        treeView.setRoot(treeRoot);
        treeRoot.setExpanded(true);


        VBox infoContainer = new VBox();
        infoContainer.setPadding(new Insets(100,0,0,0));
        infoContainer.setSpacing(7);

        HBox fieldContainers = new HBox();
        VBox labels = new VBox();
        labels.setPadding(new Insets(10, 0, 0, 50));
        labels.setSpacing(4);
        VBox fields = new VBox();
        fields.setPadding(new Insets(10, 0, 0, 50));

        labels.getChildren().addAll(maxWidth.getLabel(), maxHeight.getLabel(), offsetX.getLabel(), offsetY.getLabel(), spriteWidth.getLabel(), spriteHeight.getLabel());
        fields.getChildren().addAll(maxWidth.getTextField(), maxHeight.getTextField(), offsetX.getTextField(), offsetY.getTextField(), spriteWidth.getTextField(), spriteHeight.getTextField());
        fieldContainers.getChildren().addAll(labels,fields);

        ScrollPane sPane = new ScrollPane();
        sPane.setMaxWidth(345);
        sPane.setMaxHeight(195);
        HBox sBox = new HBox();
        sBox.setAlignment(Pos.CENTER);
        imageView = new ImageView(spriteImage);
        sBox.getChildren().add(imageView);
        sPane.setContent(imageView);

        Button saveAll = new Button("Save All");
        saveAll.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DirectoryChooser dirChooser = new DirectoryChooser();
                File directory = dirChooser.showDialog(getStage());
                if(directory == null)
                    return;
                try {
                    for (TreeItem<Number> archives : treeRoot.getChildren()) {
                        int archiveId = archives.getValue().intValue();
                        for (TreeItem<Number> files : archives.getChildren()) {
                            int fileId = files.getValue().intValue();
                            byte[] data = getStore().getIndexes()[SpriteDefinition.INDEX_ID].getFile(archiveId, fileId);
                            if (data != null) {
                                SpriteDefinition definition = new SpriteDefinition(0);
                                definition.decode(new InputStream(data));
                                for(int spriteIndex = 0; spriteIndex < SpriteDefinition.paletteChildCount; spriteIndex++) {
                                    RGBSprite sprite = RGBSprite.getRGBSprite(spriteIndex);
                                    if(sprite == null || sprite.spriteHeight <= 0 || sprite.spriteWidth <= 0)
                                        continue;
                                    BufferedImage image = new BufferedImage(sprite.spriteWidth, sprite.spriteHeight, BufferedImage.TYPE_INT_RGB);
                                    image.setRGB(0, 0, sprite.spriteWidth, sprite.spriteHeight, sprite.pixels, 0, sprite.spriteWidth);
                                    image = ImageUtilities.makeColorTransparent(image, new Color(0,0,0));
                                    File outFile = new File(directory.getAbsolutePath() + "/" + archiveId + "_" + fileId + ".png");
                                    ImageIO.write(image, "png", outFile);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        infoContainer.getChildren().addAll(fieldContainers,sPane,saveAll);

        container.getChildren().addAll(treeView,infoContainer);
        tab.setContent(container);

    }

    public void updateInputFields(RGBSprite sprite) {
        maxWidth.getTextField().setText(sprite.maxWidth + "");
        maxHeight.getTextField().setText(sprite.maxHeight + "");
        offsetX.getTextField().setText(sprite.offsetX + "");
        offsetY.getTextField().setText(sprite.offsetY + "");
        spriteWidth.getTextField().setText(sprite.spriteWidth + "");
        spriteHeight.getTextField().setText(sprite.spriteHeight + "");
        BufferedImage image = new BufferedImage(sprite.spriteWidth, sprite.spriteHeight, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, sprite.spriteWidth, sprite.spriteHeight, sprite.pixels, 0, sprite.spriteWidth);
        WritableImage wImage = new WritableImage(sprite.spriteWidth, sprite.spriteHeight);
        wImage = SwingFXUtils.toFXImage(image, wImage);
        imageView.setImage(wImage);
    }

}
