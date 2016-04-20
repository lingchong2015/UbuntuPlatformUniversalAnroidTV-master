package curry.stephen.universalanroidtv.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;

import curry.stephen.universalanroidtv.R;
import curry.stephen.universalanroidtv.model.MediaItemModel;

/**
 * Created by LingChong on 2016/4/11 0011.
 */
public class DetailFragment extends Fragment {

    public static final String DETAIL_FRAGMENT_MEDIA_ITEM_MODEL = "DetailFragmentMediaItemModel";
    private static final String TAG = DetailFragment.class.getSimpleName();

    public static DetailFragment newInstance(MediaItemModel mediaItemModel) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DETAIL_FRAGMENT_MEDIA_ITEM_MODEL, mediaItemModel);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        MediaItemModel mediaItemModel = getArguments().getParcelable(
                DETAIL_FRAGMENT_MEDIA_ITEM_MODEL);

        ImageView imageViewLogo = (ImageView) view.findViewById(R.id.image_view_logo);
        ImageButton imageButtonPlay = (ImageButton) view.findViewById(R.id.image_button_play);
        TextView textViewDirector = (TextView) view.findViewById(R.id.text_view_director);
        TextView textViewActor = (TextView) view.findViewById(R.id.text_view_actor);
        TextView textViewContent = (TextView) view.findViewById(R.id.text_view_content);

        try {
            imageViewLogo.setImageDrawable(Drawable.createFromStream(
                    getActivity().getContentResolver().openInputStream(
                            mediaItemModel.getThumbnail()), null));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        textViewDirector.setText(String.format("导演: %s", mediaItemModel.getDirector()));
        textViewActor.setText(String.format("演员: %s", mediaItemModel.getActor()));
        textViewContent.setText(String.format("内容简介: %s", mediaItemModel.getContent()));

        return view;
    }
}
