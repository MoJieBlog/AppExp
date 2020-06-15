package com.lzp.copyui.transition.gallery;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lzp.copyui.gallery.GalleryRecyclerViewNew;
import com.lzp.copyui.gallery.itemdecoration.GalleryItemDecoration;
import com.utils.OsUtils;
import com.utils.SizeUtils;

/**
 * @describe 画廊效果的rcv 建议使用{@link GalleryRecyclerViewNew}
 * @author: lixiaopeng
 * @Date: 2019-10-25
 */
public class GalleryRecyclerView extends RecyclerView {

    private static final String TAG = "GalleryRecyclerView";

    //图片最小的缩放倍数
    private final float MIN_SCALE = 0.65f;

    private LinearLayoutManager layoutManager;
    private LinearSnapHelper helper;
    private GalleryItemDecoration itemDecoration;

    private int screentWidth;
    //默认宽度为屏幕宽度*0.8
    private int itemWidth;
    //画廊效果距离屏幕两侧偏移量
    private int offset;

    private int dp1;

    public GalleryRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public GalleryRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        dp1 = SizeUtils.dip2px(context, 1);
        screentWidth = OsUtils.getWinWide(context);
        //画廊效果默认偏移量
        itemWidth = (int) (screentWidth - dp1 * 90);
        offset = (screentWidth - itemWidth) / 2;

        helper = new LinearSnapHelper();
        layoutManager = new LinearLayoutManager(context, HORIZONTAL, false);
        itemDecoration = new GalleryItemDecoration(context);
        itemDecoration.setPagerOffset(offset);
        addItemDecoration(itemDecoration);
        helper.attachToRecyclerView(this);
        setLayoutManager(layoutManager);
        setListener();
    }

    public void setListener() {
        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (listener != null) {
                    listener.onScrollStateChanged(recyclerView, newState);
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        int currentItemPosition = getCurrentItemPosition();
                        listener.onPageSelected(currentItemPosition);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int childCount = getChildCount();//注意这个childCount是View的个数，不是条目的个数
                for (int i = 0; i < childCount; i++) {
                    View childAt = getChildAt(i);
                    int left = childAt.getLeft();
                    /////////摘抄自网上
                    float rate = 0;//是一个缩放比例
                    if (left <= offset) {//如果view距离左边的宽度 小于等于 左侧剩余空间(offset) （意味着这个view开始往左边滑动了，并且有遮挡）
                        if (left + childAt.getWidth() >= offset) {//如果view距离左边的距离 小于等于滑进去的距离 （其实就是说滑动到一半的时候）
                            rate = (offset - left) * 1f / childAt.getWidth();//（这个比例的计算结果一般都会大于1，这样一来，根据下面的 1- rate * 0.1 得出，这个比例最多不会到达1，也就是 1- 0.1， 也就是 0.9， 所以这个view的宽度最大不会小于他本身的90%）
                        } else {
                            rate = 1;
                        }

                        childAt.setScaleY(1 - rate * (1 - MIN_SCALE));
                    } else {
                        if (left <= recyclerView.getWidth() - offset) {//这个过程大概是指这个view 从最后侧刚刚出现的时候开始滑动过offset的距离
                            rate = (recyclerView.getWidth() - offset - left) * 1f / childAt.getWidth();
                        }
                        childAt.setScaleY(MIN_SCALE + rate * (1 - MIN_SCALE));
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
        this.offset = (screentWidth - itemWidth) / 2;
        this.itemDecoration.setPagerOffset(offset);
    }

    public void setCurrentItem(int position) {
        if (position != 0) {
            layoutManager.scrollToPositionWithOffset(position, offset - itemDecoration.getDiverWidth());
        } else {
            //第一个左分割线宽度就是偏移量
            layoutManager.scrollToPositionWithOffset(position, offset);
        }
    }

    public int getCurrentItemPosition() {
        View snapView = helper.findSnapView(layoutManager);
        return layoutManager.getPosition(snapView);
    }

    public interface MONScrollListener {

        default void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        }

        default void onPageSelected(int position) {
        }
    }

    private MONScrollListener listener;

    public void setMOnScrollListener(MONScrollListener listener) {
        this.listener = listener;
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public int getOffset(){
        return offset;
    }
}
