package com.lzp.appexp.tabmanager;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.base.compat.BaseActivity;
import com.lzp.appexp.R;
import com.lzp.appexp.tabmanager.TabMainAdapter.OnItemClickListener;
import com.utils.PhoneUtils;

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
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
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
            public void onSelected(RecyclerView.ViewHolder viewHolder) {

            }

            @Override
            public void onEndMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

            }
        });

        adapter.setItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder) {
                int adapterPosition = holder.getAdapterPosition();
                //初始化镜像View
                initMirrorView(holder);
                if (adapterPosition > 0 && adapterPosition <= adapter.getMyTabList().size()) {//点击我的标签
                    String s = adapter.getMyTabList().get(adapter.getMyTabPosition(adapterPosition));
                    adapter.getMyTabList().remove(s);
                    adapter.getOtherTabList().add(0, s);

                    int[] fromXY = getFromXY(adapterPosition);
                    int[] toXy = computeToXY(holder, adapter.getMyTabList().size() + 3, true);
                    adapter.notifyItemMoved(adapterPosition, adapter.getMyTabList().size() + 2);
                    showAnimation(fromXY, toXy);
                } else if (adapter.getOtherTabPosition(adapterPosition) >= 0 && adapter.getOtherTabPosition(adapterPosition) < adapter.getOtherTabList().size()) {
                    String s = adapter.getOtherTabList().get(adapter.getOtherTabPosition(adapterPosition));
                    adapter.getOtherTabList().remove(s);
                    adapter.getMyTabList().add(adapter.getMyTabList().size(), s);

                    int[] fromXY = getFromXY(adapterPosition);
                    int[] toXY = computeToXY(holder, adapter.getMyTabList().size(), false);
                    adapter.notifyItemMoved(adapterPosition, adapter.getMyTabList().size());
                    showAnimation(fromXY, toXY);
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
     */
    private void showAnimation(int[] fromXY, int toXY[]) {
        if (isAnimation && valueAnimator.isRunning()) {
            valueAnimator.cancel();
        }
        ivMirror.setVisibility(View.VISIBLE);
        ivMirror.setTranslationX(fromXY[0]);
        ivMirror.setTranslationY(fromXY[1]);

        int dx = toXY[0] - fromXY[0];
        int dy = toXY[1] - fromXY[1];
        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(260);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();
                ivMirror.setTranslationX(fromXY[0] + dx * animatedFraction);
                ivMirror.setTranslationY(fromXY[1] + dy * animatedFraction);
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
                ivMirror.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ivMirror.setVisibility(View.GONE);
                    }
                }, 200);

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
     * @param toPosition 这里的目标位置是没有刷新前的坐标，因为刷新和位移同时进行，所以不能等刷新完成后计算，所以：目标位置 = 真实目标位置+1
     * @return
     */
    private int[] computeToXY(RecyclerView.ViewHolder holder, int toPosition, boolean my2more) {

        int firstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
        int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();

        //目标位置没在屏幕内
        if (toPosition > lastVisibleItemPosition + 1) {
            int[] toXy = {0, PhoneUtils.getWinHeight(this)};
            return toXy;
        } else if (toPosition < firstVisibleItemPosition + 1) {
            int[] toXy = {0, 0};
            return toXy;
        }

        //目标位置在屏幕内
        int titleHeight = adapter.getTitleHeight();
        int itemHeight = holder.itemView.getMeasuredHeight();
        int itemWidth = holder.itemView.getMeasuredWidth();

        //因为目标的位置在变化，所以用目标位置的上一个确定位置
        int[] toXY = getFromXY(toPosition - 1);
        RecyclerView.ViewHolder preHolder = dragRcv.findViewHolderForAdapterPosition(toPosition - 1);
        if (preHolder.getItemViewType() == adapter.getTitleType()) {//是title
            toXY[0] = 0;
            toXY[1] = toXY[1] + titleHeight;
        } else {
            if (toXY[0] >= dragRcv.getRight() - itemWidth) {//最右侧一个，换行
                toXY[0] = 0;
                toXY[1] = toXY[1] + itemHeight;
            } else {
                toXY[0] = toXY[0] + itemWidth;
            }
        }

        if (my2more && adapter.getMyTabList().size() % gridLayoutManager.getSpanCount() == 0) {//我的移动到更多，并且移动后，我的会少一行
            toXY[1] = toXY[1] - itemHeight;
        }

        return toXY;
    }

    private int[] getFromXY(int targetPosition) {
        int[] position = new int[2];
        RecyclerView.ViewHolder viewHolder = dragRcv.findViewHolderForAdapterPosition(targetPosition);
        if (viewHolder != null) {
            viewHolder.itemView.getLocationOnScreen(position);
            Log.e(TAG, "computeTargetPosition: " + targetPosition + " = " + position[0] + "   " + position[1]);
        }
        return position;
    }

    /**
     * 初始化镜像控件
     */
    private void initMirrorView(RecyclerView.ViewHolder holder) {
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

       /* otherTabList.add("热点");
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
        otherTabList.add("历史");*/
        adapter.refreshData(myTabList, otherTabList);
    }
}
