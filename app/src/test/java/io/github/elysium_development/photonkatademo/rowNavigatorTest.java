package io.github.elysium_development.photonkatademo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.elysium_development.photonkatademo.core.Grid;
import io.github.elysium_development.photonkatademo.core.PathCollector;
import io.github.elysium_development.photonkatademo.core.PathTracker;
import io.github.elysium_development.photonkatademo.core.rowNavigator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class rowNavigatorTest {

    private PathCollector realCollector;
    private PathCollector mockCollector;
    @Before
    public void setUp() {
        realCollector = new PathCollector();
        mockCollector = mock(PathCollector.class);

    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithoutAGrid() {
        rowNavigator subject = new rowNavigator(1, null, new PathCollector());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithoutACollector() {
        rowNavigator subject = new rowNavigator(1, new Grid(new int[][]{ { 1, 2, 3, 4, 5 } }), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithRowNumberLessThanGridRows() {
        rowNavigator subject = new rowNavigator(0, new Grid(new int[][]{ { 1, 2, 3, 4, 5 } }), new PathCollector());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotBeConstructedWithRowNumberGreaterThanGridRows() {
        rowNavigator subject = new rowNavigator(2, new Grid(new int[][]{ { 1, 2, 3, 4, 5 } }), new PathCollector());
    }

    @Test
    public void getBestPathForRowAccumulatesCostAcrossEntireRow() {
        Grid grid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });
        rowNavigator subject = new rowNavigator(1, grid, realCollector);

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.getTotalCost(), equalTo(15));
    }

    @Test
    public void getBestPathForRowDoesNotAccumumlateAnyCostIfFirstVisitExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathTracker.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        rowNavigator subject = new rowNavigator(1, grid, realCollector);

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.getTotalCost(), equalTo(0));
    }

    @Test
    public void getBestPathForRowDoesNotAccumulateCostAfterTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathTracker.MAXIMUM_COST, 1, 1, 1, 1 } });
        rowNavigator subject = new rowNavigator(1, grid, realCollector);

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.getTotalCost(), equalTo(50));
    }

    @Test
    public void getBestPathForRowTraversesEntireRow() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, 0 } });
        rowNavigator subject = new rowNavigator(1, grid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 1, 1, 1, 1 })
        );

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void getBestPathForRowDoesNotTraverseAnyRowsIfFirstVisitExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathTracker.MAXIMUM_COST + 1, 1, 1, 0, 0 } });
        rowNavigator subject = new rowNavigator(1, grid, realCollector);

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.getRowsTraversed().size(), equalTo(0));
    }

    @Test
    public void getBestPathForRowDoesNotTraverseRowsAfterTotalCostExceedsMaximum() {
        Grid grid = new Grid(new int[][]{ { PathTracker.MAXIMUM_COST, 1, 1, 1, 1 } });
        rowNavigator subject = new rowNavigator(1, grid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1 })
        );

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void getBestPathForRowIsSuccessfulIfGridIsCompletelyTraversed() {
        Grid grid = new Grid(new int[][]{ { 1, 1, 1, 1, PathTracker.MAXIMUM_COST - 4 } });
        rowNavigator subject = new rowNavigator(1, grid, realCollector);

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void getBestPathForRowIsNotSuccessfulIfGridIsNotTraversedAtAll() {
        Grid grid = new Grid(new int[][]{ { PathTracker.MAXIMUM_COST + 1, 0, 0, 0, 0 } });
        rowNavigator subject = new rowNavigator(1, grid, realCollector);

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowIsNotSuccessfulIfGridIsPartiallyTraversed() {
        Grid grid = new Grid(new int[][]{ { PathTracker.MAXIMUM_COST, 1, 1, 1, 1 } });
        rowNavigator subject = new rowNavigator(1, grid, realCollector);

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowIsNotSuccessfulIfLastVisitCausesTotalCostToExceedMaximumCost() {
        Grid grid = new Grid(new int[][]{ { 0, 0, 0, 0, PathTracker.MAXIMUM_COST + 1 } });
        rowNavigator subject = new rowNavigator(1, grid, realCollector);

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowHandlesNegativeNumbers() {
        Grid grid = new Grid(new int[][]{ { -5, -5, -5, -5, -5 } });
        rowNavigator subject = new rowNavigator(1, grid, realCollector);

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.getTotalCost(), equalTo(-25));
        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void getBestPathForRowVisitsOtherRowsInGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 5, 6, 7, 8, 9 }, { 1, 2, 3, 4, 5 } });
        rowNavigator subject = new rowNavigator(2, twoRowGrid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2, 2, 2, 2, 2 })
        );

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.getTotalCost(), equalTo(15));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
        assertThat(result.isSuccessful(), is(true));
    }

    @Test
    public void getBestPathForRowHandlesMaximumCostForOtherRowsInGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { PathTracker.MAXIMUM_COST - 1, 2, 1, 1, 1 } });
        rowNavigator subject = new rowNavigator(2, twoRowGrid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 2 })
        );

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.getTotalCost(), equalTo(49));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
        assertThat(result.isSuccessful(), is(false));
    }

    @Test
    public void getBestPathForRowVisitsAllPathsFromThatRowThroughFullTwoRowGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, 2, 1, 1, 1 } });
        rowNavigator subject = new rowNavigator(1, twoRowGrid, mockCollector);

        subject.getLowestCostPathForRow();

        verify(mockCollector, times(16)).addPath(any(PathTracker.class));
    }

    @Test
    public void getBestPathForRowVisitsFewerPathsThroughGridWithHighCosts() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, PathTracker.MAXIMUM_COST, 1, 1, 1 } });
        rowNavigator subject = new rowNavigator(1, twoRowGrid, mockCollector);

        subject.getLowestCostPathForRow();

        verify(mockCollector, times(9)).addPath(any(PathTracker.class));
    }

    @Test
    public void getBestPathForRowReturnsLongerPathsAheadOfShorterPathsWithLowerCost() {
        Grid twoRowGrid = new Grid(new int[][]{
                { 1, 1, PathTracker.MAXIMUM_COST, PathTracker.MAXIMUM_COST, 1 },
                { 1, 10, 10, PathTracker.MAXIMUM_COST, 5 } });
        rowNavigator subject = new rowNavigator(1, twoRowGrid, realCollector);
        List<Integer> expectedPath = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 1, 2 })
        );

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.getTotalCost(), equalTo(12));
        assertThat(result.getRowsTraversed(), equalTo(expectedPath));
    }

    @Test
    public void getBestPathForRowVisitsAllPathsFromThatRowThroughFullThreeRowGrid() {
        Grid threeRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, 2, 2, 2, 2 }, { 0, 3, 3, 3, 3 } });
        rowNavigator subject = new rowNavigator(1, threeRowGrid, mockCollector);

        subject.getLowestCostPathForRow();

        verify(mockCollector, times(81)).addPath(any(PathTracker.class));
    }

    @Test
    public void getBestPathForRowCanWrapToFourthRowInFullFourRowGrid() {
        Grid fourRowGrid = new Grid(new int[][]{ { 1, 5, 5, 5, 5 }, { 0, 2, 2, 2, 2 }, { 0, 3, 3, 3, 3 }, { 0, 1, 1, 1, 1 } });
        rowNavigator subject = new rowNavigator(1, fourRowGrid, realCollector);

        PathTracker result = subject.getLowestCostPathForRow();

        assertThat(result.getTotalCost(), equalTo(5));
    }

    @Test
    public void getBestPathForRowVisitsAllPossiblePathsFromThatRowThroughFullFourRowGrid() {
        Grid fourRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 0, 2, 2, 2, 2 }, { 0, 3, 3, 3, 3 }, { 0, 4, 4, 4, 4 } });
        rowNavigator subject = new rowNavigator(1, fourRowGrid, mockCollector);

        subject.getLowestCostPathForRow();

        verify(mockCollector, times(81)).addPath(any(PathTracker.class));
    }
}
