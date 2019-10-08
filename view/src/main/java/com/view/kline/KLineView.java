package com.view.kline;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import java.util.ArrayList;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-09-29
 */
public class KLineView extends RecyclerView {

    //一个View宽度显示多少个点
    private static final String TAG = "KLineView";

    //显示多少条目
    private final int POINT_COUNT = 60;

    private KLineItemDecoration itemDecoration;

    private ArrayList<Point> points = new ArrayList<>();

    private KLineAdapter adapter;


    public KLineView(Context context) {
        this(context, null);
    }

    public KLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        itemDecoration = new KLineItemDecoration(getContext());
        addItemDecoration(itemDecoration);
        itemDecoration.refreshData(points);
        adapter = new KLineAdapter();
        setAdapter(adapter);
    }

    public void refreshPoint(ArrayList<Point> pointList) {
        this.points.clear();
        this.points.addAll(pointList);
        itemDecoration.refreshData(points);
        adapter.refreshData(points);
    }

    public void addPoint(Point point) {
        this.points.add(point);
        itemDecoration.addPoint(point);
        adapter.notifyDataSetChanged();
    }
}
