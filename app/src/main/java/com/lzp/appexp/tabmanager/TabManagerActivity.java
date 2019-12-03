package com.lzp.appexp.tabmanager;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.base.compat.BaseActivity;
import com.lzp.appexp.R;
import com.lzp.appexp.tabmanager.TabMainAdapter.OnItemClickListener;

import java.util.ArrayList;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-05
 */
public class TabManagerActivity extends BaseActivity {
    private static final String TAG = "TabManagerActivity";

    private RecyclerView dragRcv;
    private FrameLayout rootView;
    private ImageView ivMirror;

    private TabMainAdapter adapter;

    private GridLayoutManager gridLayoutManager;

    private boolean isAnimation = false;
    private ValueAnimator valueAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_order);
    }

    @Override
    public void findView() {
        dragRcv = findViewById(R.id.dragRcv);
        rootView = findViewById(R.id.rootView);
        ivMirror = findViewById(R.id.ivMirror);
    }

    private DragOrderItemTouchHelperCallBack callback;

    @Override
    public void initView() {
        callback = new DragOrderItemTouchHelperCallBack();
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(dragRcv);

        adapter = new TabMainAdapter(helper);

        gridLayoutManager = new GridLayoutManager(this, 3);
        dragRcv.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemViewType = adapter.getItemViewType(position);
                if (itemViewType == adapter.getTitleType()) {
                    return gridLayoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });

        dragRcv.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        callback.setDragCallback(new DragCallBack() {
            @Override
            public void onMove(int fromPosition, int toPosition) {
                if (fromPosition >= 0 && fromPosition <= adapter.getMyTabList().size() + 1 && toPosition >= 0 && toPosition <= adapter.getMyTabList().size()) {
                    String item = adapter.getMyTabList().get(fromPosition - 1);
                    adapter.getMyTabList().remove(fromPosition - 1);
                    adapter.getMyTabList().add(toPosition - 1, item);
                    adapter.notifyItemMoved(fromPosition, toPosition);
                }
            }

            @Override
            public void onSelected(ViewHolder viewHolder) {

            }

            @Override
            public void onEndMove(RecyclerView recyclerView, ViewHolder viewHolder) {

            }
        });

        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewHolder holder) {
                int adapterPosition = holder.getAdapterPosition();
                //初始化镜像View
                initMirrorView(holder);
                if (adapterPosition > 0 && adapterPosition <= adapter.getMyTabList().size()) {//点击我的标签
                    String s = adapter.getMyTabList().get(adapter.getMyTabPosition(adapterPosition));
                    adapter.getMyTabList().remove(s);
                    adapter.getOtherTabList().add(0, s);
                    showAnimation(holder, getFromPosition(adapterPosition), computeToPosition(holder,adapter.getMyTabList().size() + 2, false));
                    adapter.notifyItemMoved(adapterPosition, adapter.getMyTabList().size() + 2);
                } else if (adapter.getOtherTabPosition(adapterPosition) >= 0 && adapter.getOtherTabPosition(adapterPosition) < adapter.getOtherTabList().size()) {
                    String s = adapter.getOtherTabList().get(adapter.getOtherTabPosition(adapterPosition));
                    adapter.getOtherTabList().remove(s);
                    adapter.getMyTabList().add(adapter.getMyTabList().size(), s);
                    showAnimation(holder, getFromPosition(adapterPosition), computeToPosition(holder,adapter.getMyTabList().size(), true));
                    adapter.notifyItemMoved(adapterPosition, adapter.getMyTabList().size());
                }
            }
        });
    }

    @Override
    public void clearListener() {
        callback.setDragCallback(null);
        adapter.setItemClickListener(null);
    }

    /**
     * 展示平移动画
     *
     * @param holder
     */
    private void showAnimation(ViewHolder holder, int[] fromPosition, int toPosition[]) {
        if (isAnimation && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        ivMirror.setVisibility(View.VISIBLE);
        ivMirror.setTranslationX(fromPosition[0]);
        ivMirror.setTranslationY(fromPosition[1]);

        int dx = toPosition[0] - fromPosition[0];
        int dy = toPosition[1] - fromPosition[1];
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(260);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                ivMirror.setTranslationX(fromPosition[0] + dx * animatedFraction);
                ivMirror.setTranslationY(fromPosition[1] + dy * animatedFraction);
            }
        });

        valueAnimator.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimation = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimation = false;
                ivMirror.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimation = false;
                ivMirror.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    /**
     * @param toPosition 这里的目标位置是没有刷新前的坐标，因为刷新和位移同时进行，所以页不能等刷新完成后计算
     * @param toMyTab
     * @return
     */
    private int[] computeToPosition(ViewHolder holder,int toPosition, boolean toMyTab) {
        int myTitleHeight = adapter.getMyTitleHeight();
        int otherTitleHeight = adapter.getOtherTitleHeight();
        int itemHeight = holder.itemView.getMeasuredHeight();
        int itemWidth = holder.itemView.getMeasuredWidth();

        int paddingTop = dragRcv.getPaddingTop();
        MarginLayoutParams layoutParams = (MarginLayoutParams) dragRcv.getLayoutParams();
        int topMargin = layoutParams.topMargin;

        int[] position = new int[2];
        if (toMyTab) {//从其他移动到我的
            int myTabPosition = adapter.getMyTabPosition(toPosition);
            int span = myTabPosition % gridLayoutManager.getSpanCount();
            int ceil = (int) Math.ceil(1f * (myTabPosition+1) / gridLayoutManager.getSpanCount());
            position[0] = (span) * itemWidth;
            position[1] = (ceil - 1) * itemHeight + myTitleHeight+ topMargin+paddingTop;
        } else {//从我的移动到其他
            int otherTabPosition = adapter.getOtherTabPosition(toPosition);
            int span = otherTabPosition % gridLayoutManager.getSpanCount();
            int myTabClum = (int) Math.ceil(1f * adapter.getMyTabList().size() / gridLayoutManager.getSpanCount());
            int ceil = (int) Math.ceil(1f * (otherTabPosition+1) / gridLayoutManager.getSpanCount());
            position[0] = (span) * itemWidth;
            position[1] = (myTabClum + ceil - 1) * itemHeight + myTitleHeight + otherTitleHeight + topMargin+paddingTop;

        }
        return position;
    }

    private int[] getFromPosition(int targetPosition) {
        int[] position = new int[2];
        ViewHolder viewHolder = dragRcv.findViewHolderForAdapterPosition(targetPosition);
        if (viewHolder != null) {
            viewHolder.itemView.getLocationOnScreen(position);
            Log.e(TAG, "computeTargetPosition: " + targetPosition + " = " + position[0] + "   " + position[1]);
        }
        return position;
    }

    /**
     * 初始化镜像控件
     */
    private void initMirrorView(ViewHolder holder) {
        View itemView = holder.itemView;
        itemView.destroyDrawingCache();
        itemView.setDrawingCacheEnabled(true);

        Bitmap bitmap = Bitmap.createBitmap(itemView.getDrawingCache());
        ivMirror.setImageBitmap(bitmap);
        itemView.setDrawingCacheEnabled(false);

        LayoutParams layoutParams = ivMirror.getLayoutParams();
        layoutParams.width = itemView.getMeasuredWidth();
        layoutParams.height = itemView.getMeasuredHeight();
    }

    @Override
    public void getData() {

        ArrayList<String> myTabList = new ArrayList<>();
        myTabList.add("关注");
        myTabList.add("推荐");
        myTabList.add("故事");
        myTabList.add("直播");
        myTabList.add("影视");
        myTabList.add("综艺");
        myTabList.add("音乐");

        ArrayList<String> otherTabList = new ArrayList<>();
        otherTabList.add("热点");
        otherTabList.add("国际");
        otherTabList.add("足球");
        otherTabList.add("娱乐");
        otherTabList.add("教育");
        otherTabList.add("漫画");
        otherTabList.add("动漫");
        otherTabList.add("图片");
        otherTabList.add("搞笑");
        otherTabList.add("家居");
        otherTabList.add("美食");
        otherTabList.add("汽车");
        otherTabList.add("电影");
        otherTabList.add("历史");
        adapter.refreshData(myTabList, otherTabList);
    }
}
