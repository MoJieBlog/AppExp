package com.view.loadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.view.R;
import com.view.loadmore.LoadMoreRecyclerView.OnLoadMoreListener;

import java.util.List;

/**
 * 加载更多适配器
 */
public abstract class LoadMoreAdapter extends RecyclerView.Adapter {

    private static final String TAG = "LoadMoreAdapter";
    /**
     * 加载更多条目类型
     */
    public static final int TYPE_LOADMORE = -100;

    protected LoadMoreRecyclerView mLoadMoreRecyclerview;
    protected Context context;

    private RecyclerView.LayoutManager layoutManager;

    public LoadMoreAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mLoadMoreRecyclerview = (LoadMoreRecyclerView) recyclerView;
        layoutManager = mLoadMoreRecyclerview.getLayoutManager();
        //解决网格占一整行
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int itemViewType = getItemViewType(position);
                    return itemViewType == TYPE_LOADMORE ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) return TYPE_LOADMORE;
        if (super.getItemViewType(position)==TYPE_LOADMORE){
            throw new IllegalArgumentException(TYPE_LOADMORE+" has been declared to load more.Please chose anther number.");
        }else{
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOADMORE) {

            return new LoadmoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loadmore, parent, false));
        } else {
            return mOnCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, List payloads) {


        if (viewHolder.getItemViewType() == TYPE_LOADMORE) {
            //解决瀑布流加载更多占一整行
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                ViewGroup.LayoutParams targetParams = viewHolder.itemView.getLayoutParams();
                StaggeredGridLayoutManager.LayoutParams staggerLayoutParams;
                if (targetParams != null) {
                    staggerLayoutParams =
                            new StaggeredGridLayoutManager.LayoutParams(targetParams.width, targetParams.height);
                } else {
                    staggerLayoutParams =
                            new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                staggerLayoutParams.setFullSpan(true);
                viewHolder.itemView.setLayoutParams(staggerLayoutParams);
            }

            bindLoadMoreViewHolder(viewHolder);
            return;
        } else {
            mOnBindViewHolder(viewHolder, position, null);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
        onBindViewHolder(viewHolder, position, null);
    }

    @Override
    public int getItemCount() {
        return 1 + mGetItemCount();
    }

    protected abstract int mGetItemCount();

    /**
     * 绑定加载更多数据
     *
     * @param viewHolder
     */
    private void bindLoadMoreViewHolder(RecyclerView.ViewHolder viewHolder) {
        LoadmoreViewHolder holder = (LoadmoreViewHolder) viewHolder;
        final int loadmoreStatus = mLoadMoreRecyclerview.getLoadMoreStatus();
        switch (mLoadMoreRecyclerview.getLoadMoreStatus()) {
            case LoadMoreRecyclerView.LM_LOAD_COMPLETE:
                holder.loadmoreTitle.setText(context.getResources().getString(R.string.load_complete));
                holder.loadmoreView.setVisibility(View.GONE);
                holder.loadMoreLayout.setVisibility(View.VISIBLE);
                break;
            case LoadMoreRecyclerView.LM_CLICK_LOAD:
                holder.loadmoreTitle.setText(context.getString(R.string.click_load));
                holder.loadmoreView.setVisibility(View.GONE);
                holder.loadMoreLayout.setVisibility(View.VISIBLE);
                break;
            case LoadMoreRecyclerView.LM_LOAD_FAILURE:
                holder.loadmoreTitle.setText(context.getString(R.string.load_failure));
                holder.loadmoreView.setVisibility(View.GONE);
                holder.loadMoreLayout.setVisibility(View.VISIBLE);
                break;
            case LoadMoreRecyclerView.LM_AUTO_LOAD:
                holder.loadmoreTitle.setText(context.getString(R.string.loading));
                holder.loadmoreView.setVisibility(View.VISIBLE);
                holder.loadMoreLayout.setVisibility(View.VISIBLE);
                break;
            case LoadMoreRecyclerView.LM_LOAD_NON:
                holder.loadmoreTitle.setText("");
                holder.loadmoreView.setVisibility(View.GONE);
                holder.loadMoreLayout.setVisibility(View.GONE);
                break;

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loadmoreStatus == LoadMoreRecyclerView.LM_LOAD_FAILURE || loadmoreStatus == LoadMoreRecyclerView.LM_CLICK_LOAD) {
                    mLoadMoreRecyclerview.setLoadMoreStatus(LoadMoreRecyclerView.LM_AUTO_LOAD);
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
        OnLoadMoreListener onLoadmoreListener = mLoadMoreRecyclerview.getOnLoadmoreListener();
        if (mLoadMoreRecyclerview.getOnLoadmoreListener() != null) {
            onLoadmoreListener.onLoadMore();
        }
    }


    static class LoadmoreViewHolder extends RecyclerView.ViewHolder {

        ProgressBar loadmoreView;
        TextView loadmoreTitle;
        LinearLayout loadMoreLayout;

        public LoadmoreViewHolder(View itemView) {
            super(itemView);
            loadmoreView = itemView.findViewById(R.id.loadmore);
            loadmoreTitle = itemView.findViewById(R.id.tv_loadmore_title);
            loadMoreLayout = itemView.findViewById(R.id.loadMoreLayout);
        }
    }

    /**
     * @param parent
     * @param viewType
     * @return
     * @see RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)
     */
    public abstract RecyclerView.ViewHolder
    mOnCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * @param viewHolder
     * @param position
     * @param payloads
     */
    public abstract void mOnBindViewHolder(RecyclerView.ViewHolder viewHolder, int position, List payloads);
}