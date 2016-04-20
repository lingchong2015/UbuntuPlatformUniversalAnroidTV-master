package curry.stephen.universalanroidtv.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        bundle.putSerializable(DETAIL_FRAGMENT_MEDIA_ITEM_MODEL, mediaItemModel);

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        MediaItemModel MediaItemModel = (MediaItemModel) getArguments().getSerializable(DETAIL_FRAGMENT_MEDIA_ITEM_MODEL);



        return view;
    }
}
