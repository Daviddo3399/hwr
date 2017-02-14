package de.daviddo.knn.network;

import de.daviddo.knn.Neuron;
import de.daviddo.knn.solution.Solution;
import de.daviddo.utils.Utils;
import de.daviddo.utils.logging.Logger;
import de.daviddo.utils.logging.LoggerLevel;

import java.util.ArrayList;

import static de.daviddo.utils.Messages.*;

/**
 * Die Network Klasse stellt das gesamte neuronale Netzwerk dar. Über diese Klasse
 * können alle nötigen Funktionen eines Neurons bedient werden. Desweiteren kann
 * mithilfe der Methode recognize versucht werden ein Buchstabe zu erkennen.
 *
 * @author  Daviddo3399
 * @see		Neuron
 */
public class Network {

	public ArrayList<Neuron> 	neurons;
	
	public Network(int neuronCount) {
		neurons = new ArrayList<>();
		addNeurons(neuronCount);
	}

	/**
	 *
	 * @param outputs
	 * @return
	 */
	public Solution recognize(ArrayList<Double> outputs) {
		Logger.log(KNN_NETWORK_RECOGNIZING_PATTERN, LoggerLevel.INFO);
		int index = 0;
		Logger.log(KNN_NETWORK_RECOGNIZING_PATTERN_ANALYSING, LoggerLevel.INFO);
		for (int i = 0; i < neurons.size(); i++) {
			Logger.log(KNN_NETWORK_RECOGNIZING_PATTERN_AB.replace("%a", String.valueOf(outputs.get(i))).replace("%b", String.valueOf(outputs.get(index))));
			if (outputs.get(i) > outputs.get(index)) {
				index = i;
			}
		}
		Logger.log(KNN_NETWORK_RECOGNIZING_PATTERN_FINISHED, LoggerLevel.FINISHED);
		Logger.log(KNN_NETWORK_RECOGNIZING_PATTERN_INDEX.replace("%index", String.valueOf(index)));
		Logger.log(KNN_NETWORK_RECOGNIZING_PATTERN_CHARACTER.replace("%character", String.valueOf(Utils.ALPHABET[index])));
		Logger.log(KNN_NETWORK_RECOGNIZING_PATTERN_NEURON.replace("%neuron", String.valueOf(neurons.get(index).getID())));
		Logger.log(KNN_NETWORK_RECOGNIZING_PATTERN_OUTPUT.replace("%output", String.valueOf(neurons.get(index).getOutput())));
		return new Solution(index, outputs.get(index));
	}

	/**
	 * Fügt n Neuronen zu dem künstlich neuronalen Netz hinzu.
	 * 
	 * @param count die Anzahl der Neuronen, die dem künstlich neuronalen Netz
	 *             	hinzugefügt werden sollen.
	 */
	public void addNeurons(int count) {
		for (int i = 0; i < count; i++) {
			neurons.add(new Neuron(i));
		}
	}
	
	/**
	 * Setz für jedes Neuron, in dem künstlich neuronalen Netz, die ensprechenden
	 * Inputs.
	 * 
	 * @param inputs Die Inputs als ArrayList.
	 */
	public void setInputs(ArrayList<Integer> inputs) {
		neurons.forEach(neuron -> neuron.setInputs(inputs));
	}

	/**
	 * Diese Methode modifiziert die jeweiligen Verbindungsgewichte für alle Neuronen
	 * in dem künstlich neuronalen Netz.
	 *
	 * @param output der jeweilige richtige Output. Beispielsweise für A 1.0
	 */
	public void computeAllWeights(ArrayList<Double> output) {
		neurons.forEach(neuron -> neuron.modifyWeights(output.get(neuron.getID()) - neuron.getOutput()));
	}

	/**
	 * Fügt die verschiedenen Outputs der einzelnen Neuronen in eine Liste und
	 * gibt diese anschließend aus.
	 *
	 * @return die zusammengefügten Outputs des Netzes
	 */
	public ArrayList<Double> getOutputs() {
		ArrayList<Double> outputs = new ArrayList<>();
		neurons.forEach(neuron -> outputs.add(neuron.getOutput()));
		return outputs;
	}

	public int size() {
		int size = 0;
		for (Neuron neuron : neurons) {
			size += Utils.getObjectSize(neuron);
		}
		return size;
	}

	/**
	 *
	 * @return
	 */
	public ArrayList<Neuron> getNeurons() {
		return neurons;
	}
}
