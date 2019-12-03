package com.lzp.appexp.tabmanager;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzp.appexp.R;
import com.lzp.appexp.applicaton.App;
import com.utils.SizeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-12-02
 */
public class TabMainAdapter extends RecyclerView.Adapter {

    private static final String TAG = "TabMainAdapter";

    static final int TYPE_TITLE = 1;
    static final int TYPE_MY_TAB = 2;
    static final int TYPE_OTHER_TAB = 3;

    private ArrayList<String> myTabList = new ArrayList<>();
    private ArrayList<String> otherTabList = new ArrayList<>();

    private ItemTouchHelper itemTouchHelper;

    private int titleHeight = 0;

    public TabMainAdapter(ItemTouchHelper helper) {
        this.itemTouchHelper = helper;
        titleHeight = SizeUtils.dip2px(App.getApplication().getBaseContext(),60);
    }


    public void refreshData(List<String> myTabList, List<String> otherList) {
        this.myTabList.clear();
        this.myTabList.addAll(myTabList);
        if (otherList != null) {
            this.otherTabList.clear();
            this.otherTabList.addAll(otherList);
        }
        notifyDataSetChanged();
    }

    public ArrayList<String> getMyTabList() {
        return myTabList;
    }

    public ArrayList<String> getOtherTabList() {
        return otherTabList;
    }

    public int getTitleType() {
        return TYPE_TITLE;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == myTabList.size() + 1) {
            return TYPE_TITLE;
        } else if (position > 0 && position <= myTabList.size()) {
            return TYPE_MY_TAB;
        } else {
            return TYPE_OTHER_TAB;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_TITLE) {
            TextView textView = new TextView(viewGroup.getContext());
            textView.setTextColor(0xff000000);
            textView.setTextSize(17);
            textView.setTypeface(Typeface.DEFAULT_BOLD);
            MarginLayoutParams layoutParams = new MarginLayoutParams(LayoutParams.MATCH_PARENT, titleHeight);
            layoutParams.leftMargin = SizeUtils.dip2px(viewGroup.getContext(), 15);
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setLayoutParams(layoutParams);
            return new TitleHolder(textView);
        } else if (viewType == TYPE_MY_TAB) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_car, viewGroup, false);
            TabViewHolder holder = new TabViewHolder(inflate);
            holder.itemView.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (itemTouchHelper != null) {
                        itemTouchHelper.startDrag(holder);
                    }
                    return true;
                }
            });

            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener!=null){
                        itemClickListener.onItemClick(holder);
                    }
                }
            });

            return holder;
        } else {//其他
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_car, viewGroup, false);
            TabViewHolder holder = new TabViewHolder(inflate);
            holder.itemView.setOnLongClickListener(null);
            holder.itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener!=null){
                        itemClickListener.onItemClick(holder);
                    }
                }
            });
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == TYPE_TITLE) {
            TitleHolder holder = (TitleHolder) viewHolder;
            if (position == 0) {
                holder.textView.setText("我的标签");
            } else {
                holder.textView.setText("其他标签");
            }
        } else {
            TabViewHolder holder = (TabViewHolder) viewHolder;
            if (position <= myTabList.size()) {
                holder.tv.setText(myTabList.get(getMyTabPosition(position)));
            } else {
                holder.tv.setText(otherTabList.get(getOtherTabPosition(position)));
            }
        }
    }

    public int getMyTabPosition(int position) {
        return position - 1;
    }

    public int getOtherTabPosition(int position) {
        return position - myTabList.size() - 2;
    }

    @Override
    public int getItemCount() {
        return myTabList.size() + otherTabList.size() + 2;
    }

    private OnItemClickListener itemClickListener;

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(ViewHolder holder);
    }

    /*****************以下为Viewholder********************/

    static class TitleHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TitleHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    class TabViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tv;

        public TabViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.car);
            tv = itemView.findViewById(R.id.tv);
        }
    }


    public int getMyTitleHeight(){
        return titleHeight;
    }

    public int getOtherTitleHeight(){
        return titleHeight;
    }
}


