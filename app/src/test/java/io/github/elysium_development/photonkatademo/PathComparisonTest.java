package io.github.elysium_development.photonkatademo;

import org.junit.Before;
import org.junit.Test;

import io.github.elysium_development.photonkatademo.core.PathComparison;
import io.github.elysium_development.photonkatademo.core.PathTracker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class PathComparisonTest {

    private PathComparison subject;

    private PathTracker lowerCostPath;
    private PathTracker higherCostPath;

    private PathTracker shorterPath;
    private PathTracker longerPath;

    @Before
    public void setUp() {
        subject = new PathComparison();

        lowerCostPath = new PathTracker(1);
        lowerCostPath.addRowWithCost(1, 1);

        higherCostPath = new PathTracker(1);
        higherCostPath.addRowWithCost(1, 10);

        shorterPath = new PathTracker(5);
        shorterPath.addRowWithCost(1, 9);

        longerPath = new PathTracker(5);
        longerPath.addRowWithCost(1, 5);
    }

    @Test
    public void returnsNegativeOneIfFirstPathIsLongerThanSecond() {
        assertThat(subject.compare(longerPath, shorterPath), equalTo(-1));
    }

    @Test
    public void returnsPositiveOneIfFirstPathIsShorterThanSecond() {
        assertThat(subject.compare(shorterPath, longerPath), equalTo(1));
    }

    @Test
    public void returnsZeroIfPathsHaveSameLengthAndCost() {
        assertThat(subject.compare(shorterPath, shorterPath), equalTo(0));
    }

    @Test
    public void returnsNegativeOneIfFirstPathHasLowerCostThanSecondWithTheSameLength() {
        assertThat(subject.compare(lowerCostPath, higherCostPath), equalTo(-1));
    }

    @Test
    public void returnsPositiveOneIfFirstPathHasLowerCostThanSecondWithTheSameLength() {
        assertThat(subject.compare(higherCostPath, lowerCostPath), equalTo(1));
    }

    @Test
    public void returnsPositiveOneIfFirstPathIsNull() {
        assertThat(subject.compare(null, lowerCostPath), equalTo(1));
    }

    @Test
    public void returnsNegativeOneIfLastPathIsNull() {
        assertThat(subject.compare(lowerCostPath, null), equalTo(-1));
    }

    @Test
    public void returnsZeroIfBothPathsAreNull() {
        assertThat(subject.compare(null, null), equalTo(0));
    }
}