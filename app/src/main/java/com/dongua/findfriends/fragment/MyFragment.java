package com.dongua.findfriends.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dongua.findfriends.R;
import com.dongua.framework.base.BaseFragment;

public class MyFragment extends BaseFragment {

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_me,null);
        return view;
    }
}
