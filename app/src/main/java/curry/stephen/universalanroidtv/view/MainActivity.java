package curry.stephen.universalanroidtv.view;

import android.content.ContentResolver;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import curry.stephen.universalanroidtv.R;
import curry.stephen.universalanroidtv.global.GlobalVariables;
import curry.stephen.universalanroidtv.model.MediaItemModel;
import curry.stephen.universalanroidtv.model.TabDataModel;

/**
 * Created by LingChong on 2016/4/11 0011.
 */
public class MainActivity extends FragmentActivity {

    private HashMap<Integer, Fragment> mHashMapFragment = new HashMap<>();
    private List<ImageButton> mImageButtonList = new ArrayList<>();
    private Fragment mCurrentFragment;
    private FragmentManager mFragmentManager;

    private static final int SINGLE_TAB_WIDTH = 290;
    private static final int SINGLE_TAB_HEIGHT = 70;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        generateTestCase();

        initView();
    }

    private void initView() {
        initTab();

        initFragment();
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();

        if (mHashMapFragment.keySet().isEmpty()) {
            return;
        }

        mImageButtonList.get(0).setSelected(true);
        changeFragment(mHashMapFragment.get(0));
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.browser_fragment_container, fragment);
        fragmentTransaction.commit();

        mCurrentFragment = fragment;
        ((BrowserFragment) mCurrentFragment).setNeedToMoveInitPosition(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isImageButtonFocused = false;
        int indexSelectedRadioButton = 0;
        for (ImageButton imageButton : mImageButtonList) {
            if (imageButton.isFocused()) {
                isImageButtonFocused = true;
                break;
            }
            ++indexSelectedRadioButton;
        }

        if (isImageButtonFocused) {
            if (KeyEvent.KEYCODE_DPAD_LEFT == keyCode) {
                if (Integer.valueOf(String.valueOf(
                        mImageButtonList.get(indexSelectedRadioButton).getTag())) == 0) {
                    return true;
                }

                mImageButtonList.get(indexSelectedRadioButton).setSelected(false);

                changeFragment(mHashMapFragment.get(indexSelectedRadioButton - 1));
                mImageButtonList.get(((BrowserFragment) mCurrentFragment).getIndex()).setSelected(true);
                mImageButtonList.get(((BrowserFragment) mCurrentFragment).getIndex()).requestFocus();
                return true;
            } else if (KeyEvent.KEYCODE_DPAD_RIGHT == keyCode) {
                if (Integer.valueOf(String.valueOf(mImageButtonList.get(
                        indexSelectedRadioButton).getTag())) == (mImageButtonList.size() - 1)) {
                    return true;
                }

                mImageButtonList.get(indexSelectedRadioButton).setSelected(false);

                changeFragment(mHashMapFragment.get(indexSelectedRadioButton + 1));
                mImageButtonList.get(((BrowserFragment) mCurrentFragment).getIndex()).setSelected(true);
                mImageButtonList.get(((BrowserFragment) mCurrentFragment).getIndex()).requestFocus();
                return true;
            }
        }

        if (KeyEvent.KEYCODE_DPAD_UP == keyCode) {
            mImageButtonList.get(((BrowserFragment) mCurrentFragment).getIndex()).setSelected(true);
            mImageButtonList.get(((BrowserFragment) mCurrentFragment).getIndex()).requestFocus();
            return true;
        } else if (KeyEvent.KEYCODE_DPAD_DOWN == keyCode) {
            mImageButtonList.get(((BrowserFragment) mCurrentFragment).getIndex()).setSelected(false);
            ((BrowserFragment) mCurrentFragment).setFocus();
            return true;
        } else if (KeyEvent.KEYCODE_BACK == keyCode) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private Uri getLocalDrawableUri(int id) {
        return Uri.parse(String.format("%s://%s/%s/%s", ContentResolver.SCHEME_ANDROID_RESOURCE,
                getResources().getResourcePackageName(id),
                getResources().getResourceTypeName(id), getResources().getResourceEntryName(id)));
    }

    private void initTab() {
        if (GlobalVariables.globalTabDataModelList.isEmpty()) {
            Toast.makeText(this, String.format("没有任何节目信息！"), Toast.LENGTH_LONG);
            return;
        }

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear_layout_title);
        RadioGroup.LayoutParams layoutParamsRadioGroup = new RadioGroup.LayoutParams(
                SINGLE_TAB_WIDTH, SINGLE_TAB_HEIGHT);
        int index = 0;
        for (TabDataModel tabDataModel : GlobalVariables.globalTabDataModelList) {
            final ImageButton imageButton = (ImageButton) getLayoutInflater().from(this).inflate(
                    R.layout.radio_button_tab, null);
            imageButton.setTag(index);
            imageButton.setLayoutParams(layoutParamsRadioGroup);
            imageButton.setFocusable(true);
            imageButton.setFocusableInTouchMode(true);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageButton.requestFocus();
                }
            });

            try {
                Drawable drawableNormal = Drawable.createFromStream(
                        getContentResolver().openInputStream(tabDataModel.getPictureNormal()),
                        null);
                Drawable drawableSelected = Drawable.createFromStream(
                        getContentResolver().openInputStream(tabDataModel.getPictureSelected()),
                        null);

                StateListDrawable stateListDrawable = new StateListDrawable();
                stateListDrawable.addState(new int[]{android.R.attr.state_focused},
                        drawableSelected);
                stateListDrawable.addState(new int[]{android.R.attr.state_selected},
                        drawableSelected);
                stateListDrawable.addState(new int[]{}, drawableNormal);

                imageButton.setBackground(stateListDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            linearLayout.addView(imageButton);

            mImageButtonList.add(imageButton);

            initBrowserFragment(index, tabDataModel.getMediaItemModelList());

            ++index;
        }
    }

    private void initBrowserFragment(int index, List<MediaItemModel> mediaItemModelList) {
        BrowserFragment browserFragment = BrowserFragment.newInstance(
                (ArrayList<MediaItemModel>) mediaItemModelList);

        browserFragment.setIndex(index);

        mHashMapFragment.put(index, browserFragment);
    }

    private void generateTestCase() {
        GlobalVariables.globalTabDataModelList.clear();

        MediaItemModel mediaItemModel1 = new MediaItemModel.Builder()
                .id(0)
                .tabID(0)
                .identifier(UUID.randomUUID())
                .picture(getLocalDrawableUri(R.mipmap.wlds_hk))
                .thumbnail(getLocalDrawableUri(R.drawable.hks_logo))
                .title("香港卫视")
                .director("香港卫视国际传媒集团")
                .actor("高洪星 薛建华 张海勇等")
                .content("香港卫视于2008年12月19日在香港完成香港卫视国际传媒集团的商业注册，2010年9月初试" +
                        "播...内容简介：香港卫视于2008年12月19日在香港完成香港...")
                .build();

        MediaItemModel mediaItemModel2 = new MediaItemModel.Builder()
                .id(1)
                .tabID(0)
                .identifier(UUID.randomUUID())
                .picture(getLocalDrawableUri(R.mipmap.wlds_y1))
                .thumbnail(getLocalDrawableUri(R.drawable.hks_logo))
                .title("香港卫视")
                .director("香港卫视国际传媒集团")
                .actor("高洪星 薛建华 张海勇等")
                .content("香港卫视于2008年12月19日在香港完成香港卫视国际传媒集团的商业注册，2010年9月初试" +
                        "播...内容简介：香港卫视于2008年12月19日在香港完成香港...")
                .build();

        MediaItemModel mediaItemModel3 = new MediaItemModel.Builder()
                .id(2)
                .tabID(0)
                .identifier(UUID.randomUUID())
                .picture(getLocalDrawableUri(R.mipmap.wlds_y2))
                .thumbnail(getLocalDrawableUri(R.drawable.hks_logo))
                .title("香港卫视")
                .director("香港卫视国际传媒集团")
                .actor("高洪星 薛建华 张海勇等")
                .content("香港卫视于2008年12月19日在香港完成香港卫视国际传媒集团的商业注册，2010年9月初试" +
                        "播...内容简介：香港卫视于2008年12月19日在香港完成香港...")
                .build();

        MediaItemModel mediaItemModel4 = new MediaItemModel.Builder()
                .id(3)
                .tabID(0)
                .identifier(UUID.randomUUID())
                .picture(getLocalDrawableUri(R.mipmap.wlds_y3))
                .thumbnail(getLocalDrawableUri(R.drawable.hks_logo))
                .title("香港卫视")
                .director("香港卫视国际传媒集团")
                .actor("高洪星 薛建华 张海勇等")
                .content("香港卫视于2008年12月19日在香港完成香港卫视国际传媒集团的商业注册，2010年9月初试" +
                        "播...内容简介：香港卫视于2008年12月19日在香港完成香港...")
                .build();

        MediaItemModel mediaItemModel5 = new MediaItemModel.Builder()
                .id(4)
                .tabID(0)
                .identifier(UUID.randomUUID())
                .picture(getLocalDrawableUri(R.mipmap.wlds_y4))
                .thumbnail(getLocalDrawableUri(R.drawable.hks_logo))
                .title("香港卫视")
                .director("香港卫视国际传媒集团")
                .actor("高洪星 薛建华 张海勇等")
                .content("香港卫视于2008年12月19日在香港完成香港卫视国际传媒集团的商业注册，2010年9月初试" +
                        "播...内容简介：香港卫视于2008年12月19日在香港完成香港...")
                .build();

        List<MediaItemModel> mediaItemModelList1 = new ArrayList<>();
        mediaItemModelList1.add(mediaItemModel1);
        mediaItemModelList1.add(mediaItemModel2);
        mediaItemModelList1.add(mediaItemModel3);
        mediaItemModelList1.add(mediaItemModel4);
        mediaItemModelList1.add(mediaItemModel5);

        MediaItemModel mediaItemModel6 = new MediaItemModel.Builder()
                .id(0)
                .tabID(1)
                .identifier(UUID.randomUUID())
                .picture(getLocalDrawableUri(R.mipmap.wlds_hk))
                .thumbnail(getLocalDrawableUri(R.drawable.hks_logo))
                .title("香港卫视")
                .director("香港卫视国际传媒集团")
                .actor("高洪星 薛建华 张海勇等")
                .content("香港卫视于2008年12月19日在香港完成香港卫视国际传媒集团的商业注册，2010年9月初试" +
                        "播...内容简介：香港卫视于2008年12月19日在香港完成香港...")
                .build();

        MediaItemModel mediaItemModel7 = new MediaItemModel.Builder()
                .thumbnail(getLocalDrawableUri(R.drawable.hks_logo))
                .director("香港卫视国际传媒集团")
                .actor("高洪星 薛建华 张海勇等")
                .content("香港卫视于2008年12月19日在香港完成香港卫视国际传媒集团的商业注册，2010年9月初试" +
                        "播...内容简介：香港卫视于2008年12月19日在香港完成香港...")
                .id(1)
                .tabID(1)
                .identifier(UUID.randomUUID())
                .picture(getLocalDrawableUri(R.mipmap.wlds_y1))
                .thumbnail(getLocalDrawableUri(R.drawable.hks_logo))
                .title("香港卫视")
                .director("香港卫视国际传媒集团")
                .actor("高洪星 薛建华 张海勇等")
                .content("香港卫视于2008年12月19日在香港完成香港卫视国际传媒集团的商业注册，2010年9月初试" +
                        "播...内容简介：香港卫视于2008年12月19日在香港完成香港...")
                .build();

        MediaItemModel mediaItemModel8 = new MediaItemModel.Builder()
                .id(2)
                .tabID(1)
                .identifier(UUID.randomUUID())
                .picture(getLocalDrawableUri(R.mipmap.wlds_y2))
                .thumbnail(getLocalDrawableUri(R.drawable.hks_logo))
                .title("香港卫视")
                .director("香港卫视国际传媒集团")
                .actor("高洪星 薛建华 张海勇等")
                .content("香港卫视于2008年12月19日在香港完成香港卫视国际传媒集团的商业注册，2010年9月初试" +
                        "播...内容简介：香港卫视于2008年12月19日在香港完成香港...")
                .build();

        List<MediaItemModel> mediaItemModelList2 = new ArrayList<>();
        mediaItemModelList2.add(mediaItemModel6);
        mediaItemModelList2.add(mediaItemModel7);
        mediaItemModelList2.add(mediaItemModel8);

        TabDataModel tabDataModel1 = new TabDataModel.Builder()
                .id(0)
                .picureNormal(getLocalDrawableUri(R.drawable.title_local_service))
                .pictureSelected(getLocalDrawableUri(R.drawable.title_local_service_focus))
                .mediaItemModelList(mediaItemModelList1)
                .build();

        TabDataModel tabDataModel2 = new TabDataModel.Builder()
                .id(1)
                .picureNormal(getLocalDrawableUri(R.mipmap.wangluodianshi_1))
                .pictureSelected(getLocalDrawableUri(R.mipmap.wangluodianshi_2))
                .mediaItemModelList(mediaItemModelList2)
                .build();

        GlobalVariables.globalTabDataModelList.add(tabDataModel1);
        GlobalVariables.globalTabDataModelList.add(tabDataModel2);
    }
}
