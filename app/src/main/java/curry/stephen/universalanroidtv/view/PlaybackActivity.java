package curry.stephen.universalanroidtv.view;

import android.support.v4.app.Fragment;

/**
 * Created by LingChong on 2016/4/11 0011.
 */
public class PlaybackActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return PlaybackFragment.newInstance(getIntent().getStringExtra(PlaybackFragment.EXTRA_VIDEO_PATH));
    }
}
