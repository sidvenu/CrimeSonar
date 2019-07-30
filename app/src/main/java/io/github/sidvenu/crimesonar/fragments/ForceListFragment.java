package io.github.sidvenu.crimesonar.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import io.github.sidvenu.crimesonar.ForcesActivity;
import io.github.sidvenu.crimesonar.R;
import io.github.sidvenu.crimesonar.adapters.ForceListAdapter;

public class ForceListFragment extends Fragment {
    private RecyclerView recyclerView;
    private ForceListAdapter forceListAdapter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof ForcesActivity) {
            recyclerView = (RecyclerView) inflater.inflate(R.layout.force_list_fragment, container, false);
            recyclerView.setAdapter(((ForcesActivity) getActivity()).forceListAdapter);
            return recyclerView;
        }
        return null;
    }

//    public void setRecyclerViewAdapter(ForceListAdapter forceListAdapter) {
//        this.forceListAdapter = forceListAdapter;
//        recyclerView.setAdapter(forceListAdapter);
//    }

}
