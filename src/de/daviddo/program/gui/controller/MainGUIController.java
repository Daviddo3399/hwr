package de.daviddo.program.gui.controller;

import de.daviddo.knn.network.NetworkFactory;
import de.daviddo.knn.network.NetworkTrainer;
import de.daviddo.knn.network.training.Outputs;
import de.daviddo.knn.network.training.Training;
import de.daviddo.knn.network.training.TrainingLoader;
import de.daviddo.knn.solution.Solution;
import de.daviddo.program.gui.DataGUI;
import de.daviddo.program.manager.DataManager;
import de.daviddo.program.manager.ProgramManager;
import de.daviddo.program.manager.SettingsManager;
import de.daviddo.utils.DialogUtils;
import de.daviddo.utils.Utils;
import de.daviddo.utils.gui.DrawablePane;
import de.daviddo.utils.logging.Logger;
import de.daviddo.utils.logging.LoggerLevel;
import de.daviddo.utils.settings.Settings;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static de.daviddo.program.manager.ProgramManager.PATHSEPARATOR;
import static de.daviddo.utils.Messages.*;


/**
 *
 * @author  Daviddo3399
 */
public class MainGUIController implements Initializable {

    private static  DrawablePane            drawablePane;
    private         NetworkTrainer          networkTrainer;
    private         SwingNode               swingNode;

    private         String                  currentSolution = null;

    @FXML           VBox                    vBox;

    @FXML           Accordion               accordion;
    @FXML           Pane                    drawPane;
    @FXML           TitledPane              trainingPane;

    @FXML           Label                   solutionLabel;
    @FXML           Label                   solutionPercentLabel;

    @FXML           ListView<String>        topSolutionListView;
    @FXML           TableView<Solution>     fullListTableView;

    @FXML           ComboBox<String>        trainCharComboBox;
    @FXML           RadioButton             automaticSaveLoadRadioButton;

    @FXML           Button                  recognizeButton;
    @FXML           Button                  trainButton;
    @FXML           Button                  trainXTimesButton;
    @FXML           Button                  wrongHitButton;
    @FXML           Button                  saveButton;
    @FXML           Button                  loadButton;
    @FXML           Button                  showDataGUIButton;

    @FXML           TextField               trainXTimesTextField;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        networkTrainer  = ProgramManager.getNetworkTrainer();
        swingNode       = new SwingNode();

        accordion.setExpandedPane(trainingPane);

        createSwingContent();

        setupRightSide();
        setupLeftSide();
        setupComboBoxes();

        if (Settings.automaticSaveALoad) {
            automaticSaveLoadRadioButton.setSelected(true);
        } else {
            automaticSaveLoadRadioButton.setSelected(false);
        }

