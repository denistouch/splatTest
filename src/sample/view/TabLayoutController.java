package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;


public class TabLayoutController {
    @FXML
    private TextField findInDocTextField;
    @FXML
    private TextArea documentViewTextArea;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;

    @FXML
    private void initialize() {
        System.out.println("Tab initialized");

    }
}
