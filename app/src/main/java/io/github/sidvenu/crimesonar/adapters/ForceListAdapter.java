package io.github.sidvenu.crimesonar.adapters;

import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.sidvenu.crimesonar.Constants;
import io.github.sidvenu.crimesonar.R;
import io.github.sidvenu.crimesonar.models.Force;

public class ForceListAdapter extends RecyclerView.Adapter<ForceListAdapter.ForceListViewHolder> {

    List<Force> forceList;

    public ForceListAdapter(List<Force> forceList) {
        this.forceList = forceList;
    }

    public void addItem(@NonNull Force force) {
        forceList.add(force);
        notifyItemInserted(forceList.size() - 1);
    }

    @Override
    public int getItemCount() {
        return forceList.size();
    }

    @NonNull
    @Override
    public ForceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View forceItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_force, parent, false);
        return new ForceListViewHolder(forceItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ForceListViewHolder holder, int position) {
        initialiseViewsInForceItem(holder, position);
    }

    private void initialiseViewsInForceItem(@NonNull ForceListViewHolder holder, int position) {
        Force force = forceList.get(position);
        holder.forceName.setText(force.getName());
        if (!TextUtils.isEmpty(force.getDescription()))
            holder.forceDescription.setText(Html.fromHtml(force.getDescription()));
        String forceImageURL = getForceImageURLFromEngagementMethods(force.getEngagementMethods());
        loadImageIntoViewFromURL(holder.forceImage, forceImageURL);
    }

    private void loadImageIntoViewFromURL(ImageView forceImage, String forceImageURL) {
        Glide.with(forceImage.getContext())
                .load(forceImageURL)
                .thumbnail(0.1f)
                .into(forceImage);
    }

    private String getForceImageURLFromEngagementMethods(List<Force.EngagementMethod> engagementMethods) {
        Force.EngagementMethod engagementMethod = findFacebookEngagementMethod(engagementMethods);
        if (engagementMethod != null) {
            String facebookProfileURL = engagementMethod.getUrl();
            return getForceImageURLFromFacebookProfileURL(facebookProfileURL);
        }
        return null;
    }

    private String getForceImageURLFromFacebookProfileURL(String facebookProfileURL) {
        String facebookID = getFacebookIDFromFacebookProfileURL(facebookProfileURL);
        return String.format(Constants.FACEBOOK_GRAPH_API_BASE_URL, facebookID);
    }

    private String getFacebookIDFromFacebookProfileURL(String facebookProfileURL) {
        facebookProfileURL = removeTrailingSlash(facebookProfileURL);
        return facebookProfileURL.substring(facebookProfileURL.lastIndexOf('/') + 1);
    }

    private String removeTrailingSlash(String facebookProfileURL) {
        if (containsTrailingSlash(facebookProfileURL))
            return facebookProfileURL.substring(0, facebookProfileURL.length() - 1);
        return facebookProfileURL;
    }

    private boolean containsTrailingSlash(String facebookProfileURL) {
        return facebookProfileURL.lastIndexOf('/') == facebookProfileURL.length() - 1;
    }

    private Force.EngagementMethod findFacebookEngagementMethod(List<Force.EngagementMethod> engagementMethods) {
        for (Force.EngagementMethod engagementMethod : engagementMethods) {
            if (engagementMethod.getTitle().equals("Facebook"))
                return engagementMethod;
        }
        return null;
    }

    public static class ForceListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.force_image)
        ImageView forceImage;

        @BindView(R.id.force_name)
        TextView forceName;

        @BindView(R.id.force_description)
        TextView forceDescription;

        public ForceListViewHolder(View forceItem) {
            super(forceItem);
            ButterKnife.bind(this, forceItem);
        }
    }
}
