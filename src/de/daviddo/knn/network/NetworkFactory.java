package de.daviddo.knn.network;

import de.daviddo.knn.Neuron;
import de.daviddo.utils.DialogUtils;
import de.daviddo.utils.io.YAMLFile;
import de.daviddo.utils.logging.Logger;
import de.daviddo.utils.logging.LoggerLevel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static de.daviddo.utils.Messages.*;

/**
 * Mithilfe dieser Klasse können gespeicherte Netze wieder geladen werden. Die
 * Verbindungsgewichte und Inputs werden dann für die jeweiligen Neuronen geladen
 * um so sicher zu stellen, dass das eigentliche Wissen des Netzes nicht verloren
 * geht.
 * Desweiteren besteht die Möglichkeit das aktuelle Netz zu speichern.
 *
 * @author  Daviddo3399
 */
public class NetworkFactory {

    private File        file;
    private YAMLFile    yamlFile;

    /**
     *
     * @param file Die Datei in welcher das künstlich neuronale Netz gespeichert
     *             werden soll.
     */
    public NetworkFactory(File file) {
        this.file = file;
        yamlFile = new YAMLFile(file);

        if (!file.exists()) try {
            file.createNewFile();
        } catch (IOException e) {
            DialogUtils.showExceptionDialog("Failed to create the new file..", e);
        }
    }

    /**
     * Läd die Inputs und die Gewichte für alle Neuronen innerhalb des künstlich
     * neuronalen Netzes.
     *
     * @param network Das künstlich neuronale Netz
     *
     * @see #loadInputs(int)
     * @see #loadWeights(int)
     */
    public void load(Network network) {
        network.getNeurons().forEach(neuron -> neuron.loadInputs(loadInputs(neuron.getID())));
        network.getNeurons().forEach(neuron -> neuron.loadWeights(loadWeights(neuron.getID())));
    }

    /**
     * Speichert die Inputs und die Gewichte für alle Neuronen innerhalb des künstlich
     * neuronalen Netzes in der dafür vorgesehenen Datei.
     *
     * @param network Das künstlich neuronale Netz
     *
     * @see #saveInputs(Neuron)
     * @see #saveWeights(Neuron)
     */
    public void save(Network network) {
        network.getNeurons().forEach(neuron -> saveInputs(neuron));
        network.getNeurons().forEach(neuron -> saveWeights(neuron));
    }

    /**
     * Läd die Inputs für die gegebene ID eines Neurons aus der Datei, um diese
     * später den Neuronen zur verfügung zu stellen.
     *
     * @param id    Die ID des jeweiligen Neurons
     * @return      Die Inputs für das jeweilige Neuron als ArrayList
     */
    public ArrayList<Integer> loadInputs(int id) {
        Logger.log(KNN_NETWORK_INPUTS_LOAD_UPDATE.replace("%id", String.valueOf(id)), LoggerLevel.INFO);
        ArrayList<Integer> inputs = new ArrayList<>(yamlFile.get().getIntegerList("neurons." + id + ".inputs"));
        if (inputs.size() != 0) {
            Logger.log(KNN_NETWORK_INPUTS_LOAD_UPDATE_FINISHED.replace("%id", String.valueOf(id)), LoggerLevel.INFO);
            Logger.log(KNN_NETWORK_INPUTS_LOAD_UPDATE_FINISHED_ARRAYLIST_SIZE.replace("%size", String.valueOf(inputs.size())));
            Logger.log(KNN_NETWORK_INPUTS_LOAD_UPDATE_FINISHED_ARRAYLIST_CONTENT.replace("%content", String.valueOf(inputs)));
        } else {
            Logger.log(KNN_NETWORK_INPUTS_LOAD_UPDATE_FAILED.replace("%id", String.valueOf(id)), LoggerLevel.WARNING);
        }

        return (inputs.size() == 0) ? new ArrayList<>() : inputs;
    }

