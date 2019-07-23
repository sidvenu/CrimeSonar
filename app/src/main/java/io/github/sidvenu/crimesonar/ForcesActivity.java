package io.github.sidvenu.crimesonar;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.github.sidvenu.crimesonar.adapters.ForceListAdapter;
import io.github.sidvenu.crimesonar.fragments.ForceListFragment;
import io.github.sidvenu.crimesonar.models.Force;
import io.github.sidvenu.crimesonar.models.ForceIdAndName;
import io.github.sidvenu.crimesonar.remote.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForcesActivity extends BaseActivity {
    ForceListAdapter forceListAdapter = new ForceListAdapter(new ArrayList<>());
    ApiManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force);
    }

    @Override
    protected void onStart() {
        super.onStart();
        functionality();
    }

    private void functionality() {
        manager = ApiManager.Companion.getInstance();

        populateForcesList();
    }

    private void populateForcesList() {
        setFragmentRecyclerViewAdapter();
        getForcesIdAndNameFromApi().enqueue(new Callback<List<ForceIdAndName>>() {
            @Override
            public void onResponse(Call<List<ForceIdAndName>> call, Response<List<ForceIdAndName>> response) {
                Log.v("TAG", response.code() + ":response code");
                if (response.isSuccessful()) {
                    getForcesListWithIdAndNamesAndDisplay(response);
                }
            }

            @Override
            public void onFailure(Call<List<ForceIdAndName>> call, Throwable t) {

            }
        });
    }

    private void setFragmentRecyclerViewAdapter() {
        ForceListFragment forceListFragment = (ForceListFragment) getSupportFragmentManager().findFragmentById(R.id.force_list_fragment);
        if (forceListFragment != null) {
            forceListFragment.setRecyclerViewAdapter(forceListAdapter);
        }
    }

    private void getForcesListWithIdAndNamesAndDisplay(Response<List<ForceIdAndName>> response) {
        List<ForceIdAndName> forcesIdAndName = response.body();
        if (forcesIdAndName != null) {
            for (ForceIdAndName forceIdAndName : forcesIdAndName) {
                getForceWithIdAndNameAndAddToForcesList(forceIdAndName);
            }
        }
    }

    private void getForceWithIdAndNameAndAddToForcesList(ForceIdAndName forceIdAndName) {
        getForceWithIdAndName(forceIdAndName).enqueue(new Callback<Force>() {
            @Override
            public void onResponse(Call<Force> call, Response<Force> response) {
                Log.v("TAG", response.code() + ":response code");
                Log.v("TAG", response.raw().toString());
                if (response.isSuccessful()) {
                    addToForceList(response);
                }
            }

            @Override
            public void onFailure(Call<Force> call, Throwable t) {

            }
        });
    }

    private void addToForceList(Response<Force> response) {
        Force force = response.body();
        if (force != null) {
            Log.v("TAG", "forceURL: " + force.getUrl());
            forceListAdapter.addItem(force);
        }
    }

    private Call<Force> getForceWithIdAndName(ForceIdAndName forceIdAndName) {
        return manager.getPoliceService().getForce(forceIdAndName.getId());
    }

    private Call<List<ForceIdAndName>> getForcesIdAndNameFromApi() {
        return manager.getPoliceService().getForcesIdAndName();
    }
}
