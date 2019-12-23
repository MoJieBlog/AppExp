package com.lzp.appexp.tabmanager;

import android.util.Log;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-05
 */
public class DragOrderItemTouchHelperCallBack extends ItemTouchHelper.SimpleCallback {


    private static final String TAG = "MyCallback";

    public DragOrderItemTouchHelperCallBack(int dragDirs, int swipeDirs) {
        super(dragDirs, swipeDirs);
    }

    public DragOrderItemTouchHelperCallBack() {
        super(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, 0);
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            Log.d(TAG, "onMove: 位置" + viewHolder.getAdapterPosition()+"：" + target.getAdapterPosition());
            return false;
        }
        if (dragCallback != null) {
            dragCallback.onMove(fromPosition, toPosition);
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            //选中时要如何
            if (dragCallback != null) {
                dragCallback.onSelected(viewHolder);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (dragCallback != null) {
            dragCallback.onEndMove(recyclerView, viewHolder);
        }
        super.clearView(recyclerView, viewHolder);
    }

    private DragCallBack dragCallback;

    public void setDragCallback(DragCallBack dragCallback) {
        this.dragCallback = dragCallback;
    }
}
