package io.github.elysium_development.photonkatademo.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.github.elysium_development.photonkatademo.ui.CustomDataInputFragment;
import io.github.elysium_development.photonkatademo.ui.DataSetFragment;
import io.github.elysium_development.photonkatademo.utils.GridConstants;

/**
 * custom view pager adapter
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    /**
     * Method returning fragment based on position
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DataSetFragment();
            case 1:
                return new CustomDataInputFragment();
            default:
                return null;
        }
    }

    /**
     * Method returning number of fragments
     * @return
     */
    @Override
    public int getCount() {
        return GridConstants.NO_OF_FRAGMENTS;
    }

    /**
     * method returning view pager title based on position
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                //return Resources.getSystem().getString(R.string.sample_dataset);
                return "Sample DataSet Grids";
            case 1:
                //return Resources.getSystem().getString(R.string.custom_dataset);
                return "Custom DataSet Grid";
            default:
                return null;
        }
    }
}
