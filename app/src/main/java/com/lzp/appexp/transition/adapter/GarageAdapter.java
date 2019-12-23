package com.lzp.appexp.transition.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lzp.appexp.R;
import com.lzp.appexp.transition.view.CarCardView;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-25
 */
public class GarageAdapter extends RecyclerView.Adapter {

    private final String HIND_CAR_IMG = "hind_car_img";
    private int size = 10;

    private int[] colors = {0xff12abab, 0xffab12ab, 0xffabab12};
    private int selectedPosition = 0;

    //首次加载因为要播放转场动画，所以不显示图片
    private boolean isFirstInflate = true;

    private int itemWidth;
    public GarageAdapter(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    /**
     * 关闭页面必须调用这个，不然转场动画播放会出现两个图片
     *
     * @param index
     * @param rcv
     */
    public void onDestroy(int index, RecyclerView rcv) {
        RecyclerView.LayoutManager layoutManager = rcv.getLayoutManager();
        View childAt = layoutManager.findViewByPosition(index);
        if (childAt instanceof CarCardView) {
            ((CarCardView) childAt).setCarImgVisible(View.GONE);
        }
    }

    public void showLastCar(int index,RecyclerView rcv) {
        RecyclerView.LayoutManager layoutManager = rcv.getLayoutManager();
        View childAt = layoutManager.findViewByPosition(index);
        if (childAt instanceof CarCardView) {
            ((CarCardView) childAt).setCarImgVisible(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CarCardView carCardView = (CarCardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_garage, viewGroup, false);
        return new MyViewHolder(carCardView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        View itemView = viewHolder.itemView;
        if (itemView instanceof CarCardView) {
            CarCardView carCardView = (CarCardView) itemView;
            if (position % 2 == 0) {
                carCardView.setRootBgColor(colors[0]);
            } else if (position % 3 == 0) {
                carCardView.setRootBgColor(colors[1]);
            } else {
                carCardView.setRootBgColor(colors[2]);
            }
        }
    }

    @Override
    public int getItemCount() {
        return size;
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.width = itemWidth;
        }
    }
}
