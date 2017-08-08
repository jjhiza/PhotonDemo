package io.github.elysium_development.photonkatademo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.elysium_development.photonkatademo.core.Grid;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class GridTest {

    @Test(expected = IllegalArgumentException.class)
    public void checkGridHasAtleastOneRow() {
        int values[][] = new int[0][0];
        Grid subject = new Grid(values);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkGridHasTenOrLessRows() {
        int values[][] = new int[15][7];
        Grid subject = new Grid(values);
    }

    @Test(expected = IllegalArgumentException.class)
    public void CheckGridHaveAtLeastFiveColumns() {
        int values[][] = new int[1][3];
        Grid subject = new Grid(values);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkGridHasLessThanHundredColumns() {
        int values[][] = new int[1][120];
        Grid subject = new Grid(values);
    }

    @Test
    public void checksValueAtRowAndColumn() {
        int values[][] = new int[][]{ { 3, 5, 2, 5, 1 }, { 7, 3, 5, 9, 7 } };
        Grid subject = new Grid(values);

        assertThat(subject.getValueAtRowAndColumn(2, 1), equalTo(7));
        assertThat(subject.getValueAtRowAndColumn(1, 5), equalTo(1));
    }

    @Test
    public void returnsRowCount() {
        Grid oneRowGrid = new Grid(new int[][]{ { 5, 1, 5, 2, 3 }});
        Grid twoRowGrid = new Grid(new int[][]{{1,2,3,4,5},{6,7,8,9,10}});
        Grid threeRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9 , 10 }, { 11, 12, 13, 14, 15 }});

        assertThat(oneRowGrid.getNoOfRows(), equalTo(1));
        assertThat(twoRowGrid.getNoOfRows(), equalTo(2));
        assertThat(threeRowGrid.getNoOfRows(), equalTo(3));
    }

    @Test
    public void returnsColumnCount() {
        Grid fiveColumnGrid = new Grid(new int[][]{ { 5, 1, 5, 2, 3, 8 } });
        Grid sevenColumnGrid = new Grid(new int[][]{ { 8, 3, 3, 3, 3, 5, 4 ,4, 5, 5} });

        assertThat(fiveColumnGrid.getNoOfColumns(), equalTo(6));
        assertThat(sevenColumnGrid.getNoOfColumns(), equalTo(10));
    }

    @Test
    public void getAdjacentRowsForOneRowGrid() {
        Grid oneRowGrid = new Grid(new int[][]{ { 2, 2, 3, 3, 3 } });
        List<Integer> adjacentRows = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1 })
        );

        assertThat(oneRowGrid.getRowsAdjacentTo(1), equalTo(adjacentRows));
    }

    @Test
    public void getAdjacentRowsForTwoRowGrid() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 } });
        List<Integer> adjacentRows = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 2 })
        );

        assertThat(twoRowGrid.getRowsAdjacentTo(1), equalTo(adjacentRows));
        assertThat(twoRowGrid.getRowsAdjacentTo(2), equalTo(adjacentRows));
    }

    @Test
    public void getAdjacentRowsForThreeRowsGrid() {
        Grid threeRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 13, 14, 15 } });
        List<Integer> adjacentRows = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 2, 3 })
        );

        assertThat(threeRowGrid.getRowsAdjacentTo(1), equalTo(adjacentRows));
        assertThat(threeRowGrid.getRowsAdjacentTo(2), equalTo(adjacentRows));
        assertThat(threeRowGrid.getRowsAdjacentTo(3), equalTo(adjacentRows));
    }

    @Test
    public void getAdjacentRowsReturnsNothingWhenInvalidRowIsPassed() {
        Grid oneRowGrid = new Grid(new int[][]{ { 2, 2, 2, 2, 2 } });
        assertThat(oneRowGrid.getRowsAdjacentTo(0).size(), equalTo(0));
        assertThat(oneRowGrid.getRowsAdjacentTo(2).size(), equalTo(0));
    }


    @Test
    public void getAdjacentRowsReturnsWrappedRowsWhenMoreThanThreeRows() {
        Grid fourRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 6, 7, 8, 9, 10 }, { 11, 12, 12, 14, 15 }, { 16, 17, 18, 19, 20 } });
        List<Integer> expectedRows = new ArrayList<Integer>(
                Arrays.asList(new Integer[]{ 1, 2, 4 })
        );

        assertThat(fourRowGrid.getRowsAdjacentTo(1), equalTo(expectedRows));
    }

    @Test
    public void asDelimitedStringOutputsValuesForARowSeparatedByChosenDelimiter() {
        Grid oneRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 } });

        assertThat(oneRowGrid.asDelimitedString("|"), equalTo("1|2|3|4|5"));
    }

    @Test
    public void asDelimitedStringOutputsValuesForMultipleRowsWithTrailingLineBreaks() {
        Grid twoRowGrid = new Grid(new int[][]{ { 1, 2, 3, 4, 5 }, { 2, 4, 6, 8, 10 } });

        assertThat(twoRowGrid.asDelimitedString("\t"), equalTo("1\t2\t3\t4\t5\n2\t4\t6\t8\t10"));
    }
}