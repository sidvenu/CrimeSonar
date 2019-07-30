package io.github.sidvenu.crimesonar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import io.github.sidvenu.crimesonar.adapters.ForceListAdapter;
import io.github.sidvenu.crimesonar.fragments.ForceDetailFragment;
import io.github.sidvenu.crimesonar.fragments.ForceListFragment;
import io.github.sidvenu.crimesonar.models.Force;
import io.github.sidvenu.crimesonar.models.ForceIdAndName;
import io.github.sidvenu.crimesonar.remote.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForcesActivity extends BaseActivity {
    public ForceListAdapter forceListAdapter;
    ApiManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force);
        forceListAdapter = new ForceListAdapter(this, new ArrayList<>());
        showForceList();
        functionality();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeForceDetailFragment();
    }

    private void removeForceDetailFragment() {
        Fragment forceDetailFragment = getSupportFragmentManager().findFragmentByTag(Constants.FORCE_DETAIL_FRAGMENT_TAG);
        if (forceDetailFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(forceDetailFragment);
            transaction.commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void functionality() {
        manager = ApiManager.Companion.getInstance();
        populateForcesList();
    }

    public void showForceList() {
        ForceListFragment forceListFragment = new ForceListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.force_fragments_container, forceListFragment, Constants.FORCE_LIST_FRAGMENT_TAG);
        transaction.commit();
        Log.v("TAG", "Hi");
    }

    public void showForceDetail(Force force, Bitmap bitmap) {
        // Create new fragment and transaction
        ForceDetailFragment forceDetailFragment = new ForceDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.FORCE_DETAIL_KEY, force);
        bundle.putParcelable(Constants.FORCE_DETAIL_BITMAP_KEY, bitmap);
        forceDetailFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack

        transaction.replace(R.id.force_fragments_container, forceDetailFragment, Constants.FORCE_DETAIL_FRAGMENT_TAG);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void populateForcesList() {
//        setFragmentRecyclerViewAdapter();
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

//    private void setFragmentRecyclerViewAdapter() {
//        ForceListFragment forceListFragment = (ForceListFragment) getSupportFragmentManager().findFragmentByTag(Constants.FORCE_LIST_FRAGMENT_TAG);
//        if (forceListFragment != null) {
//            forceListFragment.setRecyclerViewAdapter(forceListAdapter);
//        }
//    }

    List<ForceIdAndName> forcesIdAndName;
    int currentPosition;

    private void getForcesListWithIdAndNamesAndDisplay(Response<List<ForceIdAndName>> response) {
        forcesIdAndName = response.body();
        if (forcesIdAndName != null) {
            currentPosition = 0;
            getForceWithIdAndName(forcesIdAndName.get(currentPosition)).enqueue(new Callback<Force>() {
                @Override
                public void onResponse(Call<Force> call, Response<Force> response) {
                    Log.v("TAG", response.code() + ":response code");
                    Log.v("TAG", response.raw().toString());
                    if (response.isSuccessful()) {
                        addToForceList(response);
                        currentPosition++;
                    }
                    if (currentPosition < forcesIdAndName.size())
                        getForceWithIdAndName(forcesIdAndName.get(currentPosition)).enqueue(this);
                }

                @Override
                public void onFailure(Call<Force> call, Throwable t) {

                }
            });
//            for (ForceIdAndName forceIdAndName : forcesIdAndName) {
//                getForceWithIdAndNameAndAddToForcesList(forceIdAndName);
//            }
        }
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

    public void openCrimesActivity(View view) {
        startActivity(new Intent(ForcesActivity.this, CrimesActivity.class));
    }
}
