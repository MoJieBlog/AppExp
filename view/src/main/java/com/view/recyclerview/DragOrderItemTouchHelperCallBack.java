package com.view.recyclerview;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-05
 */
public class DragOrderItemTouchHelperCallBack extends ItemTouchHelper.SimpleCallback {


    private static final String TAG = "MyCallback";

    private int mFromPosition = -1, mToPosition = -1;
    private boolean isDrag;

    private RecyclerView.Adapter adapter;

    public DragOrderItemTouchHelperCallBack(int dragDirs, int swipeDirs, RecyclerView.Adapter adapter) {
        super(dragDirs, swipeDirs);
        this.adapter = adapter;
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
        if (fromPosition >= 0 && toPosition >= 0 && toPosition <= adapter.getItemCount() - 1) {
            if (mFromPosition == -1 && mToPosition == -1) {
                isDrag = true;
                mFromPosition = fromPosition;
            }
            mToPosition = toPosition;
            adapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        adapter.notifyDataSetChanged();
        if (isDrag) {
            isDrag = false;
            if (mFromPosition != mToPosition) {

                if (onPositionChangeListener != null) {
                    onPositionChangeListener.onPositionChangeListener(mFromPosition,mToPosition);
                }
                adapter.notifyDataSetChanged();
            }
            mToPosition = -1;
            mFromPosition = -1;
            isDrag = false;
        }

    }

    public OnPositionChangeListener onPositionChangeListener;
    public interface OnPositionChangeListener {
        public void onPositionChangeListener(int fromPosition,int toPosition);
    }
}
