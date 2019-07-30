package io.github.sidvenu.crimesonar.adapters;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.sidvenu.crimesonar.Constants;
import io.github.sidvenu.crimesonar.ForcesActivity;
import io.github.sidvenu.crimesonar.R;
import io.github.sidvenu.crimesonar.Utils;
import io.github.sidvenu.crimesonar.models.Force;

public class ForceListAdapter extends RecyclerView.Adapter<ForceListAdapter.ForceListViewHolder> {

    public List<Force> forceList;
    ForcesActivity forcesActivity;

    public ForceListAdapter(ForcesActivity forcesActivity, List<Force> forceList) {
        this.forcesActivity = forcesActivity;
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
        holder.forceDescription.setText(Utils.getHtmlString(force.getDescription()));
        String forceImageURL = getForceImageURLFromEngagementMethods(force.getEngagementMethods());
        loadImageIntoViewFromURL(holder.forceImage, forceImageURL);
    }

    private void loadImageIntoViewFromURL(ImageView forceImage, String forceImageURL) {
        Glide.with(forceImage.getContext())
                .load(forceImageURL)
                .thumbnail(0.1f)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .error(R.drawable.police_image_not_found_padded)
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

    public class ForceListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.force_image)
        ImageView forceImage;

        @BindView(R.id.force_name)
        TextView forceName;

        @BindView(R.id.force_description)
        TextView forceDescription;

        public ForceListViewHolder(View forceItem) {
            super(forceItem);
            ButterKnife.bind(this, forceItem);
            forceItem.setOnClickListener(v -> {
                int position = getAdapterPosition();
                showForceDetailFragment(forceList.get(position), getBitmapFromImageView(forceImage));
            });
        }
    }

    private Bitmap getBitmapFromImageView(ImageView imageView) {
//        try {
//            return Glide.with(forcesActivity)
//                    .asBitmap()
//                    .load(imageView)
//                    .submit()
//                    .get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return null;
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        return null;
    }

    private void showForceDetailFragment(Force force, Bitmap bitmap) {
        forcesActivity.showForceDetail(force, bitmap);
    }
}
