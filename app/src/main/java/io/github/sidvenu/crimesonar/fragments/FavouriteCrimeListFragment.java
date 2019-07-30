package io.github.sidvenu.crimesonar.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import io.github.sidvenu.crimesonar.CrimesActivity;
import io.github.sidvenu.crimesonar.R;
import io.github.sidvenu.crimesonar.adapters.CrimeListAdapter;

public class FavouriteCrimeListFragment extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof CrimesActivity) {
            CrimesActivity crimesActivity = (CrimesActivity) getActivity();
            recyclerView = (RecyclerView) inflater.inflate(R.layout.favourite_crime_list_fragment, container, false);
            Log.v("TAG", crimesActivity.favouritesDbHelper.getCrimes().get(0).toString());
            recyclerView.setAdapter(new CrimeListAdapter(crimesActivity, crimesActivity.favouritesDbHelper.getCrimes()));
            return recyclerView;
        }
        return null;
    }

}
