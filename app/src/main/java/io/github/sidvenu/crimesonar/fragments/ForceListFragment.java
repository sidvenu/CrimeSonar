package io.github.sidvenu.crimesonar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.github.sidvenu.crimesonar.ForcesActivity;
import io.github.sidvenu.crimesonar.R;
import io.github.sidvenu.crimesonar.adapters.ForceListAdapter;
import io.github.sidvenu.crimesonar.models.Force;

public class ForceListFragment extends Fragment {
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof ForcesActivity) {
            recyclerView = (RecyclerView) inflater.inflate(R.layout.list_force, container, false);
            return recyclerView;
        }
        return null;
    }

    public void setRecyclerViewAdapter(ForceListAdapter forceListAdapter) {
        recyclerView.setAdapter(forceListAdapter);
    }

}
