package com.base.compat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * @describe
 * @author: lixiaopeng
 * @Date: 2019-10-23
 */
public interface IBase {

    default void readArgument(@NonNull Bundle bundle) {
    }

    default void writeArgument(@NonNull Bundle bundle) {
    }

    /***fragment用这个****/
    default void findView(@NonNull View view) {
    }

    default void findView() {
    }

    default void initView() {
    }

    default void getData() {
    }

    default void setListener() {
    }

    default void clearListener() {
    }
}
