package io.github.sidvenu.crimesonar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import io.github.sidvenu.crimesonar.adapters.CrimeListAdapter;
import io.github.sidvenu.crimesonar.database.FavouritesDbHelper;
import io.github.sidvenu.crimesonar.fragments.CrimeDetailFragment;
import io.github.sidvenu.crimesonar.fragments.CrimeListFragment;
import io.github.sidvenu.crimesonar.fragments.FavouriteCrimeListFragment;
import io.github.sidvenu.crimesonar.models.Crime;
import io.github.sidvenu.crimesonar.remote.ApiManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimesActivity extends BaseActivity {

    public CrimeListAdapter crimeListAdapter;
    ApiManager manager;
    String lat, lng, date;
    public FavouritesDbHelper favouritesDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favouritesDbHelper = new FavouritesDbHelper(this);
        setContentView(R.layout.activity_crimes);
        lat = getResources().getString(R.string.default_lat);
        lng = getResources().getString(R.string.default_lng);
        date = getResources().getString(R.string.default_date);
        crimeListAdapter = new CrimeListAdapter(this, new ArrayList<>());
        showCrimeList();
        functionality();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeCrimeDetailFragment();
    }

    private void removeCrimeDetailFragment() {
        Fragment crimeDetailFragment = getSupportFragmentManager().findFragmentByTag(Constants.CRIME_DETAIL_FRAGMENT_TAG);
        if (crimeDetailFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(crimeDetailFragment);
            transaction.commit();
        }
    }

    private void functionality() {
        manager = ApiManager.Companion.getInstance();
        populateCrimesList();
    }

    public void reloadData(String lat, String lng, String date) {
        this.lat = lat;
        this.lng = lng;
        this.date = date;
        crimeListAdapter.crimeList.clear();
        crimeListAdapter.notifyDataSetChanged();
        populateCrimesList();
    }

    private void populateCrimesList() {
        getCrimesWithLatAndLon().enqueue(new Callback<List<Crime>>() {
            @Override
            public void onResponse(Call<List<Crime>> call, Response<List<Crime>> response) {
                Log.v("TAG", "crime:"+response.code());
                if(response.isSuccessful()){
                    setCrimesList(response);
                }
            }

            @Override
            public void onFailure(Call<List<Crime>> call, Throwable t) {

            }
        });
    }

    private void setCrimesList(Response<List<Crime>> response) {
//        crimeListAdapter.crimeList = response.body();
        crimeListAdapter.swap(response.body());
        Log.v("TAG", crimeListAdapter.getItemCount()+"");
        setFavouritesFromDatabase();
//        crimeListAdapter.notifyDataSetChanged();
    }

    private void setFavouritesFromDatabase() {
        List<String> favouriteCrimeIDs = favouritesDbHelper.getCrimeIDs();
    }

    private Call<List<Crime>> getCrimesWithLatAndLon() {
        return manager.getPoliceService().getCrimesAtLocation(lat, lng, date);
    }

    private void showCrimeList() {
        CrimeListFragment crimeListFragment = new CrimeListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.crimes_fragments_container, crimeListFragment, Constants.CRIME_LIST_FRAGMENT_TAG);
        transaction.commit();
    }

    public void showFavouriteCrimesList(View view) {
        FavouriteCrimeListFragment favouriteCrimeListFragment = new FavouriteCrimeListFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.crimes_fragments_container, favouriteCrimeListFragment, Constants.CRIME_LIST_FRAGMENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showCrimeDetail(Crime crime) {
        Log.v("TAG", crime.getCategory()+"");
        // Create new fragment and transaction
        CrimeDetailFragment crimeDetailFragment = new CrimeDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.CRIME_DETAIL_KEY, crime);
        crimeDetailFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack

        transaction.replace(R.id.crimes_fragments_container, crimeDetailFragment, Constants.CRIME_DETAIL_FRAGMENT_TAG);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
