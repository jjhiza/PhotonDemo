package io.github.elysium_development.photonkatademo.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * class for traveling all grids and getting minimum cost path
 */
public class gridNavigator {

    //variable representing current grid
    private Grid grid;

    //comparator to compare for getting best path
    private PathComparison pathComparison;

    public gridNavigator(Grid grid) {
        if (grid == null) {
            throw new IllegalArgumentException("Grid can't be null");
        }

        this.grid = grid;
        pathComparison = new PathComparison();
    }

    /**
     * Method to get the lowest cost path for grid
     * @return
     */
    public PathTracker getLowestCostPathForGrid() {
        List<PathTracker> allAvailablePaths = new ArrayList<PathTracker>();
        for (int row = 1; row <= grid.getNoOfRows(); row++) {
            rowNavigator visitor = new rowNavigator(row, grid, new PathCollector());
            allAvailablePaths.add(visitor.getLowestCostPathForRow());
        }

        Collections.sort(allAvailablePaths, pathComparison);

        return allAvailablePaths.get(0);
    }
}
