package com.view.loadmore;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.view.R;

/**
 * 加载更多适配器
 */
public abstract class LoadMoreAdapter extends RecyclerView.Adapter {

    /**
     * 加载更多条目类型
     */
    public static final int TYPE_LOADMORE = -100;

    protected LoadMoreRecyclerView mLoadMoreRecyclerview;
    protected Context context;

    public LoadMoreAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mLoadMoreRecyclerview = (LoadMoreRecyclerView) recyclerView;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) return TYPE_LOADMORE;
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADMORE) {
            return new LoadmoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loadmore, parent, false));
        } else {
            return mOnCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int position) {
        // 预加载处理
        int loadmoreStatus = mLoadMoreRecyclerview.getLoadmoreStatus();
        if (mLoadMoreRecyclerview.isPreLoad()
                && (loadmoreStatus == LoadMoreRecyclerView.LM_LOAD_SUCCESS || loadmoreStatus == LoadMoreRecyclerView.LM_LOAD_FAILURE)) {
            int toBottomItemCount = getItemCount() - 2 - position;
            if (toBottomItemCount <= LoadMoreRecyclerView.PRE_LOAD_COUNT) {
                mLoadMoreRecyclerview.setLoadmoreStatus(LoadMoreRecyclerView.LM_LOADING);
                notifyLoadmore();
            }
        }

        if (viewHolder.getItemViewType() == TYPE_LOADMORE) {
            bindLoadmoreViewHolder(viewHolder);
            return;
        } else {
            mOnBindViewHolder(viewHolder, position);
        }
    }

    @Override
    public int getItemCount() {
        return 1+mGetItemCount();
    }

    protected abstract int mGetItemCount();

    /**
     * 绑定加载更多数据
     *
     * @param viewHolder
     */
    private void bindLoadmoreViewHolder(RecyclerView.ViewHolder viewHolder) {
        LoadmoreViewHolder holder = (LoadmoreViewHolder) viewHolder;
        final int loadmoreStatus = mLoadMoreRecyclerview.getLoadmoreStatus();
        switch (mLoadMoreRecyclerview.getLoadmoreStatus()) {
            case LoadMoreRecyclerView.LM_LOAD_COMPLETE:
                holder.loadmoreTitle.setText(context.getResources().getString(R.string.load_complete));
                holder.loadmoreView.setVisibility(View.GONE);
                break;
            case LoadMoreRecyclerView.LM_LOAD_SUCCESS:
                holder.loadmoreTitle.setText(context.getString(R.string.click_load));
                holder.loadmoreView.setVisibility(View.GONE);
                break;
            case LoadMoreRecyclerView.LM_LOAD_FAILURE:
                holder.loadmoreTitle.setText(context.getString(R.string.load_failure));
                holder.loadmoreView.setVisibility(View.GONE);
                break;
            case LoadMoreRecyclerView.LM_LOADING:
                holder.loadmoreTitle.setText(context.getString(R.string.loading));
                holder.loadmoreView.setVisibility(View.VISIBLE);
                break;

        }
        Drawable loadmoreBg = mLoadMoreRecyclerview.getLoadmoreBgDrawable();
        if (loadmoreBg == null) {
            loadmoreBg = new ColorDrawable(Color.parseColor("#f5f6f7"));
            mLoadMoreRecyclerview.setLoadmoreBgDrawable(loadmoreBg);
        }
        holder.itemView.setBackground(loadmoreBg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadmoreStatus == LoadMoreRecyclerView.LM_LOAD_FAILURE || loadmoreStatus == LoadMoreRecyclerView.LM_LOAD_SUCCESS) {
                    mLoadMoreRecyclerview.setLoadmoreStatus(LoadMoreRecyclerView.LM_LOADING);
                    notifyItemChanged(getItemCount() - 1);
                    notifyLoadmore();
                }
            }
        });
    }

    /**
     * 通知加载更多
     */
    private void notifyLoadmore() {
        LoadMoreRecyclerView.OnLoadmoreListener onLoadmoreListener = mLoadMoreRecyclerview.getOnLoadmoreListener();
        if (onLoadmoreListener != null) {
            onLoadmoreListener.onLoadmore();
        }
    }


    static class LoadmoreViewHolder extends RecyclerView.ViewHolder {

        ProgressBar loadmoreView;
        TextView loadmoreTitle;

        public LoadmoreViewHolder(@NonNull View itemView) {
            super(itemView);
            loadmoreView = itemView.findViewById(R.id.loadmore);
            loadmoreTitle = itemView.findViewById(R.id.tv_loadmore_title);
        }
    }

    protected abstract RecyclerView.ViewHolder mOnCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    protected abstract void mOnBindViewHolder(ViewHolder viewHolder, int position);

}