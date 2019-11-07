package com.view.recyclerview;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

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
    private boolean longPressDragEnabled = false;

    private List data;



    public DragOrderItemTouchHelperCallBack(int dragDirs, int swipeDirs, RecyclerView.Adapter adapter,boolean longPressDragEnabled) {
        super(dragDirs, swipeDirs);
        this.adapter = adapter;
        this.longPressDragEnabled = longPressDragEnabled;
    }


    public void setData(List data) {
        this.data = data;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return longPressDragEnabled;
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
            if (onPositionChangeListener != null) {
                onPositionChangeListener.onPositionChangeListener(mFromPosition,mToPosition);
            }

            if (data!=null){
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(data, i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(data, i, i - 1);
                    }
                }
            }

            adapter.notifyItemMoved(fromPosition,toPosition);
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
        if (isDrag) {
            isDrag = false;
            adapter.notifyDataSetChanged();
            mToPosition = -1;
            mFromPosition = -1;
            isDrag = false;
        }

    }

    private OnPositionChangeListener onPositionChangeListener;

    public void setOnPositionChangeListener(OnPositionChangeListener onPositionChangeListener) {
        this.onPositionChangeListener = onPositionChangeListener;
    }

    public interface OnPositionChangeListener {
        public void onPositionChangeListener(int fromPosition, int toPosition);
    }
}
