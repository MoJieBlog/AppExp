package com.base.compat;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment implements IBase {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            readArgument(savedInstanceState);
        } else if (getArguments() != null) {
            readArgument(getArguments());
        }
        findView(view);
        initView();
        setListener();
        getData();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        writeArgument(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearListener();
    }
}
