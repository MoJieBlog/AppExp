package com.view.kline;

import java.io.Serializable;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-08
 */
public class Point implements Serializable {
    private long date;
    private float value;
    private boolean isDisconnect;

    //是否展示date,展示时需要的参数，用于表示当前item是否展示了日期
    private boolean showDate = false;

    public Point(long date, float value, boolean isDisconnect) {
        this.date = date;
        this.value = value;
        this.isDisconnect = isDisconnect;
    }

    public Point() {
    }

    public long getDate() {
        return date;
    }

    public boolean isShowDate() {
        return showDate;
    }

    public void setShowDate(boolean showDate) {
        this.showDate = showDate;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isDisconnect() {
        return isDisconnect;
    }

    public void setDisconnect(boolean disconnect) {
        isDisconnect = disconnect;
    }
}
