package curry.stephen.universalanroidtv.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import curry.stephen.universalanroidtv.R;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * Created by LingChong on 2016/4/11 0011.
 */
public class PlaybackFragment extends Fragment {

    private String mVideoPath;

    public static final String EXTRA_VIDEO_PATH = "ExtraVideoPath";
    private static final String TAG = PlaybackFragment.class.getSimpleName();

    public static PlaybackFragment newInstance(String videoPath) {
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_VIDEO_PATH, videoPath);

        PlaybackFragment playbackFragment = new PlaybackFragment();
        playbackFragment.setArguments(bundle);

        return playbackFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVideoPath = getArguments().getString(EXTRA_VIDEO_PATH);

        Vitamio.isInitialized(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.from(getContext()).inflate(R.layout.fragment_play_back, container, false);
        initView(view);

        return view;
    }

    private void initView(View view) {
        final VideoView videoView = (VideoView) view.findViewById(R.id.my_video_view);
        videoView.setVideoLayout(VideoView.VIDEO_LAYOUT_STRETCH,0);
        videoView.setVideoPath(mVideoPath);
        MediaController mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setPlaybackSpeed(1.0f);
            }
        });

        videoView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                            if (videoView.isPlaying()) {
                                videoView.pause();
                            } else {
                                videoView.start();
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_UP:
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            break;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            break;
                        default:
                            break;
                    }
                }

                return false;
            }
        });
    }
}
