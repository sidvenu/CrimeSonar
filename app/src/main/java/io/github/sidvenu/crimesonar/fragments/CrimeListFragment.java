package io.github.sidvenu.crimesonar.fragments;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import io.github.sidvenu.crimesonar.CrimesActivity;
import io.github.sidvenu.crimesonar.R;

public class CrimeListFragment extends Fragment {
    private View rootView;
    private CrimesActivity crimesActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof CrimesActivity) {
            crimesActivity = (CrimesActivity) getActivity();
            rootView = inflater.inflate(R.layout.crime_list_fragment, container, false);
            RecyclerView recyclerView = rootView.findViewById(R.id.crime_list);
            recyclerView.setAdapter(crimesActivity.crimeListAdapter);

            rootView.findViewById(R.id.latitude).setOnClickListener(v -> changeRequestData("Latitude", getLat(), (TextView) v, InputType.TYPE_NUMBER_FLAG_DECIMAL));
            rootView.findViewById(R.id.longitude).setOnClickListener(v -> changeRequestData("Longitude", getLng(), (TextView) v, InputType.TYPE_NUMBER_FLAG_DECIMAL));
            rootView.findViewById(R.id.date_selector).setOnClickListener(v -> changeRequestData("Date", getDate(), rootView.findViewById(R.id.date), InputType.TYPE_CLASS_TEXT));
            return rootView;
        }
        return null;
    }

    void changeRequestData(String dialogTitle, String placeholderTextOnEditText, TextView outputTextView, int inputType) {
        AlertDialog.Builder builder = new AlertDialog.Builder(crimesActivity);
        builder.setTitle(dialogTitle);

        // Set up the input
        final EditText input = new EditText(getContext());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the textInputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
        input.setInputType(inputType);
        input.setText(placeholderTextOnEditText);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
            outputTextView.setText(input.getText().toString());
            refreshData();
        });
        builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());

        builder.show();
    }

    void refreshData() {
        crimesActivity.reloadData(getLat(), getLng(), getDate());
    }

    String getLat() {
        return getTextWithID(R.id.latitude);
    }

    String getLng() {
        return getTextWithID(R.id.longitude);
    }

    String getDate() {
        return getTextWithID(R.id.date);
    }

    String getTextWithID(int id) {
        return (String) ((TextView) rootView.findViewById(id)).getText();
    }

}
