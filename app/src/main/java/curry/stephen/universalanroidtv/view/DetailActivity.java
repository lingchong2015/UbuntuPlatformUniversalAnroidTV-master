package curry.stephen.universalanroidtv.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import curry.stephen.universalanroidtv.model.MediaItemModel;

/**
 * Created by LingChong on 2016/4/11 0011.
 */
public class DetailActivity extends SingleFragmentActivity {

    private MediaItemModel mMediaItemModel;

    @Override
    protected Fragment createFragment() {
        Intent intent = getIntent();
        mMediaItemModel = (MediaItemModel) intent.getExtras().getSerializable(
                DetailFragment.DETAIL_FRAGMENT_MEDIA_ITEM_MODEL);

        return DetailFragment.newInstance(mMediaItemModel);
    }
}
