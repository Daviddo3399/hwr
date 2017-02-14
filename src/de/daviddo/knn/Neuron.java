package de.daviddo.knn;

import java.util.ArrayList;

/**
 * Repräsentiert die Neuronen Klasse, welche versucht, das biologische Vorbild zu
 * imitieren. Die Klasse bietet alle Methoden um die verschiedenen Rechnungen und
 * Prozesse durchzuführen. Als Lernregel wurde die Perzeptron-Lernregel genutz.
 * Desweiteren bestitzt jede Instanz die neu erzeugt wird, ein eigenes Bias-Neuron
 * welche die Aktivität des Neurons gewährleisten soll, auch wenn kein akuter
 * Input vorhanden ist.
 *
 * Zusätzlich lässt sich über die Methode loadWeights(...) und loadInputs(...)
 * schon gespeicherte Gewichte und Inputs zu laden, um schon vorhandenes "Wissen"
 * wieder benutzen zu können.
 *
 * Jedes Neuron besitzt außerdem eine ID, welche in dem Fall ein Wert aus dem Index
 * des Alphabets besitz. Das bedeutet, das beispielsweise das Neuron, welches für
 * den Buchstaben A "zuständig" ist die ID 0 besitzt.
 *
 * @author  Daviddo3399
 */
public class Neuron {

    private final   Double              LEARNINGRATIO   = 0.1;

    private         ArrayList<Integer>  inputs;
    private         ArrayList<Double>   weights;

    private         Integer             id;

    private         BiasNeuron          biasNeuron;

    public Neuron(int id) {
        biasNeuron  = new BiasNeuron(LEARNINGRATIO);
        inputs      = new ArrayList<>();
        weights     = new ArrayList<>();

        this.id     = id;
    }

    /**
     * Setz die Inputs für das entsprechende Neuron. Sollte die Zeichenfläche leer
     * sein oder das Program zum ersten mal gestartet, so werden zusätzlich die
     * Gewichte des Neurons durch die Methode initWeights() generiert.
     *
     * @param inputList Die Liste der einzelnen Inputs. Die Liste besteht aus
     *                  Nullen und Einsen, welche den Zustand des einzelnen Pixels
     *                  in dem Zeichenfeld repräsentieren. Null steht dabei für
     *                  nicht "gefüllt" und eins für "gefüllt". Mithilfe der
     *                  einzelnen Inputs können so später die neuen Verbindungen
     *                  berechnet werden.
     *
     * @see #initWeights()
     */
    public void setInputs(ArrayList<Integer> inputList) {
        if (inputs.size() == 0) {
            inputs = new ArrayList<>(inputList);
            initWeights();
            return;
        }
        inputs = new ArrayList<>(inputList);
    }

    /**
     * Berechnet und verfeinert das Gewicht der Verbindungen für des jeweiligen
     * Neuron mit Hilfe der Perzeptoren-Lernregel
     *
     * @param delta Das Delta welches sich aus dem Soll-Output und dem gegebenen
     *              Output, durch eine subtraktion berechnet wird.
     */
    public void modifyWeights(Double delta) {
        for (int i = 0; i < inputs.size(); i++) {
            double weight = weights.get(i);
            weight += LEARNINGRATIO * delta * inputs.get(i);
            weights.set(i, weight);
        }
        biasNeuron.modifyWeights(delta);
    }

    /**
     * Bestimmt den gesamten Netzinput für das jeweilige Neuron. Des weiteren wird
     * in dieser Methode der Biasinput für das Neuron bestimmt, damit das Neuron
     * aktiviert bleibt, auch wenn kein starker Input (0) vorhanden ist.
     *
     * @return  den gesamten Netzinput, inklusive Biasinput als Double.
     */
    public double computeNetinput() {
        double netinput = 0.0;

        for (int i = 0; i < inputs.size(); i++) {
            netinput += inputs.get(i) * weights.get(i);
        }
        netinput +=  biasNeuron.computeOutput();
        return netinput;
    }

    /**
     * Initialisiert die einzelnen Verbindungsgewichte für das jeweilige Neuron.
     * Diese Methode wird genutzt, wenn das Program zum ersten Mal gestartet wurde
     * oder das Zeichenfeld leer ist.
     */
    private void initWeights() {
        for (int i = 0; i < inputs.size(); i++) {
            weights.add(Math.random());
        }
        biasNeuron.initWeight();
    }

    /**
     * Die logische Aktivierungsfunktion, welche das Aktivitätslevel des Neurons
     * ermittelt.
     *
     * @param netinput  der Netzinput für das jeweilige Neuron.
     * @return          das errechnete Aktivitätslevel.
     */
    private double activationFunction(double netinput) {
        return (1 / (1 + Math.exp(-netinput)));
    }

    /**
     * Diese Methode läd die verschieden Gewichte der Verbindungen für das Neuron.
     * Das laden der Gewichte ist vor allem deshalb notwendig, da die Gewichte nur
     * temporär gespeichert sind und jedes mal beimschließen des Programms gelöscht
     * werden.
     * Würden die Gewicht nicht bei Programm start geladen werden, vorausgesetzt
     * das Programm wurde schon einmal gestartet und das künstlich neuronale Netz
     * schon einmal trainiert, würden alle schon "antrainierte" Zeichen vergessen
     * werden.
     *
     * @param weights die Gewichte der Verbindungen für das jeweilige Neuron.
     */
    public void loadWeights(ArrayList<Double> weights) {
        this.weights = new ArrayList<>(weights);
    }

    /**
     * Diese Methode läd die verschiedenen Inputs für das Neuron.
     *
     * @param inputs Die Inputs für das jeweilige Neuron
     */
    public void loadInputs(ArrayList<Integer> inputs) {
        this.inputs = new ArrayList<>(inputs);
    }

    /**
     * Gibt den Output des jeweiligen Neurons aus. Der Output wird mithilfe der
     * computeNetinput() Methode bestimmt.
     *
     * @return  den Output des jeweiligen Neurons als Double
     *
     * @see #computeNetinput()
     * @see #activationFunction(double)
     */
    public double getOutput() {
        return activationFunction(computeNetinput());
    }

    /**
     * Gibt die gesamten Inputs für das jeweilige Neuron als ArrayList aus.
     *
     * @return Die gesamten Inputs als ArrayList
     */
    public ArrayList<Integer> getInputs() {
        return inputs;
    }

    /**
     * Gibt die gesamt Verbindungsgewichte für das jeweilige Neuron als ArrayList
     * aus.
     *
     * @return die gesamten Verbindungsgewichte als ArrayList
     */
    public ArrayList<Double> getWeights() {
        return weights;
    }

    /**
     * Gibt das Bias-Neuron des jeweiligen Neurons zurück.
     *
     * @return das Bias-Neuron
     */
    public BiasNeuron getBiasNeuron() { return biasNeuron; }

    /**
     * Gibt die ID für das jeweilige Neuron zurück.
     *
     * @return die ID des Neurons
     */
    public int getID() {
        return id;
    }
}
