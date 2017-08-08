package io.github.elysium_development.photonkatademo.core;

import java.util.List;

/**
 * class for navigating and getting lowest path for a particular row
 */
public class rowNavigator {

    //variable representing current row number
    private int row;

    //variable representing grid to be visited
    private Grid grid;

    //variable to update paths in pathcollector
    private PathCollector pathCollector;

    /**
     * constructor to initialize startrow,grid and pathcollector
     * @param startRow
     * @param grid
     * @param collector
     */
    public rowNavigator(int startRow, Grid grid, PathCollector collector) {
        if (grid == null) {
            throw new IllegalArgumentException("Grid is Null");
        } else if (collector == null) {
            throw new IllegalArgumentException("Collector is Null");
        } else if (startRow <= 0 || startRow > grid.getNoOfRows()) {
            throw new IllegalArgumentException("Value should be between grid boundaries");
        }

        this.row = startRow;
        this.grid = grid;
        this.pathCollector = collector;
    }

    /**
     * Method to get the lowest cost path for specified row
     * @return
     */
    public PathTracker getLowestCostPathForRow() {
        PathTracker initialPath = new PathTracker(grid.getNoOfColumns());

        visitPathsForRow(row, initialPath);

        return pathCollector.getLowestCostPath();
    }

    /**
     * Method to visit rows and updating path in pathcollector
     * @param row
     * @param path
     */
    private void visitPathsForRow(int row, PathTracker path) {
        if (canVisitRowOnPath(row, path)) {
            visitRowOnPath(row, path);
        }

        List<Integer> adjacentRows = grid.getRowsAdjacentTo(row);
        boolean currentPathAdded = false;

        for (int adjacentRow : adjacentRows) {
            if (canVisitRowOnPath(adjacentRow, path)) {
                PathTracker pathCopy = new PathTracker(path);
                visitPathsForRow(adjacentRow, pathCopy);
            } else if (!currentPathAdded) {
                pathCollector.addPath(path);
                currentPathAdded = true;
            }
        }
    }

    /**
     * Method to check if next row can be visited ie path is not complete and maximum cost is not exceeded.
     * @param row
     * @param path
     * @return
     */
    private boolean canVisitRowOnPath(int row, PathTracker path) {
        return !path.isComplete() && !nextMoveExceedsMaximumCost(row, path);
    }

    /**
     * Method to visit next row and update the patj and cost
     * @param row
     * @param path
     */
    private void visitRowOnPath(int row, PathTracker path) {
        int columnToVisit = path.getPathLength() + 1;
        path.addRowWithCost(row, grid.getValueAtRowAndColumn(row, columnToVisit));
    }

    /**
     * Method to check if vising next column exceeds maximum cost of 50
     * @param row
     * @param path
     * @return
     */
    private boolean nextMoveExceedsMaximumCost(int row, PathTracker path) {
        int nextColumn = path.getPathLength() + 1;
        return (path.getTotalCost() + grid.getValueAtRowAndColumn(row, nextColumn)) > PathTracker.MAXIMUM_COST;
    }
}
