package io.github.elysium_development.photonkatademo.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * initialization and Grid related operations.
 */
public class Grid {
    int[][] values;

    /**
     * constructor to initialize Grid
     * @param values
     */
    public Grid(int[][] values) {
        if (values.length < 1 || values.length > 10) {
            throw new IllegalArgumentException("No of rows should be between 1 and 10");
        } else if (values[0].length < 5 || values[0].length > 100) {
            throw new IllegalArgumentException("No of columns should be between 5 and 100");
        }

        this.values = values;
    }

    /**
     * Method returning the value at specified row and column number
     * @param row
     * @param column
     * @return
     */
    public int getValueAtRowAndColumn(int row, int column) {
        return values[row - 1][column - 1];
    }

    /**
     * Method returning number of rows in Grid
     * @return
     */
    public int getNoOfRows() {
        return values.length;
    }

    /**
     * Method returning number of columns in Grid
     * @return
     */
    public int getNoOfColumns() {
        return values[0].length;
    }

    /**
     * Method returning adjacent rows for the specified row
     * @param rowNumber
     * @return
     */
    public List<Integer> getRowsAdjacentTo(int rowNumber) {
        Set<Integer> adjacentRows = new HashSet<Integer>();

        if (isValidRowNumber(rowNumber)) {
            adjacentRows.add(rowNumber);
            adjacentRows.add(getRowAbove(rowNumber));
            adjacentRows.add(getRowBelow(rowNumber));
        }

        return new ArrayList<Integer>(adjacentRows);
    }

    /**
     * Method to format the display
     * @param delimiter
     * @return
     */
    public String asDelimitedString(String delimiter) {
        StringBuilder builder = new StringBuilder();

        for (int row = 0; row < values.length; row++) {
            for (int column = 0; column < values[row].length; column++) {
                builder.append(values[row][column]);
                if (column < values[row].length - 1) {
                    builder.append(delimiter);
                }
            }
            if (row < values.length - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }

    /**
     * Method to check if the row number is a valid row
     * @param rowNumber
     * @return
     */
    private boolean isValidRowNumber(int rowNumber) {
        return (rowNumber > 0) && (rowNumber <= values.length);
    }

    /**
     * Method returning row above the provided row number
     * @param rowNumber
     * @return
     */
    private int getRowAbove(int rowNumber) {
        int potentialRowNumber = rowNumber - 1;
        return (potentialRowNumber < 1) ? values.length : potentialRowNumber;
    }

    /**
     * Method returning row below the provided row number
     * @param rowNumber
     * @return
     */
    private int getRowBelow(int rowNumber) {
        int potentialRowNumber = rowNumber + 1;
        return (potentialRowNumber > values.length) ? 1 : potentialRowNumber;
    }
}
