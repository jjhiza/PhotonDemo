package io.github.elysium_development.photonkatademo;

import android.widget.TextView;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.github.elysium_development.photonkatademo.ui.DataSetFragment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class DataSetFragmentTest {

    @Test
    public void shouldNotBeNull() throws Exception
    {
        DataSetFragment fragment = new DataSetFragment();
        startFragment(fragment);
        assertNotNull(fragment);
    }


    @Test
    public void whenTheFragmentViewIsCreatedThenTheViewShouldBePopulated() {
        DataSetFragment fragment = new DataSetFragment();
        startFragment(fragment);

        TextView totalCostView = (TextView) fragment.getView().findViewById(R.id.results_total_cost);
        assertEquals("No results", totalCostView.getText());
    }

}
