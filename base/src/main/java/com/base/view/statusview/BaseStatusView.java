package com.base.view.statusview;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.R;

/**
 * @author Li Xiaopeng
 * @date 2019/1/3
 */
public class BaseStatusView extends LinearLayout implements IStatusView {

    protected Context context;
    protected Resources resources;

    private ImageView imageView;
    private TextView title;
    private TextView content;
    private Button refreshButton;

    protected int defaultImageRes;

    protected String defaultTitleStr;
    protected int defaultTitleColor;
    protected int defaultTitleSize;

    protected String defaultContentStr;
    protected int defaultContentColor;
    protected int defaultContentSize;

    protected String defaultBtnTextStr;
    protected int defaultBtnShapeRes;
    protected int defaultBtnTextSize;
    protected int defaultBtnTextColor;

    public BaseStatusView(Context context) {
        this(context, null);
    }

    public BaseStatusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        resources = context.getResources();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BaseStatusView);
        defaultImageRes = typedArray.getResourceId(R.styleable.BaseStatusView_image_src, 0);

        defaultTitleStr = typedArray.getString(R.styleable.BaseStatusView_title_str);
        defaultTitleColor = typedArray.getColor(R.styleable.BaseStatusView_title_color, resources.getColor(R.color.color_a1abbc));
        defaultTitleSize = typedArray.getDimensionPixelSize(R.styleable.BaseStatusView_title_size, 15);

        defaultContentStr = typedArray.getString(R.styleable.BaseStatusView_content_str);
        defaultContentColor = typedArray.getColor(R.styleable.BaseStatusView_content_color, resources.getColor(R.color.color_a1abbc));
        defaultContentSize = typedArray.getDimensionPixelSize(R.styleable.BaseStatusView_content_size, 13);

        defaultBtnTextStr = typedArray.getString(R.styleable.BaseStatusView_btn_text);
        defaultBtnShapeRes = typedArray.getResourceId(R.styleable.BaseStatusView_btn_shape,R.drawable.rect_bf3f4f6_r2);
        defaultBtnTextSize = typedArray.getDimensionPixelSize(R.styleable.BaseStatusView_btn_text_size,15);
        defaultBtnTextColor = typedArray.getColor(R.styleable.BaseStatusView_btn_text_color,resources.getColor(R.color.color_333333));
        typedArray.recycle();

        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(context).inflate(R.layout.view_error_layout,this,false);
        findView(view);
        addView(view);
    }
    public void setView(){
        setImageRes(defaultImageRes);
        setTitleStr(defaultTitleStr);
        setTitleTextColor(defaultTitleColor);
        setTitleTextSize(defaultTitleSize);

        setContentStr(defaultContentStr);
        setContentTextColor(defaultContentColor);
        setContentTextSize(defaultContentSize);

        setButtonText(defaultBtnTextStr);
        setButtonShapeRes(defaultBtnShapeRes);
        setButtonTextSize(defaultBtnTextSize);
        setButtonTextColor(defaultBtnTextColor);
    }

    private void findView(View view) {
        imageView = view.findViewById(R.id.iv_err);
        title = view.findViewById(R.id.tv_err_title);
        content = view.findViewById(R.id.tv_err_content);
        refreshButton = view.findViewById(R.id.btn_err);
    }

    @Override
    public void setImageRes(int imageRes) {
        if (imageView != null) {
            imageView.setImageResource(imageRes);
        }
    }

    @Override
    public void setImageBitmap(Bitmap bmp) {
        if (imageView != null)
            imageView.setImageBitmap(bmp);
    }

    @Override
    public void setTitleStr(String titleStr) {
        if (title != null) {
            title.setText(titleStr);
        }
    }

    @Override
    public void setTitleRes(int titleRes) {
        setTitleStr(resources.getString(titleRes));
    }

    @Override
    public void setTitleTextColor(int color) {
        if (title != null) {
            title.setTextColor(color);
        }
    }

    @Override
    public void setTitleTextColorRes(int colorRes) {
        setTitleTextColor(resources.getColor(colorRes));
    }

    @Override
    public void setTitleTextSize(int size) {
        if (title != null) {
            title.setTextSize(size);
        }
    }

    @Override
    public void setContentStr(String contentStr) {
        if (content != null) {
            content.setText(contentStr);
        }
    }

    @Override
    public void setContentRes(int contentRes) {
        setContentStr(resources.getString(contentRes));
    }

    @Override
    public void setContentTextColor(int color) {
        if (content != null) {
            content.setTextColor(color);
        }
    }

    @Override
    public void setContentTextColorRes(int colorRes) {
        setContentTextColor(resources.getColor(colorRes));
    }

    @Override
    public void setContentTextSize(int size) {
        if (content != null) {
            content.setTextSize(size);
        }
    }

    @Override
    public void setButtonText(String btnText) {
        if (refreshButton != null) {
            refreshButton.setText(btnText);
        }
    }

    @Override
    public void setButtonTextRes(int btnTextRes) {
        setButtonText(getResources().getString(btnTextRes));
    }

    @Override
    public void setButtonShapeRes(int shapeRes) {
        if (refreshButton != null) {
            refreshButton.setBackgroundResource(shapeRes);
        }
    }

    @Override
    public void setButtonTextSize(int size) {
        if (refreshButton != null) {
            refreshButton.setTextSize(size);
        }
    }

    @Override
    public void setButtonBackgroundColor(int color) {
        if (refreshButton != null) {
            refreshButton.setBackgroundColor(color);
        }
    }

    @Override
    public void setButtonBackgroundColorRes(int colorRes) {
        setButtonBackgroundColor(resources.getColor(colorRes));
    }

    @Override
    public void setButtonTextColor(int color) {
        if (refreshButton!=null){
            refreshButton.setTextColor(color);
        }
    }

    public void setOnBtnClickListener(OnClickListener listener){
        refreshButton.setOnClickListener(listener);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getTitle() {
        return title;
    }

    public TextView getContent() {
        return content;
    }

    public Button getRefreshButton() {
        return refreshButton;
    }
}

