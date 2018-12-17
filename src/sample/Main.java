package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.view.RootLayoutController;

public class Main extends Application {
    private double width = 800;
    private double height = 600;
    private Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/RootLayoutController.fxml"));
        Parent parent = loader.load();
        RootLayoutController rootLayoutController = loader.getController();
        Scene scene = new Scene(parent,width,height);
        primaryStage.setTitle(getClass().getName());
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(400);
        primaryStage.setMinHeight(350);
        primaryStage.show();
        rootLayoutController.setMain(this);
        rootLayoutController.loaded();
    }

    public Stage getStage(){
        return stage;
    }



    public static void main(String[] args) {
        launch(args);
    }
}
