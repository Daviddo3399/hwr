package de.daviddo.knn.network.training;

import java.util.ArrayList;

/**
 * Repräsentiert die Training Klasse, welche als ein einzelnes "Training" für das
 * künstlich neuronale Netz angesehen werden kann. Es beinhaltet die gegebenen
 * Inputs, sowie die dafür vorgesehenen/gewünschten Ergebnisse (Outputs). Die Outputs
 * stellen hierbei eine ArrayList dar, welche aus insgesamt 26 Einträgen besteht.
 * Ist der Trainingssatz beispielsweise zum training des Buchstabens A vorgesehen,
 * so würde die ArrayList wie folgt aussehen:
 *
 *      A: {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
 *
 * Da es sich in diesem fall um den Buchstaben A handelt, ist auch dieser an der
 * Stelle 0 mit dem Wert 1.0 versehen. Dieser Wert stellt dabei immer das Optimal
 * ergebnis dar(100%), welches das künstlich neuronale Netz erreichen kann.
 *
 * @author  Daviddo3399
 * @see     Outputs
 */
public class Training {

    private ArrayList<Integer>  inputs;
    private ArrayList<Double>   outputs;

    public Training(ArrayList<Integer> inputs, ArrayList<Double> outputs) {
        this.inputs     = inputs;
        this.outputs    = outputs;
    }

    /**
     * Gibt die Inputs für die jeweilige Trainingseinheit aus.
     * @return die Inputs
     */
    public ArrayList<Integer> getInputs() {
        return inputs;
    }

    /**
     * Gibt den vorgesehenen/gewünschte Output für die jeweilige Trainingseinheit
     * aus.
     *
     * @return der vorgesehenen/gewünschte Output
     */
    public ArrayList<Double> getOutput() {
        return outputs;
    }
}
