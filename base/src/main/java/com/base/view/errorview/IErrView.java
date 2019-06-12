package com.base.view.errorview;

import android.graphics.Bitmap;

/**
 * @author Li Xiaopeng
 * @date 2019/1/3
 */
public interface IErrView {

    void setImageRes(int imageRes);
    void setImageBitmap(Bitmap bmp);

    void setTitleStr(String title);
    void setTitleRes(int titleRes);
    void setTitleTextColor(int color);
    void setTitleTextColorRes(int colorRes);
    void setTitleTextSize(int size);

    void setContentStr(String content);
    void setContentRes(int contentRes);
    void setContentTextColor(int color);
    void setContentTextColorRes(int colorRes);
    void setContentTextSize(int size);

    void setButtonText(String btnText);
    void setButtonTextRes(int btnTextRes);
    void setButtonShapeRes(int shapeRes);
    void setButtonTextSize(int size);
    void setButtonBackgroundColor(int color);
    void setButtonBackgroundColorRes(int colorRes);
    void setButtonTextColor(int color);
}
