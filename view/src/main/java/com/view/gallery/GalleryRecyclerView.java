package com.view.gallery;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.utils.PhoneUtils;
import com.view.gallery.itemdecoration.GarageItemDecoration;

/**
 * @describe 画廊效果的rcv 暂时只支持一屏只能看到三个
 * @author: lixiaopeng
 * @Date: 2019-10-25
 */
public class GalleryRecyclerView extends RecyclerView {

    private static final String TAG = "GalleryRecyclerView";

    //图片最小的缩放倍数
    private final float MIN_SCALE = 0.85f;

    private LinearLayoutManager layoutManager;
    private LinearSnapHelper helper;
    private GarageItemDecoration itemDecoration;

    private int screentWidth;
    //默认宽度为屏幕宽度*0.8
    private int itemWidth;
    //画廊效果距离屏幕两侧偏移量
    private int offset;

    public GalleryRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public GalleryRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        screentWidth = PhoneUtils.getWinWide(context);
        //画廊效果默认偏移量
        itemWidth = (int) (screentWidth * 0.8);
        offset = (screentWidth - itemWidth) / 2;

        helper = new LinearSnapHelper();
        layoutManager = new LinearLayoutManager(context, HORIZONTAL, false);
        itemDecoration = new GarageItemDecoration(context);
        itemDecoration.setPagerOffset(offset);

        addItemDecoration(itemDecoration);
        helper.attachToRecyclerView(this);
        setLayoutManager(layoutManager);


        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

            }
        });

        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
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
        this.offset = (PhoneUtils.getWinWide(getContext()) - itemWidth) / 2;
        this.itemDecoration.setPagerOffset(offset);
    }

    public void setCurrentItem(int position) {
        layoutManager.scrollToPositionWithOffset(position, offset);
    }

    public int getCurrentItemPosition() {
        //TODO 一屏可以看到多个也可以用这个算法
        int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        int firstPosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        //因为选中的居中，所以中间的index为（最后一个index + 第一个index）/2
        return (lastPosition + firstPosition) / 2;
    }
}
