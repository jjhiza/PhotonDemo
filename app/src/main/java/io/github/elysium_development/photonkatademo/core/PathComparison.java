package io.github.elysium_development.photonkatademo.core;

import java.util.Comparator;

/**
 * Class to compare paths based on length and cost
 */
public class PathComparison implements Comparator<PathTracker> {

    private static int SORT_LEFT_FIRST = -1;
    private static int SORT_RIGHT_FIRST = 1;
    private static int SORT_EQUAL = 0;

    /**
     * override compare method to compare path based on length and cost
     * @param leftPath
     * @param rightPath
     * @return
     */
    @Override
    public int compare(PathTracker leftPath, PathTracker rightPath) {
        int comparedLength = compareLengths(leftPath, rightPath);
        return (comparedLength != 0) ? comparedLength : compareCosts(leftPath, rightPath);
    }

    /**
     * Method to compare path lengths
     * @param leftPath
     * @param rightPath
     * @return
     */
    private int compareLengths(PathTracker leftPath, PathTracker rightPath) {
        int leftLength = getLengthFromPath(leftPath);
        int rightLength = getLengthFromPath(rightPath);

        return (leftLength > rightLength) ? SORT_LEFT_FIRST : (leftLength == rightLength) ? SORT_EQUAL : SORT_RIGHT_FIRST;
    }

    /**
     * Method to compare path costs
     * @param leftPath
     * @param rightPath
     * @return
     */
    private int compareCosts(PathTracker leftPath, PathTracker rightPath) {
        int leftCost = getCostFromPath(leftPath);
        int rightCost = getCostFromPath(rightPath);

        return (leftCost < rightCost) ? SORT_LEFT_FIRST : (leftCost == rightCost) ? SORT_EQUAL : SORT_RIGHT_FIRST;
    }

    /**
     * Method to get pathlength(rows traversed)
     * @param path
     * @return
     */
    private int getLengthFromPath(PathTracker path) {
        if (path != null) {
            return path.getPathLength();
        } else {
            return Integer.MIN_VALUE;
        }
    }

    /**
     * Method to get cost from path
     * @param path
     * @return
     */
    private int getCostFromPath(PathTracker path) {
        if (path != null) {
            return path.getTotalCost();
        } else {
            return Integer.MAX_VALUE;
        }
    }
}
