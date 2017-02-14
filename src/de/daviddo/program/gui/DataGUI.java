package de.daviddo.program.gui;

import de.daviddo.utils.DialogUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 *
 * @author  Daviddo3399
 */
public class DataGUI {

    public DataGUI() {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/javafx/DataGUI.fxml"));
        } catch (IOException e) {
            DialogUtils.showExceptionDialog("Failed to load the DataGUI GUI!", e);
        }
        stage.setScene(new Scene(root));
        stage.show();
    }
}
