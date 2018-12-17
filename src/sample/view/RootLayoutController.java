package sample.view;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import sample.Main;
import sample.utilities.PathItem;
import sample.utilities.PathTreeItem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class RootLayoutController {
    @FXML
    private Button openButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private ChoiceBox extensionChoiceBox;
    @FXML
    private TreeView<PathItem> directoryTreeView;
    @FXML
    private TabPane documentTabPane;
    @FXML
    private Label fileNameLabel;
    @FXML
    private Label countMatchesLabel;
    @FXML
    private ProgressBar indicator;
    private Main main;

    private float indicatorValue = 0.00f;

    public RootLayoutController() {
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void initialize() {
        documentTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        indicator.setVisible(true);
        loading.start();
        extensionChoiceBox.getItems().addAll(".log", ".txt", ".java");
        extensionChoiceBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void setOnOpenHandler() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Please select a root directory.");
        File file = directoryChooser.showDialog(main.getStage());
        loading.start();
        if (file == null)
            return;
        Path path = file.toPath();

        directoryTreeView.setRoot(createTreeItem(new PathItem(path)));
        directoryTreeView.getRoot().setExpanded(true);
        directoryTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (directoryTreeView.getSelectionModel().getSelectedItem()==null)
                    return;
                fileNameLabel.setText(directoryTreeView.getSelectionModel().getSelectedItem().getValue().toString());
                if (event.getClickCount() == 2)
                    createTab();
            }
        });
        loading.stop();
    }

    private TreeItem<PathItem> createTreeItem(PathItem pathItem) {
        return PathTreeItem.createTreeItem(pathItem);
    }

    protected AnimationTimer loading = new AnimationTimer() {
        @Override
        public void handle(long now) {
            indicatorAnimation();
        }
    };

    private void indicatorAnimation() {

    }

    private void createTab() {
        try {
            Tab tab = new Tab(directoryTreeView.getTreeItem(directoryTreeView.getSelectionModel().getSelectedIndex()).getValue().toString());
            tab.setClosable(true);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(main.getClass().getResource("view/TabLayoutController.fxml"));
            BorderPane borderPane = loader.load();
            borderPane.setPrefWidth(documentTabPane.getWidth()-5);
            borderPane.setPrefHeight(documentTabPane.getHeight()-30);
            tab.setContent(borderPane);
            documentTabPane.getTabs().add(tab);
            System.out.println("Tab was created.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    public void loaded() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Good news!");
        alert.setHeaderText(getClass().getSimpleName() + " successful loaded!");
        alert.setContentText("From: " + main.getClass().getSimpleName());
        alert.showAndWait();
        loading.stop();
        indicator.setVisible(false);
    }
}
