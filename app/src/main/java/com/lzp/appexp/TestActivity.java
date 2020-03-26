package com.lzp.appexp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @describe:
 * @Author: lixiaopeng
 * @Date: 2020-01-21
 */
public class TestActivity extends BaseActivity {

    private RecyclerView rv;

    private TestAdapter adapter;
    AdapterWrapper adapterWrapper;

    private int from = 0;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);
    }

    @Override
    public void initView() {
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TestAdapter();
        adapterWrapper = new AdapterWrapper(adapter);
        rv.setAdapter(adapterWrapper);


        findViewById(R.id.freshBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    @Override
    public void getData() {
        list.clear();
        for (int i = from; i < from + 10; i++) {
            list.add(String.valueOf(i));
        }
        adapter.freshData(list);

        adapterWrapper.notifyDataSetChanged();
        from += 10;
    }

    static class TestAdapter extends RecyclerView.Adapter {

        public List<String> list = new ArrayList<>();

        public void freshData(List<String> list) {
            this.list.clear();
            this.list.addAll(list);
            //notifyDataSetChanged();
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item, parent, false);
            return new TestViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TestViewHolder viewHolder = (TestViewHolder) holder;
            viewHolder.tv.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class TestViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public TestViewHolder(@NonNull View itemView) {
                super(itemView);
                tv = itemView.findViewById(R.id.tv);
            }
        }
    }


    static class AdapterWrapper extends RecyclerView.Adapter {

        @NotNull
        private RecyclerView.Adapter innerAdapter;

        public AdapterWrapper(@NotNull RecyclerView.Adapter innerAdapter) {
            this.innerAdapter = innerAdapter;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == innerAdapter.getItemCount()) {
                return 1;
            }
            return super.getItemViewType(position);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == 1) {
                return new LoadmoreViewHolder(LayoutInflater.from(parent.getContext()).inflate(com.view.R.layout.layout_loadmore, parent, false));
            } else {
                return innerAdapter.onCreateViewHolder(parent, viewType);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder.getItemViewType() == 1) {
                return;
            }
            innerAdapter.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return innerAdapter.getItemCount() + 1;
        }


        class LoadmoreViewHolder extends RecyclerView.ViewHolder {
            public LoadmoreViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
