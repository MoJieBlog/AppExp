package com.lzp.appexp.tabmanager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.base.compat.BaseActivity;
import com.lzp.appexp.R;
import com.lzp.appexp.adapter.DragOrderAdapter;
import com.view.recyclerview.DragOrderItemTouchHelperCallBack;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-11-05
 */
public class TabManagerActivity extends BaseActivity {

    private RecyclerView dragRcv;
    private TabManagerAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drag_order);
    }

    @Override
    public void findView() {
        dragRcv = findViewById(R.id.dragRcv);
    }

    @Override
    public void initView() {




        dragRcv.setLayoutManager(new LinearLayoutManager(this));
        dragRcv.addItemDecoration(new TabItemDecorator(this));
        adapter = new TabManagerAdapter();
        dragRcv.setAdapter(adapter);

    }

    class TabManagerAdapter extends RecyclerView.Adapter{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            RecyclerView recyclerView = new RecyclerView(viewGroup.getContext());
            recyclerView.setNestedScrollingEnabled(false);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
            recyclerView.setLayoutParams(layoutParams);
            return new MyViewHolder(recyclerView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            MyViewHolder holder = (MyViewHolder) viewHolder;

            DragOrderAdapter dragOrderAdapter = new DragOrderAdapter();

            if (i==0){
                DragOrderItemTouchHelperCallBack dragOrderItemTouchHelper = new DragOrderItemTouchHelperCallBack(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT,
                        dragOrderAdapter, true);
                dragOrderItemTouchHelper.setData(dragOrderAdapter.getList());
                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(dragOrderItemTouchHelper);
                dragOrderAdapter.setItemTouchHelper(itemTouchHelper);
            }else{
                dragOrderAdapter.setItemTouchHelper(null);
            }

            holder.recyclerView.setAdapter(dragOrderAdapter);

        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView;
            recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(),2));

            int adapterPosition = getAdapterPosition();

        }
    }
}
