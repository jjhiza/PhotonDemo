package io.github.elysium_development.photonkatademo;

import org.junit.Before;
import org.junit.Test;

import io.github.elysium_development.photonkatademo.core.PathCollector;
import io.github.elysium_development.photonkatademo.core.PathTracker;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class PathCollectorTest {

    private PathCollector collector;

    @Before
    public void setUp() {
        collector = new PathCollector();
    }

    @Test
    public void collectorShouldReturnsNullIfNoPathIsAdded() {
        assertThat(collector.getLowestCostPath(), is(nullValue()));
    }

    @Test
    public void collectorReturnsPathAddedIfOnlyOnePathAdded() {
        PathTracker expectedPath = new PathTracker(5);
        expectedPath.addRowWithCost(1, 5);

        collector.addPath(expectedPath);

        assertThat(collector.getLowestCostPath(), equalTo(expectedPath));
    }

    @Test
    public void collectorReturnsLongerPathOfTwoPathsAdded() {
        PathTracker longerPath = new PathTracker(5);
        longerPath.addRowWithCost(1, 5);
        longerPath.addRowWithCost(1, 5);
        PathTracker shorterPath = new PathTracker(5);
        shorterPath.addRowWithCost(1, 10);

        collector.addPath(longerPath);
        collector.addPath(shorterPath);

        assertThat(collector.getLowestCostPath(), equalTo(longerPath));
    }

    @Test
    public void collectorReturnsLowerCostPathOfTwoEqualLengthPathsAdded() {
        PathTracker lowCostPath = new PathTracker(5);
        lowCostPath.addRowWithCost(1, 5);
        lowCostPath.addRowWithCost(1, 5);
        PathTracker highCostPath = new PathTracker(5);
        highCostPath.addRowWithCost(1, 10);
        highCostPath.addRowWithCost(1, 20);

        collector.addPath(lowCostPath);
        collector.addPath(highCostPath);

        assertThat(collector.getLowestCostPath(), equalTo(lowCostPath));
    }

    @Test
    public void collectorPrefersExistingPathIfPathsAreEqual() {
        PathTracker firstPath = new PathTracker(5);
        firstPath.addRowWithCost(1, 5);
        firstPath.addRowWithCost(1, 5);
        PathTracker secondPath = new PathTracker(5);
        secondPath.addRowWithCost(1, 5);
        secondPath.addRowWithCost(1, 5);

        collector.addPath(firstPath);
        collector.addPath(secondPath);

        assertThat(collector.getLowestCostPath(), equalTo(firstPath));
    }
}