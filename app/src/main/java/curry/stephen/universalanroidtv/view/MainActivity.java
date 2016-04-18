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
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import curry.stephen.universalanroidtv.R;
import curry.stephen.universalanroidtv.adapter.MainActivityAdapter;
import curry.stephen.universalanroidtv.global.GlobalVariables;
import curry.stephen.universalanroidtv.model.MediaItemModel;
import curry.stephen.universalanroidtv.model.TabDataModel;

/**
 * Created by LingChong on 2016/4/11 0011.
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private RadioButton localService;

    private HashMap<String, Fragment> mHashMapFragment = new HashMap<>();
    private View mViews[];
    private int mCurrentIndex = 0;

    private static final int SINGLE_TAB_WIDTH = 290;
    private static final int SINGLE_TAB_HEIGHT = 70;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        generateTestCase();

        initView();

//        setListener();

//        initBrowserFragment();
    }

    private void initView() {
        initTitleTab();

        initFragment();
//        initViewPager();

//        localService = (RadioButton) findViewById(R.id.main_title_local);
//        localService.setSelected(true);
//        mViews = new View[]{localService};
    }

    private FragmentManager mFragmentManager;
    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();

        if (mHashMapFragment.keySet().isEmpty()) {
            return;
        }

        changeFragment(mHashMapFragment.get("0"));
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.browser_fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void setListener() {
//        localService.setOnClickListener(this);
//
//        localService.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    mViewPager.setCurrentItem(0);
//                }
//            }
//        });
//        setting.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    mViewPager.setCurrentItem(1);
//                }
//            }
//        });
//        app.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus) {
//                    mViewPager.setCurrentItem(2);
//                }
//            }
//        });
    }

    private void initViewPager() {
//        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
//
//        mViewPager.setAdapter(new MainActivityAdapter(getSupportFragmentManager(), mHashMapFragment));
//        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                mViewPager.setCurrentItem(position);
//                switch (position) {
//                    case 0:
//                        localService.setSelected(true);
//                        break;
//                }
//            }
//        });
//        mViewPager.setCurrentItem(1);
    }

    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.main_title_local:
//                mViewPager.setCurrentItem(0);
//                break;
//            case R.id.main_title_setting:
//                mViewPager.setCurrentItem(1);
//                break;
//            case R.id.main_title_app:
//                mViewPager.setCurrentItem(2);
//                break;
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        boolean focusFlag = false;
//        for (View v : mViews) {
//            if (v.isFocused()) {
//                focusFlag = true;
//            }
//        }
//        if (focusFlag) {
//            if (KeyEvent.KEYCODE_DPAD_LEFT == keyCode) {
//                if (mCurrentIndex > 0) {
//                    mViews[--mCurrentIndex].requestFocus();
//                }
//                return true;
//            } else if (KeyEvent.KEYCODE_DPAD_RIGHT == keyCode) {
//                if (mCurrentIndex < 2) {
//                    mViews[++mCurrentIndex].requestFocus();
//                }
//                return true;
//            }
//
//            if (KeyEvent.KEYCODE_DPAD_DOWN == keyCode) {
//                ((BrowserFragment)  mHashMapFragment.get(0)).setFocus();
//            }
//        }
//
//        if (KeyEvent.KEYCODE_DPAD_DOWN == keyCode) {
//            ((BrowserFragment)  mHashMapFragment.get(0)).setFocus();
//        }
//
//        if (KeyEvent.KEYCODE_BACK == keyCode) {
//            return true;
//        }

        return super.onKeyDown(keyCode, event);
    }

    private Uri getDrawableUri(int id) {
        return Uri.parse(String.format("%s://%s/%s/%s", ContentResolver.SCHEME_ANDROID_RESOURCE,
                getResources().getResourcePackageName(id),
                getResources().getResourceTypeName(id), getResources().getResourceEntryName(id)));
    }

    private void initTitleTab() {
        if (GlobalVariables.globalTabDataModelList.isEmpty()) {
            Toast.makeText(this, String.format("没有任何节目信息！"), Toast.LENGTH_LONG);
            return;
        }

        RadioGroup radioGroupTitle = (RadioGroup) findViewById(R.id.radio_group_main);
        RadioGroup.LayoutParams layoutParamsRadioGroup = new RadioGroup.LayoutParams(
                SINGLE_TAB_WIDTH, SINGLE_TAB_HEIGHT);
        for (TabDataModel tabDataModel : GlobalVariables.globalTabDataModelList) {
            final RadioButton radioButton = (RadioButton) getLayoutInflater().from(this).inflate(
                    R.layout.radio_button_tab, null);
            radioButton.setTag(tabDataModel.getID());
            radioButton.setLayoutParams(layoutParamsRadioGroup);
            radioButton.setFocusable(true);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    radioButton.requestFocus();
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

                radioButton.setBackground(stateListDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            radioGroupTitle.addView(radioButton);

            initBrowserFragment(tabDataModel.getID(), tabDataModel.getMediaItemModelList());
        }
    }

    private void initBrowserFragment(int tabID, List<MediaItemModel> mediaItemModelList) {
//        mHashMapFragment.clear();

//        BrowserFragment browserFragment = new BrowserFragment();
        BrowserFragment browserFragment = BrowserFragment.newInstance(
                (ArrayList<MediaItemModel>) mediaItemModelList);

        mHashMapFragment.put(String.valueOf(tabID), browserFragment);

//        mHashMapFragment.clear();//清空

//        LocalServiceFragment interactTV = new LocalServiceFragment();//本地服务Fragment.
//        SettingFragment setting = new SettingFragment();
//        AppFragment app = new AppFragment();
//
//        mHashMapFragment.add(interactTV);
//        mHashMapFragment.add(setting);
//        mHashMapFragment.add(app);
//
//        MainActivityAdapter mAdapter = new MainActivityAdapter(getSupportFragmentManager(),
//                mHashMapFragment);
//        mViewPager.setAdapter(mAdapter);
//        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
//
//            @Override
//            public void onPageSelected(int position) {
//                mViewPager.setCurrentItem(position);
//                switch (position) {
//                    case 0:
//                        localService.setSelected(true);
//                        MainActivity.this.setting.setSelected(false);
//                        MainActivity.this.app.setSelected(false);
//                        break;
//                    case 1:
//                        localService.setSelected(false);
//                        MainActivity.this.setting.setSelected(true);
//                        MainActivity.this.app.setSelected(false);
//                        break;
//                    case 2:
//                        localService.setSelected(false);
//                        MainActivity.this.setting.setSelected(false);
//                        MainActivity.this.app.setSelected(true);
//                        break;
//                }
//            }
//        });
//        mViewPager.setCurrentItem(0);
    }

    private void generateTestCase() {
        GlobalVariables.globalTabDataModelList.clear();

        MediaItemModel mediaItemModel1 = new MediaItemModel.Builder()
                .id(0)
                .tabID(0)
                .identifier(UUID.randomUUID())
                .picture(getDrawableUri(R.mipmap.wlds_hk))
                .build();

        MediaItemModel mediaItemModel2 = new MediaItemModel.Builder()
                .id(1)
                .tabID(0)
                .identifier(UUID.randomUUID())
                .picture(getDrawableUri(R.mipmap.wlds_y1))
                .build();

        MediaItemModel mediaItemModel3 = new MediaItemModel.Builder()
                .id(2)
                .tabID(0)
                .identifier(UUID.randomUUID())
                .picture(getDrawableUri(R.mipmap.wlds_y2))
                .build();

        MediaItemModel mediaItemModel4 = new MediaItemModel.Builder()
                .id(3)
                .tabID(0)
                .identifier(UUID.randomUUID())
                .picture(getDrawableUri(R.mipmap.wlds_y3))
                .build();

        MediaItemModel mediaItemModel5 = new MediaItemModel.Builder()
                .id(4)
                .tabID(0)
                .identifier(UUID.randomUUID())
                .picture(getDrawableUri(R.mipmap.wlds_y4))
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
                .picture(getDrawableUri(R.mipmap.wlds_hk))
                .build();

        MediaItemModel mediaItemModel7 = new MediaItemModel.Builder()
                .id(1)
                .tabID(1)
                .identifier(UUID.randomUUID())
                .picture(getDrawableUri(R.mipmap.wlds_y1))
                .build();

        MediaItemModel mediaItemModel8 = new MediaItemModel.Builder()
                .id(2)
                .tabID(1)
                .identifier(UUID.randomUUID())
                .picture(getDrawableUri(R.mipmap.wlds_y2))
                .build();

        List<MediaItemModel> mediaItemModelList2 = new ArrayList<>();
        mediaItemModelList2.add(mediaItemModel6);
        mediaItemModelList2.add(mediaItemModel7);
        mediaItemModelList2.add(mediaItemModel8);

        TabDataModel tabDataModel1 = new TabDataModel.Builder()
                .id(0)
                .picureNormal(getDrawableUri(R.drawable.title_local_service))
                .pictureSelected(getDrawableUri(R.drawable.title_local_service_focus))
                .mediaItemModelList(mediaItemModelList1)
                .build();

        TabDataModel tabDataModel2 = new TabDataModel.Builder()
                .id(1)
                .picureNormal(getDrawableUri(R.mipmap.wangluodianshi_1))
                .pictureSelected(getDrawableUri(R.mipmap.wangluodianshi_2))
                .mediaItemModelList(mediaItemModelList2)
                .build();

        GlobalVariables.globalTabDataModelList.add(tabDataModel1);
        GlobalVariables.globalTabDataModelList.add(tabDataModel2);
    }
}
