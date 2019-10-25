package com.lzp.appexp.car.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzp.appexp.R;
import com.lzp.appexp.car.view.CarCardView;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-24
 */
public class GarageViewPagerAdapter extends PagerAdapter {
    private int size = 0;
    private int delayTime = 0;

    private int[] colors = {0xff12abab,0xffab12ab,0xffabab12};
    private boolean isFirstInflate = true;
    private SparseArray<CarCardView> viewMap = new SparseArray<>();
    private int selectedPosition = 0;

    public GarageViewPagerAdapter() {
        this.size = 15;
        this.delayTime = 500;
    }

    public GarageViewPagerAdapter(int size, int delayTime) {
        this.size = size;
        this.delayTime = delayTime;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public void onDestroy(int position) {
        CarCardView view = viewMap.get(position);
        view.setCarImgVisible(View.INVISIBLE);
    }

    @Override
    public int getCount() {
        return size;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        CarCardView carCardView = (CarCardView) LayoutInflater.from(container.getContext()).inflate(R.layout.item_garage, container, false);

        if (position%2==0){
            carCardView.setBackgroundColor(colors[0]);
        }else if(position%3==0){
            carCardView.setBackgroundColor(colors[1]);
        }else{
            carCardView.setBackgroundColor(colors[2]);
        }

        if (isFirstInflate && position == selectedPosition) {
            isFirstInflate = false;
            carCardView.setCarImgVisible(View.INVISIBLE);
            carCardView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    carCardView.setCarImgVisible(View.VISIBLE);
                }
            }, delayTime - 100);
        } else {
            carCardView.setCarImgVisible(View.VISIBLE);
        }
        viewMap.put(position, carCardView);
        container.addView(carCardView);
        return carCardView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
