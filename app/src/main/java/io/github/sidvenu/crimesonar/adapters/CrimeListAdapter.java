package io.github.sidvenu.crimesonar.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.sidvenu.crimesonar.CrimesActivity;
import io.github.sidvenu.crimesonar.R;
import io.github.sidvenu.crimesonar.models.Crime;

public class CrimeListAdapter extends RecyclerView.Adapter<CrimeListAdapter.crimeListViewHolder> {

    public List<Crime> crimeList;
    CrimesActivity crimesActivity;

    public CrimeListAdapter(CrimesActivity crimesActivity, List<Crime> crimeList) {
        this.crimesActivity = crimesActivity;
        this.crimeList = crimeList;
    }

    public void swap(List<Crime> crimeList) {
        if (this.crimeList != null) {
            this.crimeList.clear();
            this.crimeList.addAll(crimeList);
        } else {
            this.crimeList = crimeList;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return crimeList.size();
    }

    @NonNull
    @Override
    public crimeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View crimeItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crime, parent, false);
        return new crimeListViewHolder(crimeItem);
    }

    @Override
    public void onBindViewHolder(@NonNull crimeListViewHolder holder, int position) {
        initialiseViewsInCrimeItem(holder, position);
    }

    private void initialiseViewsInCrimeItem(@NonNull crimeListViewHolder holder, int position) {
        Crime crime = crimeList.get(position);
        holder.crimeCategory.setText(crime.getCategory());
        holder.crimeID.setText(crime.getId());
        holder.crimeLocation.setText(crime.getLocationName());

        List<String> favouriteCrimeIDs = crimesActivity.favouritesDbHelper.getCrimeIDs();
        ImageView crimeFavouriteStar = holder.crimeFavouriteStar;
        if (favouriteCrimeIDs.contains(crime.getId())) {
            crimeFavouriteStar.setImageResource(R.drawable.ic_star_24dp);
            crimeFavouriteStar.setTag(R.drawable.ic_star_24dp);
        } else {
            crimeFavouriteStar.setTag(R.drawable.ic_star_border_24dp);
        }
        crimeFavouriteStar.setOnClickListener(v -> {
            if (crimeFavouriteStar.getTag().equals(R.drawable.ic_star_24dp)) {
                crimeFavouriteStar.setImageResource(R.drawable.ic_star_border_24dp);
                crimeFavouriteStar.setTag(R.drawable.ic_star_border_24dp);
                removeFromFavourites(crime.getId());
            } else if (crimeFavouriteStar.getTag().equals(R.drawable.ic_star_border_24dp)) {
                crimeFavouriteStar.setImageResource(R.drawable.ic_star_24dp);
                crimeFavouriteStar.setTag(R.drawable.ic_star_24dp);
                addToFavourites(crime);
            }
        });
    }

    void addToFavourites(Crime crime) {
        crimesActivity.favouritesDbHelper.insertCrime(crime);
    }

    void removeFromFavourites(String crimeID) {
        crimesActivity.favouritesDbHelper.deleteCrime(crimeID);
    }

    public class crimeListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.crime_category)
        TextView crimeCategory;

        @BindView(R.id.crime_id)
        TextView crimeID;

        @BindView(R.id.crime_location)
        TextView crimeLocation;

        @BindView(R.id.crime_show_details)
        TextView crimeShowDetails;

        @BindView(R.id.crime_favourite_star)
        ImageView crimeFavouriteStar;

        public crimeListViewHolder(View crimeItem) {
            super(crimeItem);
            ButterKnife.bind(this, crimeItem);
            crimeShowDetails.setOnClickListener(v -> {
                int position = getAdapterPosition();
                showCrimeDetailFragment(crimeList.get(position));
            });
        }
    }

    private void showCrimeDetailFragment(Crime crime) {
        crimesActivity.showCrimeDetail(crime);
    }
}
