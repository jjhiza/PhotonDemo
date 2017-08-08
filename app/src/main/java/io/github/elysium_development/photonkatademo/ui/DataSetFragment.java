package io.github.elysium_development.photonkatademo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import io.github.elysium_development.photonkatademo.R;
import io.github.elysium_development.photonkatademo.core.Grid;
import io.github.elysium_development.photonkatademo.core.PathTracker;
import io.github.elysium_development.photonkatademo.core.gridNavigator;
import io.github.elysium_development.photonkatademo.utils.GridConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.elysium_development.photonkatademo.core.Grid;


/**
 * Fragment to deal with test data sets.
 */
public class DataSetFragment extends Fragment {

    private View view;
    private Grid selectedGrid;
    private Grid loadedGrid;

    @BindView(R.id.buttonOne)
    Button buttonOne;
    @BindView(R.id.buttonTwo)
    Button buttonTwo;
    @BindView(R.id.buttonThree)
    Button buttonThree;
    @BindView(R.id.buttonGo)
    Button buttonGo;
    @BindView(R.id.results_success)
    TextView resultView;
    @BindView(R.id.results_total_cost)
    TextView totalCostView;
    @BindView(R.id.results_path_taken)
    TextView pathView;
    @BindView(R.id.grid_contents)
    TextView gridContentView;

    /**
     * Default constructor for state restore.
     */
    public DataSetFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dataset_fragment, container, false);

        ButterKnife.bind(this,view);

        return view;
    }

    /**
     * Method to clear text for all the displayed text views
     */
    private void clearResults() {
        resultView.setText("");
        totalCostView.setText(getResources().getText(R.string.no_results));
        pathView.setText("");
    }

    /**
     * Method to display path in proper format
     * @param path
     * @return
     */
    private String formatPath(PathTracker path) {
        StringBuilder builder = new StringBuilder();
        List<Integer> rows = path.getRowsTraversed();

        for (int i = 0; i < rows.size(); i++) {
            builder.append(rows.get(i));
            if (i < rows.size() - 1) {
                builder.append("\t");
            }
        }

        return builder.toString();
    }

    @OnClick({R.id.buttonOne,R.id.buttonTwo,R.id.buttonThree,R.id.buttonGo})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonOne:
                selectedGrid = GridConstants.GRID_1;
                updateGrid();
                break;
            case R.id.buttonTwo:
                selectedGrid = GridConstants.GRID_2;
                updateGrid();
                break;
            case R.id.buttonThree:
                selectedGrid = GridConstants.GRID_3;
                updateGrid();
                break;
            case R.id.buttonGo:
                gridNavigator visitor = new gridNavigator(loadedGrid);
                PathTracker bestPath = visitor.getLowestCostPathForGrid();

                if (bestPath.isSuccessful()) {
                    resultView.setText(R.string.yes);
                } else {
                    ((TextView) view.findViewById(R.id.results_success)).setText(R.string.no);
                }
                totalCostView.setText(Integer.toString(bestPath.getTotalCost()));
                pathView.setText(formatPath(bestPath));
                break;
        }
    }

    /**
     * Method to update Grid when user selects different data set.
     */
    private void updateGrid(){
        if (!selectedGrid.equals(loadedGrid)) {
            clearResults();
        }

        loadedGrid = selectedGrid;
        gridContentView.setText(loadedGrid.asDelimitedString("\t"));
        buttonGo.setEnabled(true);
    }
}
