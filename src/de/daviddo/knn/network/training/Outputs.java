package de.daviddo.knn.network.training;

import de.daviddo.utils.Utils;

import java.util.ArrayList;

/**
 *
 * @author  Daviddo3399
 */
public class Outputs {

    private static Outputs outputs;
    private static ArrayList<ArrayList<Double>> values;

    public Outputs() {
        values = new ArrayList<>();
        addOutputs();
    }

    /**
     * Diese Methode erstellt eine ArrayList für jeden Buchstaben im Alphabet. Diese
     * List sieht wie folg aus (hierfür der Buchstabe C als Beispiel):
     *      {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
     *
     *      Jeder Buchstabe wird anfangs mit dem Wert 0.0 eingespeichert. Da wir in
     *      diesem Fall davon ausgehen, dass das C der aktuelle Buchstabe ist, wird
     *      an der 3. Position in der ArrayList der Wert mit 1.0 ersetzt. Dies wird
     *      für jeden Buchstaben durchgeführt. Die erstellten ArrayList werden dann
     *      in die gesamt umfassende ArrayList values hinzugefügt. Letztendlich sieht
     *      das gesamt Ergebniswie folgt aus:
     *
     *      A: {1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
     *      B: {0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
     *      C: {0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
     *      D: {0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}
     *      ...
     */
    private void addOutputs() {
        for (int a = 0; a < Utils.ALPHABET.length; a++) {
            ArrayList<Double> list = new ArrayList<>();

            for (int b = 0; b < Utils.ALPHABET.length; b++) {
                list.add(0.0);
            }
            list.set(a, 1.0);
            values.add(list);
        }
    }

    /**
     * Gibt den Output für den gegebenen Buchstaben aus. Mit Hilfe des Outputs kann
     * so später ermittelt werden ob das Netz einen Buchstaben erkennen konnte
     * oder nicht.
     *
     * @param character Der Buchstabe, für den der Output ausgegeben werden soll
     * @return          Den Output für den gegebenen Buchstaben
     */
    public ArrayList<Double> getOutput(String character) {
        return values.get(Utils.getIndex(character));
    }

    public static Outputs get() {
        if (outputs == null) {
            outputs = new Outputs();
        }
        return outputs;
    }
}
