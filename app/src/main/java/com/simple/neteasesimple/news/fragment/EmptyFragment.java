package com.simple.neteasesimple.news.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.simple.neteasesimple.R;



public class EmptyFragment extends Fragment {
    public SimpleDraweeView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty, container, false);
        imageView = view.findViewById(R.id.image);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        imageView.setImageURI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1545043973749&di=22dd6d0f13dc09ab26dd82783d318a5d&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F4b90f603738da977c7804d01bb51f8198618e36a.jpg");
    }
}