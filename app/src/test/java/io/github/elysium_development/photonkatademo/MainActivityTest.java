package io.github.elysium_development.photonkatademo;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.github.elysium_development.photonkatademo.ui.MainActivity;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class)
public class MainActivityTest {

    private MainActivity mainActivity;

    @Before
    public void setUp() throws Exception
    {
        mainActivity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void activityShouldNotBeNull() throws Exception
    {
        assertNotNull(mainActivity);
    }
}
