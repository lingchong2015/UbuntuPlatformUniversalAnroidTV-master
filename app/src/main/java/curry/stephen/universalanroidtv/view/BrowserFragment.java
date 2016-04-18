package curry.stephen.universalanroidtv.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import curry.stephen.universalanroidtv.R;
import curry.stephen.universalanroidtv.adapter.GalleryAdapter;
import curry.stephen.universalanroidtv.model.MediaItemModel;

/**
 * Created by LingChong on 2016/4/11 0011.
 */
public class BrowserFragment extends Fragment {

    private MyRecyclerView mRecyclerView;//自定义RecyclerView.
    private GalleryAdapter mAdapter;//RecyclerView适配器.
//    private List<Integer> mTestDataList;//数据源.
    private int mIndex;

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    public void setInitFocus() {
        mRecyclerView.getChildAt(0).requestFocus();
    }

    public static final String MEDIA_ITEM_MODEL_LIST = "BrowserFragmentMediaItemModelList";

    public static BrowserFragment newInstance(ArrayList<MediaItemModel> mediaItemModelList) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(BrowserFragment.MEDIA_ITEM_MODEL_LIST, mediaItemModelList);

        BrowserFragment browserFragment = new BrowserFragment();
        browserFragment.setArguments(bundle);
        return browserFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        List<MediaItemModel> mediaItemModelList = (List<MediaItemModel>) getArguments().getSerializable(MEDIA_ITEM_MODEL_LIST);

        View view = inflater.inflate(R.layout.fragment_browser, container, false);

//        initTestDataList();

        //获取RecyclerView对象, 设置布局管理器, 设置RecyclerView动画.
        mRecyclerView = (MyRecyclerView) view.findViewById(R.id.id_recyclerview_horizontal);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);//将RecyclerView设置为横向布局.
        mRecyclerView.setLayoutManager(linearLayoutManager);

        //初始化与设置RecyclerView的适配器.
        mAdapter = new GalleryAdapter(getActivity(), mediaItemModelList);
        mRecyclerView.setAdapter(mAdapter);

        //设置适配器的监听事件, 这里需要监听单击事件与选中事件.
        mAdapter.setOnItemClickListener(new GalleryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                mRecyclerView.checkAutoAdjust(position);
//                mRecyclerView.smoothToCenter(position);//当单击Item时, 将该Item置于中间位置.
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        mAdapter.setOnItemSelectListener(new GalleryAdapter.OnItemSelectListener() {
            @Override
            public void onItemSelect(View view, int position) {
//                linearLayoutManager.scrollToPositionWithOffset(position,350);
                mRecyclerView.smoothToCenter(position);
            }
        });

        //设置RecyclerView的监听事件, 这里需要设置焦点变化事件.
        mRecyclerView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (mRecyclerView.getChildCount() > 0) {
                        linearLayoutManager.scrollToPositionWithOffset(0, 0);
                        mRecyclerView.getChildAt(0).requestFocus();
                    }
                }
            }
        });

        mRecyclerView.requestFocus();

        return view;
    }

    public void setFocus() {
        mRecyclerView.getChildAt(0).requestFocus();
    }

//    private void initTestDataList() {
//        mTestDataList = new ArrayList<>(Arrays.asList(R.mipmap.ic_launcher, R.mipmap.ic_launcher,
//                R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher));
//    }
}
