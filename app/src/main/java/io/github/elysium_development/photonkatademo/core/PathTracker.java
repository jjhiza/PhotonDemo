package io.github.elysium_development.photonkatademo.core;

import java.util.ArrayList;
import java.util.List;

/**
 * class to track path, and the cost for traveling the path.
 */
public class PathTracker {

    //Maximum cost for path
    public static int MAXIMUM_COST = 50;

    //Arraylist to maintain row's traversed
    private List<Integer> rowsTraversed = new ArrayList<Integer>();

    //variable to store total cost
    private int totalCost = 0;

    //variable to store expected length for the path to be complete
    private int expectedLength = 0;

    /**
     * constructor for initialization
     * @param expectedLength
     */
    public PathTracker(int expectedLength) {
        this.expectedLength = expectedLength;
    }

    /**
     * constuctor for initialization of pathtracker
     * @param anotherPathTracker
     */
    public PathTracker(PathTracker anotherPathTracker) {
        this.totalCost = anotherPathTracker.totalCost;
        this.expectedLength = anotherPathTracker.expectedLength;
        for (int rowTraversed : anotherPathTracker.rowsTraversed) {
            this.rowsTraversed.add(rowTraversed);
        }
    }

    /**
     * Method to get list of rows traveled
     * @return
     */
    public List<Integer> getRowsTraversed() {
        return rowsTraversed;
    }

    /**
     * Method to return total cost for path
     * @return
     */
    public int getTotalCost() {
        return totalCost;
    }

    /**
     * Method to get path length
     * @return
     */
    public int getPathLength() {
        return rowsTraversed.size();
    }

    /**
     * Method to add rows traveled and total cost
     * @param row
     * @param cost
     */
    public void addRowWithCost(int row, int cost) {
        rowsTraversed.add(row);
        totalCost += cost;
    }

    /**
     * Method to check if path is complete
     * @return
     */
    public boolean isComplete() {
        return rowsTraversed.size() == expectedLength;
    }

    /**
     * Method to check if path is succesful ie complete and the cost is less than maximum cost limit.
     * @return
     */
    public boolean isSuccessful() {
        return isComplete() && !isOverCost();
    }

    /**
     * Method to check if total cost exceeds maximum cost limit of 50
     * @return
     */
    public boolean isOverCost() {
        return totalCost > MAXIMUM_COST;
    }
}
