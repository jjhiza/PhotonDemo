package io.github.elysium_development.photonkatademo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.elysium_development.photonkatademo.core.Grid;
import io.github.elysium_development.photonkatademo.core.PathTracker;
import io.github.elysium_development.photonkatademo.core.gridNavigator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class gridNavigatorTest {

    @Test(expected = IllegalArgumentException.class)
    public void checkGridVisitorForNullValue() {
        gridNavigator subject = new gridNavigator(null);
    }

    @Test
    public void findPathForExampleOne() {
        Grid grid = new Grid(new int[][]{
                { 3, 4, 1, 2, 8, 6 },
                { 6, 1, 8, 2, 7, 4 },
                { 5, 9, 3, 9, 9, 5 },
                { 8, 4, 1, 3, 2, 6 },
                { 3, 7, 2, 8, 6, 4 }
        });

        gridNavigator visitor = new gridNavigator(grid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 2, 3, 4, 4, 5 })
        );

        PathTracker solution = visitor.getLowestCostPathForGrid();
        assertThat(solution.isSuccessful(), is(true));
        assertThat(solution.getTotalCost(), equalTo(16));
        assertThat(solution.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void findsPathForExampleTwo() {
        Grid grid = new Grid(new int[][]{
                { 3, 4, 1, 2, 8, 6 },
                { 6, 1, 8, 2, 7, 4 },
                { 5, 9, 3, 9, 9, 5 },
                { 8, 4, 1, 3, 2, 6 },
                { 3, 7, 2, 1, 2, 3 }
        });

        gridNavigator visitor = new gridNavigator(grid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 2, 1, 5, 4, 5 })
        );

        PathTracker solution = visitor.getLowestCostPathForGrid();
        assertThat(solution.isSuccessful(), is(true));
        assertThat(solution.getTotalCost(), equalTo(11));
        assertThat(solution.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void findsPathForExampleThree() {
        Grid grid = new Grid(new int[][]{
                { 19, 10, 19, 10, 19 },
                { 21, 23, 20, 19, 12 },
                { 20, 12, 20, 11, 10 }
        });

        gridNavigator visitor = new gridNavigator(grid);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 1, 1 })
        );

        PathTracker solution = visitor.getLowestCostPathForGrid();
        assertThat(solution.isSuccessful(), is(false));
        assertThat(solution.getTotalCost(), equalTo(48));
        assertThat(solution.getRowsTraversed(), equalTo(expectedPath));
    }
}

