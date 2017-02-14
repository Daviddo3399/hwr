package de.daviddo.knn;

/**
 * Repräsentiert das Bias-Neuron mit all seinen Funktionen. Es dient dazu das
 * jeweilige Neuron aktiv zuhalten auch wenn dessen Input gering ist. Um die
 * Verbindungsgewichte zu modifizieren, arbeitet das Bias-Neuron, sowie auch
 * die Neuronen in diesem Netz mit der Perzeptron-Lernregel.
 *
 * @author  Daviddo3399
 */
public class BiasNeuron {

    private final   Integer BIAS            = 1;

    private         Double  learningRatio;
    private         Double  weight;

    /**
     *
     * @param learningRatio die Lernrate
     */
    public BiasNeuron(Double learningRatio) {
        this.learningRatio = learningRatio;
        initWeight();
    }

    /**
     * Initialisiert das Verbindungsgewicht zischen dem jeweiligen Neuron und dem
     * Bias-Neuron.
     */
    public void initWeight() {
        weight = Math.random();
    }

    /**
     * Berechnet und verfeinert das Gewicht der Verbindungen zwischen dem jeweiligen
     * Neuron und dem Bias-Neuron mithilfe der Perzeptoren-Lernregel
     *
     * @param delta Das Delta welches sich aus dem Soll-Output und dem gegebenen
     *              Output, durch eine subtraktion berechnet wird.
     */
    public void modifyWeights(Double delta) {
        weight += learningRatio * delta * BIAS;
    }

    /**
     * Bestimmt den Output für das Bias-Neuron, welcher für das jeweilige Neuron
     * zusätzlich noch einen Output zurverfügung stellt, welcher dann zum Netinput
     * hinzuaddiert wird.
     *
     * @return den Output des Bias-Neuron.
     */
    public Double computeOutput() {
        return BIAS * weight;
    }
}
