package com.osrs.suite;

import com.osrs.suite.utilities.GamepackDownloader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Allen Kinzalow on 3/13/2015.
 */
public class CacheSuite extends Application {

    MenuBar bar;
    TabPane tabPane;
    DirectoryChooser cacheChooser;
    ArrayList<CacheEnvironment> environments;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        environments = new ArrayList<CacheEnvironment>();
        stage.setTitle("OSRS Cache Suite " + SuiteConstants.VERSION + "." + SuiteConstants.SUB_VERSION);
        stage.setResizable(false);
        Group rootGroup = new Group();
        Scene scene = new Scene(rootGroup, SuiteConstants.WIDTH, SuiteConstants.HEIGHT);

        cacheChooser = new DirectoryChooser();

        VBox vertical = new VBox();
        this.setupMenuBar(stage);
        vertical.getChildren().add(bar);
        setupTabPane(stage);
        vertical.getChildren().add(tabPane);
        rootGroup.getChildren().add(vertical);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Setup the tab panel.
     * @param stage
     */
    private void setupTabPane(Stage stage) {
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);

        Tab info = new Tab("FAQ");
        info.setClosable(false);
        tabPane.getTabs().addAll(info);
        tabPane.prefWidthProperty().bind(stage.widthProperty());
        tabPane.prefWidthProperty().bind(stage.widthProperty());
        System.out.println("Tab Pane: " + tabPane.getWidth() + "," + tabPane.getHeight());
    }

    /**
     * Setup the menu bar.
     * @param stage
     */
    private void setupMenuBar(final Stage stage) {
        bar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem file_open = new MenuItem("Open...");
        file_open.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                File file = cacheChooser.showDialog(stage);
                if(file == null)
                    return;
                System.out.println("Dir: " + file.getAbsolutePath());
                addEnvironment(file, stage);
            }
        });
        MenuItem download_client = new MenuItem("Download Client");
        download_client.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                GamepackDownloader downloader = new GamepackDownloader();
                downloader.download();
            }
        });
        file.getItems().addAll(file_open, download_client);
        bar.getMenus().add(file);
        bar.prefWidthProperty().bind(stage.widthProperty());
    }

    /**
     * Add a cache environment to our suite.
     * @param file
     */
    private void addEnvironment(File file, Stage stage) {
        String environmentName = file.getAbsolutePath().substring(file.getAbsolutePath().lastIndexOf('\\'));
        final Tab envTab = new Tab(environmentName);
        final CacheEnvironment environment = new CacheEnvironment(file,envTab,stage);
        envTab.setClosable(true);
        final CacheSuite suite = this;
        envTab.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                suite.removeEnvironment(environment);
                tabPane.getTabs().remove(envTab);
            }
        });
        environments.add(environment);
        tabPane.getTabs().add(envTab);
    }

    /**
     * Remove a cache environment from our suite.
     * @param environment
     */
    private void removeEnvironment(CacheEnvironment environment) {
        environments.remove(environment);
    }

}
