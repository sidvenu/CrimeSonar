package io.github.sidvenu.crimesonar.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.sidvenu.crimesonar.Constants;
import io.github.sidvenu.crimesonar.CrimesActivity;
import io.github.sidvenu.crimesonar.R;
import io.github.sidvenu.crimesonar.Utils;
import io.github.sidvenu.crimesonar.models.Crime;
import io.github.sidvenu.crimesonar.models.Location;
import io.github.sidvenu.crimesonar.models.Outcome;

public class CrimeDetailFragment extends Fragment {
    Crime crime;

    @BindView(R.id.crime_detail_category)
    TextView crimeCategory;

    @BindView(R.id.crime_detail_id)
    TextView crimeID;

    @BindView(R.id.crime_detail_location)
    TextView crimeLocation;

    @BindView(R.id.crime_detail_lat)
    TextView crimeLat;

    @BindView(R.id.crime_detail_lng)
    TextView crimeLng;

    @BindView(R.id.crime_detail_outcome)
    TextView crimeOutcome;

    @BindView(R.id.crime_detail_outcome_date)
    TextView crimeOutcomeDate;

    @BindView(R.id.crime_detail_notes)
    TextView crimeNotes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof CrimesActivity) {
//            if (container != null)
//                container.removeAllViews();

            View view = inflater.inflate(R.layout.crime_detail_fragment, container, false);
            ButterKnife.bind(this, view);
            Bundle arguments = getArguments();
            if (arguments != null) {
                crime = arguments.getParcelable(Constants.CRIME_DETAIL_KEY);

                if (crime != null) {
                    crimeCategory.setText(Utils.getHtmlString(crime.getCategory()));
                    crimeID.setText(Utils.getHtmlString(crime.getId()));
                    crimeLocation.setText(Utils.getHtmlString(crime.getLocationName()));

                    Location location = crime.getLocation();
                    if (location != null) {
                        crimeLat.setText(Utils.getHtmlString(location.getLatitude()));
                        crimeLng.setText(Utils.getHtmlString(location.getLongitude()));
                    }

                    Outcome outcome = crime.getOutcome();
                    if (outcome != null) {
                        crimeOutcome.setText(Utils.getHtmlString(outcome.getCategory()));
                        crimeOutcomeDate.setText(Utils.getHtmlString(outcome.getDate()));
                    }
                    if (!TextUtils.isEmpty(crime.getContext()))
                        crimeNotes.setText(Utils.getHtmlString(crime.getContext()));
                }
            }
            return view;
        }
        return null;
    }


}
