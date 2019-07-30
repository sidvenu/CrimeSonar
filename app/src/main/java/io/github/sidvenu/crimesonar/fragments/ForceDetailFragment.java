package io.github.sidvenu.crimesonar.fragments;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.sidvenu.crimesonar.Constants;
import io.github.sidvenu.crimesonar.ForcesActivity;
import io.github.sidvenu.crimesonar.R;
import io.github.sidvenu.crimesonar.Utils;
import io.github.sidvenu.crimesonar.models.Force;

public class ForceDetailFragment extends Fragment {
    Force force;
    Bitmap forceImageBitmap;

    @BindView(R.id.force_detail_web)
    TextView forceWebLink;

    @BindView(R.id.force_detail_description)
    TextView forceDescription;

    @BindView(R.id.force_detail_image)
    ImageView forceImage;

    @BindView(R.id.force_detail_phone)
    TextView forcePhone;

    @BindView(R.id.force_detail_name)
    TextView forceName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getActivity() instanceof ForcesActivity) {
//            if (container != null)
//                container.removeAllViews();

            View view = inflater.inflate(R.layout.force_detail_fragment, container, false);
            ButterKnife.bind(this, view);
            Bundle arguments = getArguments();
            if (arguments != null) {
                force = arguments.getParcelable(Constants.FORCE_DETAIL_KEY);
                forceImageBitmap = arguments.getParcelable(Constants.FORCE_DETAIL_BITMAP_KEY);

                if (force != null) {
                    forceName.setText(Utils.getHtmlString(force.getName()));
                    forceDescription.setText(Utils.getHtmlString(force.getDescription()));

                    forceWebLink.setPaintFlags(forceWebLink.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    forceWebLink.setMovementMethod(LinkMovementMethod.getInstance());
                    forceWebLink.setText(Utils.getHtmlString(
                            "<a href=\"" +
                                    force.getUrl() +
                                    "\">Website</a>"
                            )
                    );

                    forcePhone.setText(Utils.getHtmlString(force.getTelephone()));

                    if (forceImageBitmap != null)
                        forceImage.setImageBitmap(forceImageBitmap);
                    else {
                        forceImage.setImageResource(R.drawable.police_image_not_found_padded);
                    }
                }
            }
            return view;
        }
        return null;
    }


}