        Logger.log(MANAGER_PROGRAM_LOADING_FINISHED, LoggerLevel.FINISHED);
        Logger.log(MANAGER_PROGRAM_LOADING_FINISHED2);
    }

    @FXML
    private void handleRecognizeButton() {
        if (drawablePane.isPanelClear()) {
            DialogUtils.showWarningDialog(GUI_PANE_EMPTY_SHORT, GUI_PANE_EMPTY_LONG);
            return;
        }

        networkTrainer.setInputs(drawablePane.getPixelsAsList());

        Solution solution = networkTrainer.getNetwork().recognize(networkTrainer.getOutputs());
        updateSolution(solution);

        networkTrainer.getSolutionManager().updateOutputs(networkTrainer.getOutputs());

        ProgramManager.getDataManager().increaseRecognizedTotal();
        ProgramManager.getDataManager().increaseRecognized(solution);

        DataManager.updateData("knn.recognizedTotal", ProgramManager.getDataManager().getRecognizedTotal());
        DataManager.updateData("knn.mistakesTotal", ProgramManager.getDataManager().getMistakesTotal());
        DataManager.updateData("knn." + solution.getCharacter() + ".recognized", solution.getRecognized());

        currentSolution = solution.getCharacter();

        updateTop3();
        updateCharTable(networkTrainer.getSolutionManager().getSolutions());
    }

    @FXML
    private void handleTrainXTimesButton() {
        Object o = trainXTimesTextField.getText();
        if (String.valueOf(o).isEmpty() || Utils.isNumeric(o)) {
            DialogUtils.showWarningDialog(GUI_NOT_NUMERIC_SHORT, GUI_NOT_NUMERIC_LONG);
            return;
        } else {
            networkTrainer.train(Long.valueOf(String.valueOf(o)));
        }
    }

    @FXML
    private void handleTrainButton() {
        train();
    }

    @FXML
    private void handleWrongHitButton() {
        String correct = DialogUtils.showChoiceCharacterDialog();
        if (!currentSolution.equals(correct)) {
            Logger.log(KNN_IMPROVE_SOLUTION, LoggerLevel.INFO);
            trainCharComboBox.getSelectionModel().select(Utils.getIndex(correct));
            train();
            Logger.log(KNN_IMPROVE_SOLUTION_FINISHED, LoggerLevel.INFO);

            Solution solution       = networkTrainer.getSolutionManager().getSolution(currentSolution);
            Solution newSolution    = networkTrainer.getSolutionManager().getSolution(correct);

            ProgramManager.getDataManager().increaseMistakesTotal();
            ProgramManager.getDataManager().decreaseRecognized(solution);
            ProgramManager.getDataManager().increaseMistakes(newSolution);
            ProgramManager.getDataManager().increaseRecognized(newSolution);

            DataManager.updateData("knn.mistakesTotal", ProgramManager.getDataManager().getMistakesTotal());

            DataManager.updateData("knn." + solution.getCharacter() + ".recognized", solution.getRecognized());
            DataManager.updateData("knn." + newSolution.getCharacter() + ".mistakes", newSolution.getMistakes());
            DataManager.updateData("knn." + newSolution.getCharacter() + ".recognized", newSolution.getRecognized());

        }
        Logger.log(KNN_SOLUTION_SIMILAR, LoggerLevel.INFO);
        Solution solution = networkTrainer.getSolutionManager().getSolution(correct);

        updateSolution(solution);
        updateTop3();
        updateCharTable(networkTrainer.getSolutionManager().getSolutions());
    }

    @FXML
    private void handleSaveButton() {
        new NetworkFactory(new File(ProgramManager.APPLICATION_PATH + PATHSEPARATOR + "network.yml")).save(networkTrainer.getNetwork());
    }

    @FXML
    private void handleLoadButton() {
         new NetworkFactory(new File(ProgramManager.APPLICATION_PATH + PATHSEPARATOR + "network.yml")).load(networkTrainer.getNetwork());
    }

    @FXML
    private void handleLoadSaveRadioButton() {
        if (!automaticSaveLoadRadioButton.isSelected()) {
            SettingsManager.updateSettings("program.settings.automaticSaveALoad", false);
            Settings.automaticSaveALoad = false;
        } else {
            SettingsManager.updateSettings("program.settings.automaticSaveALoad", true);
            Settings.automaticSaveALoad = true;
        }
    }

    @FXML
    private void handleShowDataGUIButton() {
        new DataGUI();
    }

    public void train() {
        String s = trainCharComboBox.getValue();
        if (s == null) {
            DialogUtils.showWarningDialog(GUI_NO_CHARACTER_SELECTED_SHORT, GUI_NO_CHARACTER_SELECTED_LONG);
            return;
        }
        if (drawablePane.isPanelClear()) {
            DialogUtils.showWarningDialog(GUI_PANE_EMPTY_SHORT, GUI_PANE_EMPTY_LONG);
            return;
        }
        networkTrainer.addTraining(new Training(drawablePane.getPixelsAsList(), Outputs.get().getOutput(s)));
        networkTrainer.train(5000);
        TrainingLoader.save(drawablePane.getPixelsAsList(), s);
    }

    private void setupLeftSide() {
        drawPane.getChildren().add(swingNode);
    }

    private void setupRightSide() {
        solutionLabel.setText(null);
        solutionPercentLabel.setText(null);
    }

    private void setupComboBoxes() {
        trainCharComboBox.getItems().addAll(Utils.ALPHABET);
    }

    private void createSwingContent() {
        SwingUtilities.invokeLater(() -> {
            drawablePane = new DrawablePane(680, 680, Settings.gui_dpanel_res);
            swingNode.setContent(drawablePane);
        });
    }

    private void updateSolution(Solution solution) {
        solutionLabel.setText(solution.getCharacter());
        solutionPercentLabel.setTextFill(solution.getColor());
        solutionPercentLabel.setText(solution.getPercentage() + "%");
    }

    private void updateTop3() {
        topSolutionListView.getItems().clear();

        networkTrainer.getSolutionManager().getTop3().forEach(Solution -> topSolutionListView.getItems().add(Solution.getCharacter() + " (" + Solution.getPercentage() + "%)"));
    }

    private void updateCharTable(ArrayList<Solution> solutions) {
        fullListTableView.setItems(FXCollections.observableArrayList(solutions));

        TableColumn<Solution, String> chars = new TableColumn<>("Zeichen");
        chars.setCellValueFactory(new PropertyValueFactory<>("character"));

        TableColumn<Solution, String> probability = new TableColumn<>("Wahrscheinlichkeit");
        probability.setCellValueFactory(new PropertyValueFactory<>("percentage"));

        fullListTableView.getColumns().setAll(chars, probability);
    }
}
