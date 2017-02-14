package de.daviddo.knn.network.training;

import de.daviddo.program.manager.ProgramManager;
import de.daviddo.utils.Utils;
import de.daviddo.utils.io.YAMLFile;
import de.daviddo.utils.logging.Logger;
import de.daviddo.utils.logging.LoggerLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static de.daviddo.program.manager.ProgramManager.PATHSEPARATOR;
import static de.daviddo.utils.Messages.KNN_NETWORK_TRAINING_LOADER_CHARACTER;
import static de.daviddo.utils.Messages.KNN_NETWORK_TRAINING_LOADER_CHARACTER_FAILED;
import static de.daviddo.utils.Messages.KNN_NETWORK_TRAINING_LOADER_CHARACTER_FINISHED;

/**
 * Mithilfe der TrainingLoader Klasse, können die gegebenen Traningssätze geladen
 * werden, welche sich indem Resourceordner befinden.
 *
 * @author  Daviddo3399
 */
public class TrainingLoader {

    public static ArrayList<Training> loadTraings() {
        ArrayList<Training> trainings = new ArrayList<>();

        for (int i = 0; i < 26; i++) {
            String character = Utils.ALPHABET[i].toUpperCase();
            Logger.log(KNN_NETWORK_TRAINING_LOADER_CHARACTER.replace("%character", String.valueOf(character)), LoggerLevel.INFO);
            ArrayList<ArrayList<Integer>> arrayLists = readTrainings(new File(ProgramManager.APPLICATION_PATH + PATHSEPARATOR + "characters" + PATHSEPARATOR + character + ".yml"));
            if (arrayLists != null) {
                trainings.addAll(arrayLists.stream().map(list -> new Training(list, Outputs.get().getOutput(character))).collect(Collectors.toList()));
                Logger.log(KNN_NETWORK_TRAINING_LOADER_CHARACTER_FINISHED.replace("%character", character), LoggerLevel.INFO);
            } else {
                Logger.log(KNN_NETWORK_TRAINING_LOADER_CHARACTER_FAILED.replace("%character", character), LoggerLevel.WARNING);
            }
        }
        return trainings;
    }

    public static void save(ArrayList<Integer> input, String character) {
        YAMLFile yamlFile = new YAMLFile(new File(ProgramManager.APPLICATION_PATH + PATHSEPARATOR + "characters" + PATHSEPARATOR + character + ".yml"));

        if (!yamlFile.exists()) yamlFile.createNewFile();
        if (!yamlFile.get().contains("inputs.count")) yamlFile.get().set("inputs.count", -1);

        Integer count = yamlFile.get().getInteger("inputs.count") + 1;
        yamlFile.get().set("inputs." + count + ".input", input);
        yamlFile.get().set("inputs.count", count);
        yamlFile.save();
    }

    public static ArrayList<ArrayList<Integer>> readTrainings(File file) {
        YAMLFile                        yamlFile    = new YAMLFile(file);
        ArrayList<ArrayList<Integer>>   data        = new ArrayList<>();
        Integer                         count       = yamlFile.get().getInteger("inputs.count");

        for (int i = 0; i < count; i++) {
            data.add(new ArrayList<>(yamlFile.get().getIntegerList("inputs." + count + ".input")));
        }
        return data;
    }
}
