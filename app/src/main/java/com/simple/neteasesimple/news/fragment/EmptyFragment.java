package com.simple.neteasesimple.news.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.simple.neteasesimple.R;
import com.simple.neteasesimple.until.GlideApp;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmptyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmptyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmptyFragment extends Fragment {
    private ImageView imageView;

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

        GlideApp.with(this)
                .load("http://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=f3fcb65bb2119313c743f8b65d036bea/c995d143ad4bd113d34fcfab5aafa40f4afb05ac.jpg")
                .into(imageView);
    }
}