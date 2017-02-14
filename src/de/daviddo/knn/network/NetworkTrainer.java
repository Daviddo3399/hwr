package de.daviddo.knn.network;

import de.daviddo.knn.network.training.Training;
import de.daviddo.knn.network.training.TrainingLoader;
import de.daviddo.knn.solution.SolutionManager;
import de.daviddo.program.manager.ProgramManager;
import de.daviddo.utils.logging.Logger;
import de.daviddo.utils.logging.LoggerLevel;
import de.daviddo.utils.settings.Settings;

import java.io.File;
import java.util.ArrayList;

import static de.daviddo.program.manager.ProgramManager.PATHSEPARATOR;

/**
 * Repräsntiert die NetworkTrainer Klasse, mit welcher man das künstlich neuronale
 * Netz mithilfe der verschiedenen Trainingssätze trainieren kann. Zu beginn, werden
 * die vorab erstellten Trainingsdaten genutzt, welche dann im Verlauf durch die
 * des Benutzer erstellten, ergänzt werden.
 *
 * @author  Daviddo3399
 * @see     Network
 * @see     SolutionManager
 */
public class NetworkTrainer {

    private Network                 network;
    private SolutionManager         solutionManager;

    private Integer                 neuronCount = Settings.neuron_count;

    private ArrayList<Training>     trainingSets;

    /**
     * Initialisiert alle wichtigen Parameter, läd die vorhanden Trainingsdaten,
     * sowie ein gespeichertes künstlich neuronales Netz, sofern eins existiert.
     */
    public NetworkTrainer() {
        network         = new Network(neuronCount);
        solutionManager = new SolutionManager();
        ProgramManager.getDataManager().setSolutionManager(solutionManager);

        trainingSets = TrainingLoader.loadTraings();

        if (Settings.automaticSaveALoad) {
            Logger.log("Auto load & save is activated.. Loading network..", LoggerLevel.INFO);
            new NetworkFactory(new File(ProgramManager.APPLICATION_PATH + PATHSEPARATOR + "network.yml")).load(network);
        }
    }

    /**
     * Trainiert das gesamte Netz n-Mal mit einem zufällig ausgewähltem Trainingssatz.
     *
     * @param count die Anzahl, wie oft der Vorgang wiederholt werden soll.
     */
    public void train(long count) {
        for (long l = 0; l < count; l++) {
            int index = ((int) (Math.random() * trainingSets.size()));

            Training trainingSet = trainingSets.get(index);

            network.setInputs(trainingSet.getInputs());
            network.computeAllWeights(trainingSet.getOutput());
        }
    }

    /**
     * Setzt die Inputs für das gesamte Netzwerk. Diese Inputs werden später dann
     * auf die einzelnen Neuronen verteilt.
     *
     * @param inputs die Inputs für das Netz
     */
    public void setInputs(ArrayList<Integer> inputs) {
        network.setInputs(inputs);
    }

    /**
     * Fügt einen neuen Trainingssatz hinzu.
     *
     * @param training der Trainingssatz
     */
    public void addTraining(Training training) {
        trainingSets.add(training);
    }

    /**
     * Gibt den gesamt Output des künstlich neuronalen Netzes aus.
     *
     * @return der gesamt Output des Netzes
     */
    public ArrayList<Double> getOutputs() {
        return network.getOutputs();
    }


    /**
     * Gibt das aktuelle künstlich neuronale aus.
     *
     * @return das künstlich neuronale Netz
     */
    public Network getNetwork() {
        return network;
    }


    public SolutionManager getSolutionManager() {
        return solutionManager;
    }
}
