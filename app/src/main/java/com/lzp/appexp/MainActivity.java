package com.lzp.appexp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.imageloader.ImageLoader;
import com.imageloader.interfaces.IMGLoadListener;
import com.utils.permission.PermissionConstant;
import com.utils.permission.PermissionUtils;
import com.view.refresh.SwipeRefreshLayout;
import com.view.refresh.SwipeRefreshLayout.OnRefreshListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    final String url = "http://pic37.nipic.com/20140113/8800276_184927469000_2.png";

    
    SwipeRefreshLayout refreshLayout;

    private RecyclerView rcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PermissionUtils.getPermission(this, PermissionConstant.EXTERNAL_STORAGE_GROUP);
        rcv = findViewById(R.id.rcv);
        refreshLayout = findViewById(R.id.refresh);

        refreshLayout.setCanRefresh(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        rcv.setLayoutManager(new GridLayoutManager(this,3));
        rcv.setAdapter(new MAdapter());
    }

    class MAdapter extends RecyclerView.Adapter{

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.test_item, viewGroup, false);
            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            ImageLoader.get(MainActivity.this)
                    .display(url)
                   // .size(300,300)
                    .needMemory(true)
                    .placeHolder(R.mipmap.ic_launcher_round)
                    .errHolder(R.mipmap.ic_launcher)
                    .listener(new IMGLoadListener<Drawable>() {
                        @Override
                        public void success(Drawable drawable) {

                        }

                        @Override
                        public void fail(Exception e) {

                        }
                    })
                    .into(holder.testIv);
        }

        @Override
        public int getItemCount() {
            return 8;
        }

        class MyViewHolder extends RecyclerView.ViewHolder{

            ImageView testIv;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                testIv = itemView.findViewById(R.id.testIv);

                testIv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,TestActivity.class);
                        ActivityOptionsCompat options = ActivityOptionsCompat
                                .makeSceneTransitionAnimation(MainActivity.this,
                                        v,"testImg");
                        startActivity(intent,options.toBundle());
                    }
                });
            }
        }
    }
}
