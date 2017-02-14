package de.daviddo.program.gui.controller;

import de.daviddo.knn.solution.Solution;
import de.daviddo.program.manager.ProgramManager;
import de.daviddo.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Glow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import static de.daviddo.utils.Messages.*;

/**
 *
 * @author  Daviddo3399
 */
public class DataGUIController implements Initializable {

    @FXML private BorderPane        completeBorderPane;
    @FXML private BorderPane        characterBorderPane;

    @FXML private AnchorPane        completeAnchorPane;

    @FXML private Tab               tab;

    @FXML private ChoiceBox<String> choiceBox;

    @FXML private PieChart          characterPieChart;
    @FXML private PieChart          completePieChart;

    @FXML private Label             recognizedTotalCountLabel;
    @FXML private Label             mistakesTotalCountLabel;
    @FXML private Label             mostMistakeRecognizedCountLabel;
    @FXML private Label             mostMistakeMistakesCountLabel;
    @FXML private Label             mostMistakeLabel;
    @FXML private Label             mostMistakePercentageLabel;
    @FXML private Label             ratioPercentageLabel;
    @FXML private Label             memoryLabel;
    @FXML private Label             knnSizeLabel;

    @FXML private Text              noDataFound;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        noDataFound = new Text();

        setupChoiceBox();
        updateTotalPieChart();
        updateAdditionalInformation();
    }

    private void setupChoiceBox() {
        choiceBox.getItems().addAll(Utils.ALPHABET);
    }

    @FXML
    private void handleChoiceBox() {
        String character = choiceBox.getSelectionModel().getSelectedItem();
        tab.setText(GUI_DATAGUI_TITEL_CHARACTER_TAB.replace("%character", character));
        updateCharacterPieChart(character);
    }

    private void updateAdditionalInformation() {
        recognizedTotalCountLabel.setText(String.valueOf(ProgramManager.getDataManager().getRecognizedTotal()));
        mistakesTotalCountLabel.setText(String.valueOf(ProgramManager.getDataManager().getMistakesTotal()));

        ArrayList<Solution> solutions   = ProgramManager.getDataManager().getSolutionManager().getSolutions();
        Integer             index       = 0;

        for (int i = 0; i < solutions.size(); i++) {
            if (solutions.get(i).getMistakes() > solutions.get(index).getMistakes()) {
                index = i;
            }
        }

        Solution solution = solutions.get(index);

        mostMistakeLabel.setText(solution.getCharacter());
        mostMistakeRecognizedCountLabel.setText(String.valueOf(solution.getRecognized()));
        mostMistakeMistakesCountLabel.setText(String.valueOf(solution.getMistakes()));
        mostMistakePercentageLabel.setText(String.valueOf(Utils.round(100 * (double) solution.getMistakes() / (double) solution.getRecognized(), 2)) + "%");
        ratioPercentageLabel.setText(Utils.round(100 * (double) ProgramManager.getDataManager().getMistakesTotal() / (double) ProgramManager.getDataManager().getRecognizedTotal(), 2) + "%");

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> memoryLabel.setText(String.valueOf(Utils.bytesToMegaBytes(Runtime.getRuntime().totalMemory()) + " MB")));
            }
        }, 0, 200);

    }

    private void updateCharacterPieChart(String character) {
        noDataFound.setVisible(false);
        characterPieChart.getData().clear();

        int recognized  = ProgramManager.getDataManager().getSolution(character).getRecognized();
        int mistakes    = ProgramManager.getDataManager().getSolution(character).getMistakes();

        PieChart.Data recognizedSlice   = new PieChart.Data("Gesamt", recognized);
        PieChart.Data mistakesSlice     = new PieChart.Data("Fehler", mistakes);

        if (recognizedSlice.getPieValue() == 0 || mistakesSlice.getPieValue() == 0) {
            noDataFound.setVisible(true);
            noDataFound.setText(GUI_DATAGUI_CHARACTER_TAB_NO_DATAFOUND.replace("%character", character));

            characterBorderPane.setCenter(noDataFound);
        } else {
            characterPieChart.getData().addAll(recognizedSlice, mistakesSlice);
            characterBorderPane.setCenter(characterPieChart);
        }

        characterPieChart.getData().forEach(this::applyMouseEvent);
    }

    private void updateTotalPieChart() {
        noDataFound.setVisible(false);
        characterPieChart.getData().clear();

        int recognizedTotal = ProgramManager.getDataManager().getRecognizedTotal();
        int mistakesTotal   = ProgramManager.getDataManager().getMistakesTotal();

        PieChart.Data recognizedTotalSlice  = new PieChart.Data("Gesamt", recognizedTotal);
        PieChart.Data mistakesTotalSlice    = new PieChart.Data("Fehler", mistakesTotal);

        if (recognizedTotalSlice.getPieValue() == 0 || mistakesTotalSlice.getPieValue() == 0) {
            noDataFound.setVisible(true);
            noDataFound.setText(GUI_DATAGUI_NO_DATAFOUND);

            completeBorderPane.setCenter(noDataFound);
        } else {
            completePieChart.getData().addAll(recognizedTotalSlice, mistakesTotalSlice);
            completeBorderPane.setCenter(completePieChart);
        }

        completePieChart.getData().forEach(this::applyMouseEvent);
    }

    private void applyMouseEvent(PieChart.Data data) {
        Tooltip tooltip = new Tooltip();
        data.getNode().setOnMouseEntered(event -> {
            String pievalue_int = String.valueOf((int) data.getPieValue());
            String name         = data.getName();
            String pievalue     = String.valueOf(data.getPieValue());

            tooltip.setText(GUI_DATAGUI_TOOLTIP.replace("%pievalue_int", pievalue_int).replace("%name", name).replace("%pievalue", pievalue));
            tooltip.setText((int) data.getPieValue() + " " + data.getName() +  " (" + data.getPieValue() + "%)");
            Tooltip.install(data.getNode(), tooltip);

            Utils.editTooltipTiming(tooltip, 20);
            data.getNode().setEffect(new Glow());
        });

        data.getNode().setOnMouseExited(event -> {
            data.getNode().setEffect(null);
            tooltip.hide();
        });
    }


}