    /**
     * Läd die Gewichte für die gegebene ID eines Neurons aus der Datei, um diese
     * später den Neuronen zur verfügung zu stellen.
     *
     * @param id    Die ID des jeweiligen Neurons
     * @return      Die Gewichte für das jeweilige Neuron als ArrayList
     */
    public ArrayList<Double> loadWeights(int id) {
        Logger.log(KNN_NETWORK_WEIGHTS_LOAD_UPDATE.replace("%id", String.valueOf(id)), LoggerLevel.INFO);
        ArrayList<Double> weights = new ArrayList<>(yamlFile.get().getDoubleList("neurons." + id + ".weights"));
        if (weights.size() != 0) {
            Logger.log(KNN_NETWORK_WEIGHTS_LOAD_UPDATE_FINISHED.replace("%id", String.valueOf(id)), LoggerLevel.INFO);
            Logger.log(KNN_NETWORK_WEIGHTS_LOAD_UPDATE_FINISHED_ARRAYLIST_SIZE.replace("%size", String.valueOf(weights.size())));
            Logger.log(KNN_NETWORK_WEIGHTS_LOAD_UPDATE_FINISHED_ARRAYLIST_CONTENT.replace("%content", String.valueOf(weights)));
        } else {
            Logger.log(KNN_NETWORK_WEIGHTS_LOAD_UPDATE_FAILED.replace("%id", String.valueOf(id)), LoggerLevel.INFO);
        }
        return (weights.size() == 0) ? new ArrayList<>() : weights;
    }

    /**
     * Speichert die Inputs des gegebenen Neurons in die dafür vorgesehenen Datei.
     * Diese Inputs können mithilfe der Methode {@link #loadInputs(int)} zu einem
     * späteren Zeitpunkt wiederwieder geladen und diesem speziellen Neuron wieder
     * zur verfügung gestellt werden.
     *
     * @param neuron Das künstliche Neuron
     */
    private void saveInputs(Neuron neuron) {
        Logger.log(KNN_NETWORK_INPUTS_SAVE.replace("%id", String.valueOf(neuron.getID())), LoggerLevel.INFO);
        Logger.log(KNN_NETWORK_INPUTS_SAVE_ARRAYLIST_SIZE.replace("%size", String.valueOf(neuron.getWeights().size())));
        Logger.log(KNN_NETWORK_INPUTS_SAVE_ARRAYLIST_CONTENT.replace("%content", String.valueOf(neuron.getWeights())));
        yamlFile.get().set("neurons." + neuron.getID() + ".inputs", neuron.getInputs());
        yamlFile.save();
        Logger.log(KNN_NETWORK_INPUTS_SAVE_FINISHED.replace("%id", String.valueOf(neuron.getID())), LoggerLevel.FINISHED);
    }

    /**
     * Speichert die Gewichte des gegebenen Neurons in die dafür vorgesehenen Datei.
     * Diese Gewichte können mithilfe der Methode {@link #loadWeights(int)} (int)}
     * zu einem späteren Zeitpunkt wieder geladen und diesem speziellen Neuron
     * wieder zur verfügung gestellt werden.
     *
     * @param neuron Das künstliche Neuron
     */
    private void saveWeights(Neuron neuron) {
        Logger.log(KNN_NETWORK_WEIGHTS_SAVE.replace("%id", String.valueOf(neuron.getID())), LoggerLevel.INFO);
        Logger.log(KNN_NETWORK_WEIGHTS_SAVE_ARRAYLIST_SIZE.replace("%size", String.valueOf(neuron.getWeights().size())));
        Logger.log(KNN_NETWORK_WEIGHTS_SAVE_ARRAYLIST_CONTENT.replace("%content", String.valueOf(neuron.getWeights())));
        yamlFile.get().set("neurons." + neuron.getID() + ".weights", neuron.getWeights());
        yamlFile.save();
        Logger.log(KNN_NETWORK_WEIGHTS_SAVE_FINISHED.replace("%id", String.valueOf(neuron.getID())), LoggerLevel.FINISHED);
    }
}
