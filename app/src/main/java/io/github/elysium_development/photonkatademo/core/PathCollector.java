package io.github.elysium_development.photonkatademo.core;

/**
 * class for collecting all available paths
 */
public class PathCollector {

    //variable to store best available path
    private PathTracker bestPath;

    //comparator used to compare new and earlier path
    private PathComparison comparator;

    /**
     * constructor for initialization
     */
    public PathCollector() {
        comparator = new PathComparison();
    }

    /**
     * Method returning best path available
     * @return
     */
    public PathTracker getLowestCostPath() {
        return bestPath;
    }

    /**
     * Method to add paths traveled to path collector
     * @param newPath
     */
    public void addPath(PathTracker newPath) {
        if (comparator.compare(newPath, bestPath) < 0) {
            bestPath = newPath;
        }
    }
}
