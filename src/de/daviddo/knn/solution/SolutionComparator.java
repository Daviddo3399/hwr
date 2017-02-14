package de.daviddo.knn.solution;

import java.util.Comparator;
import java.util.Map;

/**
 *
 * @author Daviddo3399
 */
public class SolutionComparator implements Comparator<String> {

    private Map<String, Double> base;

    public SolutionComparator(Map<String, Double> base) {
        this.base = base;
    }

    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return -1;
        } else {
            return 1;
        }
    }
}
