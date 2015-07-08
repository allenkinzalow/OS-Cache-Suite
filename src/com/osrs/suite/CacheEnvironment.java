package com.osrs.suite;

import com.alex.store.Store;
import com.osrs.suite.editors.Editor;
import com.osrs.suite.editors.EditorDefinition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Allen Kinzalow on 3/14/2015.
 */
public class CacheEnvironment {

    Stage stage;
    Store store;
    File directory;
    Tab suiteTab;
    TabPane environmentPane;
    HashMap<EditorDefinition,Editor> editors = new HashMap<EditorDefinition, Editor>();


    public CacheEnvironment(File file, Tab tab, Stage stage) {
        try {
            this.directory = file;
            this.suiteTab = tab;
            this.store = new Store(file.getAbsolutePath() + "/");
            this.environmentPane = new TabPane();
            this.environmentPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
            this.environmentPane.setPrefWidth(SuiteConstants.WIDTH);
            this.environmentPane.setPrefHeight(SuiteConstants.HEIGHT);
            this.setupEnvironment();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setup our cache environment.
     */
    private void setupEnvironment() {
        StackPane pane = new StackPane();
        Tab toolTab = new Tab("Tools");
        toolTab.setClosable(false);
        setupTools(toolTab);
        this.environmentPane.getTabs().add(toolTab);
        pane.getChildren().add(this.environmentPane);
        this.suiteTab.setContent(pane);
    }

    /**
     * Setup the tools tab.
     * @param toolTab
     */
    private void setupTools(Tab toolTab) {
        FlowPane flow = new FlowPane();
        flow.setAlignment(Pos.CENTER);
        flow.setHgap(5);
        flow.setVgap(5);
        for(final EditorDefinition definition : EditorDefinition.values()) {
            final Button button = new Button(definition.getName());
            button.setMinWidth(150);
            button.setMinHeight(50);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(!editors.containsKey(definition))
                        addEditor(definition);
                }
            });
            flow.getChildren().add(button);
        }
        toolTab.setContent(flow);
    }

    /**
     * Add an editor to the cache environment.
     * @param definition
     */
    public void addEditor(final EditorDefinition definition) {
        try {
            Tab editorTab = new Tab(definition.getName());
            editorTab.setOnClosed(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    removeEditor(definition);
                }
            });
            Editor editor = (Editor) definition.getEditor().newInstance();
            editor.setTab(editorTab);
            editor.setStage(stage);
            editor.setStore(store);
            editor.setupEditor(editorTab);
            editors.put(definition,editor);
            this.environmentPane.getTabs().add(editorTab);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove an editor from the cache environment.
     * @param definition
     */
    public void removeEditor(EditorDefinition definition) {
        editors.remove(definition);
    }

    /**
     * Get the current tab reference.
     * @return
     */
    public Tab getSuiteTab() {
        return this.suiteTab;
    }

}
