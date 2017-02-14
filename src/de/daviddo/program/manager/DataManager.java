package de.daviddo.program.manager;

import de.daviddo.knn.solution.Solution;
import de.daviddo.knn.solution.SolutionManager;
import de.daviddo.utils.Messages;
import de.daviddo.utils.Utils;
import de.daviddo.utils.logging.Logger;
import de.daviddo.utils.logging.LoggerLevel;

import static de.daviddo.program.manager.ProgramManager.getDataFile;


/**
 *
 * @author  Daviddo3399
 */
public class DataManager {

    private SolutionManager solutionManager;

    private Integer         recognizedTotal;
    private Integer         mistakesTotal;

    public DataManager(boolean firstTime) {
        loadData(firstTime);
    }

    private void loadData(boolean firstTime) {

        if (firstTime) {
            Logger.log(Messages.MANAGER_WRITING_DATA, LoggerLevel.INFO);
            getDataFile().set("knn.recognizedTotal", 0);
            getDataFile().set("knn.mistakesTotal", 0);

            for (String character : Utils.ALPHABET) {
                getDataFile().set("knn." + character + ".recognized", 0);
                getDataFile().set("knn." + character + ".mistakes", 0);
            }

            ProgramManager.saveDataFile();
            Logger.log(Messages.MANAGER_WRITING_DATA_FINISHED, LoggerLevel.FINISHED);
        }

        Logger.log(Messages.MANAGER_LOADING_DATA, LoggerLevel.INFO);

        recognizedTotal = getDataFile().getInteger("knn.recognizedTotal");
        mistakesTotal   = getDataFile().getInteger("knn.mistakesTotal");

        Logger.log(Messages.MANAGER_LOADING_DATA_FINISHED, LoggerLevel.LOADED);
    }

    public static void updateData(String token, Object object) {
        getDataFile().set(token, object);
        ProgramManager.saveDataFile();
    }


    public void setSolutionManager(SolutionManager solutionManager) {
        this.solutionManager = solutionManager;
    }

    public int getRecognizedTotal() {
        return recognizedTotal;
    }

    public int getMistakesTotal() {
        return mistakesTotal;
    }

    public void increaseRecognizedTotal() {
        recognizedTotal++;
    }

    public void increaseMistakesTotal() {
        mistakesTotal++;
    }

    public void increaseRecognized(Solution solution) {
        solution.increaseRecognized();
    }

    public void increaseMistakes(Solution solution) {
        solution.increaseMistakes();
    }

    public void decreaseRecognized(Solution solution) {
        solution.decreaseRecognized();
    }

    public Solution getSolution(String character) {
        return solutionManager.getSolution(character);
    }

    public SolutionManager getSolutionManager() {
        return solutionManager;
    }
}
