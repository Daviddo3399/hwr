package de.daviddo.knn.solution;

import de.daviddo.utils.Utils;
import de.daviddo.utils.logging.Logger;
import de.daviddo.utils.logging.LoggerLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static de.daviddo.utils.Messages.*;


/**
 * Repräsentiert die SolutionManager Klasse, welche alle Lösungsansätze verwaltet
 * und diese auch erstellen kann. Mithilfe der Klasse können beispielsweise für
 * alle Lösungsansätze die aktuellen Outputs Netzes mit den alten ersetz werden.
 *
 * Die Klasse bietet desweiteren die Möglichkeit entweder die Top3 Lösungsansätze
 * anhand der aktuellen Outputs zu bestimmen und oder alle auszugeben.
 *
 * @author  Daviddo3399
 */
public class SolutionManager {

    private ArrayList<Solution> solutions;
    private ArrayList<Double>   outputs;

    public SolutionManager() {
        solutions       = new ArrayList<>();
        outputs         = new ArrayList<>();
        addSolutions();
    }

    /**
     * Fügt bei Programm start in die ArrayList den festgelegten Wert 00.00 um
     * NullPointerExceptions zu vermeiden, welche zu Programm start aufdrehten
     * werden, da das Programm anfangs versucht die Top3 auszuwerten.
     *
     * Anschließend werden die eigentlichen Lösungsansätze erstellt mit den davor
     * generierten Werten.
     */
    private void addSolutions() {
        if (outputs.size() == 0) {
            for (int i = 0; i < Utils.ALPHABET.length; i++) {
                outputs.add(00.00);
            }
        }

        for (int i = 0; i < Utils.ALPHABET.length; i++) {
            solutions.add(new Solution(i, outputs.get(i)));
        }
    }

    /**
     * Findet den Lösungsansatz für den gewählten Buchstaben.
     *
     * @param   character   der Buchstabe
     * @return              der jeweilige Lösungsansatz für den Buchstaben, sofern
     *                      einer für den Buchstaben existiert.
     */
    public Solution getSolution(String character) {
        Solution s = null;
        for (Solution solution : solutions) {
            if (solution.getCharacter().equalsIgnoreCase(character)) {
                s = solution;
            }
        }
        return s;
    }

    /**
     * Aktualisiert die Outputs, welche jedes mal erneuert werden, wenn die
     * verschiedenen Gewichte modifiziert wurden.
     *
     * @param outputs die aktualisierten Outputs.
     */
    public void updateOutputs(ArrayList<Double> outputs) {
        Logger.log(KNN_SOLUTION_MANAGER_UPDATE_OUTPUT, LoggerLevel.INFO);
        Logger.log(KNN_SOLUTION_MANAGER_UPDATE_OUTPUT_OLD + this.outputs);
        Logger.log(KNN_SOLUTION_MANAGER_UPDATE_OUTPUT_NEW + outputs);

        this.outputs = outputs;
        updateSolutions(outputs);

        Logger.log(KNN_SOLUTION_MANAGER_UPDATE_OUTPUT_FINISHED, LoggerLevel.FINISHED);
    }

    /**
     * Aktualisiert alle Lösungsansätze mit den neuen Outputs.
     *
     * @param outputs die aktualisierten Outputs.
     */
    private void updateSolutions(ArrayList<Double> outputs) {
        Logger.log(KNN_SOLUTION_MANAGER_UPDATE_OUTPUT_WITH_NEW_OUTPUTS, LoggerLevel.INFO);
        if (solutions.size() == 0) {
            addSolutions();
        }
        for (int i = 0; i < solutions.size(); i++) {
            Solution solution = new Solution(i, outputs.get(i));
            solutions.set(i, solution);
            Logger.log(KNN_SOLUTION_MANAGER_UPDATE_SOLUTION.replace("%id", String.valueOf(i)));
        }
        Logger.log(KNN_SOLUTION_MANAGER_UPDATE_OUTPUT_WITH_NEW_OUTPUTS_FINISHED, LoggerLevel.FINISHED);
    }

    /**
     * Sucht die Top 3 Lösungsansätze heraus und fügt diese in eine ArrayList,
     * welche anschließend ausgegeben wird.
     *
     * @return Die Top 3 Lösungsansätze als ArrayList
     */
    public ArrayList<Solution> getTop3() {
        ArrayList<Solution>     top3            = new ArrayList<>();
        HashMap<String, Double> map             = new HashMap<>();
        SolutionComparator      valueComparator = new SolutionComparator(map);
        TreeMap<String, Double> sortedMap       = new TreeMap<>(valueComparator);

        solutions.forEach(solution -> map.put(solution.getCharacter(), solution.getOutput()));
        sortedMap.putAll(map);

        for (int i = 0; i < 3; i++) {
            Map.Entry<String, Double> entry = sortedMap.pollFirstEntry();

            String key      = entry.getKey();
            Double value    = entry.getValue();

            Logger.log(KNN_SOLUTION_MANAGER_TOP3_SOLUTION.replace("%id", String.valueOf(i)), LoggerLevel.INFO);
            Logger.log(KNN_SOLUTION_MANAGER_TOP3_SOLUTION_CHARACTER.replace("%key", key).replace("%index", String.valueOf(Utils.getIndex(key))));
            Logger.log(KNN_SOLUTION_MANAGER_TOP3_SOLUTION_PERCENTAGE.replace("%percentage", String.valueOf(Utils.round(value * 100, 2))));
            top3.add(new Solution(Utils.getIndex(key), value));
        }
        return top3;
    }

    /**
     * Gibt die gesamten Lösungsansätze aus.
     *
     * @return Die gesamten Lösungsansätze als ArrayList
     */
    public ArrayList<Solution> getSolutions() {
        return solutions;
    }
}
