package io.github.elysium_development.photonkatademo.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.github.elysium_development.photonkatademo.R;
import io.github.elysium_development.photonkatademo.core.Grid;
import io.github.elysium_development.photonkatademo.core.PathTracker;
import io.github.elysium_development.photonkatademo.core.gridNavigator;
import io.github.elysium_development.photonkatademo.utils.GridUtilities;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Fragment for data sets provided by User.
 */
public class CustomDataInputFragment extends Fragment {

    @BindView(R.id.go_button)
    Button goButton;
    @BindView(R.id.custom_grid_contents)
    EditText customGridContents;
    @BindView(R.id.results_success)
    TextView resultView;
    @BindView(R.id.results_total_cost)
    TextView totalCostView;
    @BindView(R.id.results_path_taken)
    TextView pathView;
    @BindView(R.id.grid_contents)
    TextView gridContentView;

    /**
     * Default constructor for state restore
     */
    public CustomDataInputFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.custom_dataset_fragment, container, false);

        ButterKnife.bind(this,fragmentView);
        return fragmentView;
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

    /**
     * Method to check that no of rows and columns are between the specified limits.
     * @param contents
     * @return
     */
    private boolean gridContentsAreValid(int[][] contents) {
        if (contents.length < 1 || contents.length > 10 || contents[0].length < 5 || contents[0].length > 100) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to load the result such as path is successful, total path cost and path taken.
     * @param contents
     */
    private void loadGrid(int[][] contents) {
        Grid validGrid = new Grid(contents);
        gridNavigator visitor = new gridNavigator(validGrid);
        PathTracker bestPath = visitor.getLowestCostPathForGrid();

        if (bestPath.isSuccessful()) {
            resultView.setText(R.string.yes);
        } else {
            resultView.setText(R.string.no);
        }
        totalCostView.setText(Integer.toString(bestPath.getTotalCost()));
        pathView.setText(formatPath(bestPath));
        gridContentView.setText(validGrid.asDelimitedString("\t"));
    }

    /**
     * Method to enable Go button when user enter values
     * @param s
     */
    @OnTextChanged(value = R.id.custom_grid_contents)
    public void enableGoButton(CharSequence s) {
        goButton.setEnabled(!s.toString().isEmpty());
    }



    /**
     * onClick event when user selects the Go button
     */
        @OnClick(R.id.go_button)
        public void onClick(View view) {
            String gridString = customGridContents.getText().toString();
            int[][] potentialGridContents = GridUtilities.gridArrayFromString(gridString);
            if (gridContentsAreValid(potentialGridContents)) {
                loadGrid(potentialGridContents);
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.invalid_content)
                        .setMessage(R.string.invalid_grid_message)
                        .setPositiveButton(R.string.ok, null)
                        .show();
            }
        }
}
