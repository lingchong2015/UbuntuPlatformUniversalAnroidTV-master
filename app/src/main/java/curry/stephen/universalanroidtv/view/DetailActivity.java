package curry.stephen.universalanroidtv.view;

import android.support.v4.app.Fragment;

import curry.stephen.universalanroidtv.model.MediaItemModel;

/**
 * Created by LingChong on 2016/4/11 0011.
 */
public class DetailActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        MediaItemModel mediaItemModel = (MediaItemModel) getIntent().getExtras().getSerializable(
                DetailFragment.DETAIL_FRAGMENT_MEDIA_ITEM_MODEL);

        return DetailFragment.newInstance(mediaItemModel);
    }
}
