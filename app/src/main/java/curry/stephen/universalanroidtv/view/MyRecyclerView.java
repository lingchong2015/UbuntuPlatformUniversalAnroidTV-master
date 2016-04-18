package curry.stephen.universalanroidtv.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by txt on 2015/12/3.
 */
public class MyRecyclerView extends RecyclerView {

    private Scroller mScroller;
    private int mLastXOffset = 0;
    private int mTargetPostoin;

    private static final String TAG = MyRecyclerView.class.getSimpleName();

    public MyRecyclerView(Context context) {
        super(context);
        init(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    /**
     * 初始化Scroller对象.
     */
    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //computeScrollOffset返回true表示滚动还在继续，持续时间是startScroll设置的时间.
        if (mScroller != null && mScroller.computeScrollOffset()) {
            scrollBy(mLastXOffset - mScroller.getCurrX(), 0);

            mLastXOffset = mScroller.getCurrX();

            postInvalidate();//让系统继续重绘，重复执行computeScroll.
        }
    }

    /**
     * 将指定Item平滑移动到RecyclerView的中间位置.
     * @param position 指定Item在适配器中的位置.
     */
    public void smoothToCenter(int position) {
        int itemCount = getLayoutManager().getItemCount();//获取RecyclerView中Item的数量.
        mTargetPostoin = Math.max(0, Math.min(itemCount - 1, position));//获取目标Item的位置.

        int firstVisibleItemAdapterPosition = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();//获取可视范围内第一个Item的位置.
        View targetChild = getChildAt(mTargetPostoin - firstVisibleItemAdapterPosition);//获取目标View.
        int targetChildLeft = targetChild.getLeft();//目标View相对于RecyclerView的左边距.
        int targetChildRight = targetChild.getRight();//目标View相对于RecyclerView的右边距.
        int targetChildWidth = targetChild.getWidth();//目标View的宽度.

        int parentWidth = getWidth();//获取RecyclerView的宽度.
        int centerLeft = parentWidth / 2 - targetChildWidth / 2;//计算目标view居中后相对于RecyclerView的左边距.
        int centerRight = parentWidth / 2 + targetChildWidth / 2;//计算目标view居中后相对于RecyclerView的右边距.
        if (targetChildLeft > centerLeft) {
            //目标View左边距比居中时的左边距大, 说明目标View靠RecyclerView的右边，此时需要将目标View向左平移.
            mLastXOffset = targetChildLeft;
            mScroller.startScroll(targetChildLeft, 0, centerLeft - targetChildLeft, 0, 100);
            postInvalidate();
        } else if (targetChildRight < centerRight) {
            mLastXOffset = targetChildRight;
            mScroller.startScroll(targetChildRight, 0, centerRight - targetChildRight, 0, 100);
            postInvalidate();
        }
    }
}
