package de.daviddo.program.gui;


import de.daviddo.program.manager.ProgramManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author  Daviddo3399
 */
public class MainGUI extends Application {

    private Parent  root;
    private Scene   scene;
    private Stage   stage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        root    = FXMLLoader.load(getClass().getResource("/javafx/MainGUI.fxml"));
        stage   = primaryStage;
        scene   = new Scene(root);

        stage.setOnCloseRequest(event -> ProgramManager.exit());
        stage.setResizable(false);

        primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));

        primaryStage.setTitle("Handwriting Recognizer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
