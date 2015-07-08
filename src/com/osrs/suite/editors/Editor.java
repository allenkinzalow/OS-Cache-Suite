package com.osrs.suite.editors;

import com.alex.store.Store;
import com.osrs.suite.CacheEnvironment;
import javafx.scene.control.Tab;
import javafx.stage.Stage;

/**
 * Created by Allen Kinzalow on 3/14/2015.
 */
public abstract class Editor {

    protected Tab tab;
    protected Stage stage;
    protected Store store;

    /**
     * Setup the scene of the editor.
     */
    public abstract void setupEditor(Tab tab);

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

}
