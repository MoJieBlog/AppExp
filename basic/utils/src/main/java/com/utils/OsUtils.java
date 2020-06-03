package com.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

import java.io.File;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-05-29
 */
public final class OsUtils {

    /**
     * 获取屏幕宽，高度包括通知栏和虚拟按键
     *
     * @return
     */
    public static int getWinWide(@NonNull Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int widthPixels = dm.widthPixels;
        return widthPixels;
    }


    /**
     * 获取屏幕高
     *
     * @return
     */
    public static int getWinHeight(@NonNull Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int heightPixels = dm.heightPixels;
        return heightPixels;
    }

    /**
     * 获取可编辑区域的高度
     *
     * @param activity
     * @return
     */
    public static int getDisHeight(@NonNull Activity activity) {
        /**
         * 获取状态栏高度——方法1
         * */
        int heightPixels = -1;
        //获取status_bar_height资源的ID
        Rect outRect1 = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        heightPixels = outRect1.height();
        return heightPixels;
    }

    /**
     * 获取可编辑区域的高度
     *
     * @param activity
     * @return
     */
    public static int getDisWidth(@NonNull Activity activity) {
        /**
         * 获取状态栏高度——方法1
         * */
        int heightPixels = -1;
        //获取status_bar_height资源的ID
        Rect outRect1 = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        heightPixels = outRect1.width();
        return heightPixels;
    }

    public static int getStatusBarHeight(@NonNull Context context) {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    /**
     * 打开系统相机拍照
     *
     * @param activity
     * @param picPath
     */
    public static void startSystemCamera(@NonNull Activity activity, @NonNull String picPath, int requestCodeTakePhoto) {
        if (activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File file = new File(picPath);
            //调用系统相机
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
                Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            } else {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
            }
            activity.startActivityForResult(cameraIntent, requestCodeTakePhoto);
        } else {
            ToastUtils.toastText("没有系统相机");
        }
    }

    /**
     * 裁剪图片
     *
     * @param activity
     * @param uri
     * @param outPutFile
     * @param wide
     * @param height
     * @param requestCode
     */
    public static void startPictureCrop(@NonNull Activity activity, @NonNull Uri uri, @NonNull File outPutFile, int wide, int height, int requestCode) {
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                //添加这一句表示对目标应用临时授权该Uri所代表的文件
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");//可裁剪
            intent.putExtra("aspectX", wide);
            intent.putExtra("aspectY", height);
            intent.putExtra("outputX", wide);
            intent.putExtra("outputY", height);
            intent.putExtra("scale", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));
            intent.putExtra("return-data", false);//若为false则表示不返回数据
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", true);
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
