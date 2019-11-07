package com.lzp.appexp;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-08-15
 */
public class TestView extends ViewGroup {

    private static final String TAG = "TestView";

    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.e(TAG, "onAttachedToWindow: ");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e(TAG, "onFinishInflate: ");
    }

    private void initView() {
        Log.e(TAG, "initView: ");
    }

    public void setTest() {
        Log.e(TAG, "setTest: ");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(TAG, "onLayout: ");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: ");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG, "onMeasure: ");
    }


    public void startSearchActivity(SearchActivityConfig config) {

    }


    class SearchActivityConfig1 extends SearchActivityConfig {

        @Override
        void setUI(int level) {
            Log.e(TAG, "setUI: " + level);
        }

        @Override
        int getUILevel() {
            return 1;
        }
    }

    class SearchActivityConfig2 extends SearchActivityConfig {

        @Override
        void setUI(int level) {
            Log.e(TAG, "setUI: " + level);
        }

        @Override
        int getUILevel() {
            return Integer.MAX_VALUE;
        }
    }


    abstract class SearchActivityConfig implements Serializable {

        private SearchActivityConfig nextConfig;

        abstract void setUI(int level);

        abstract int getUILevel();

        public void handelUI(int level) {
            if (level > getUILevel()) {
                setUI(level);
            } else {
                nextConfig.handelUI(level);
            }
        }
    }


    class SearchActivity {

    }
}

