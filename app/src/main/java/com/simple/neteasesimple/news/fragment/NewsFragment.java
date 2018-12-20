package com.simple.neteasesimple.news.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.simple.neteasesimple.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.simple.neteasesimple.news.adapter.NewAdapter;
import com.simple.neteasesimple.news.adapter.ShowAdapter;
import com.simple.neteasesimple.news.bean.FragmentInfo;
import com.simple.neteasesimple.news.bean.ShowTabEvent;
import com.simple.neteasesimple.news.news_inner.HotFragment;
import com.simple.neteasesimple.until.NoScrollGridView;
import com.simple.neteasesimple.until.SharePrenceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;


public class NewsFragment extends Fragment {
    ArrayList<FragmentInfo> pages;
    NewAdapter mNewAdapter;

    ImageView add;

    //标示是否显示菜单
    boolean isShowMenu = false;

    RelativeLayout menu_title;
    FrameLayout menu;

    NoScrollGridView show,not_show;
    ShowAdapter showAdapter;
    ShowAdapter not_showAdapter;
    Button sort;

    SmartTabLayout smartTabLayout;
    ViewPager viewPager;

    //显示标题的缓存key
    final static  String SHOW_CONTENT="show";
    //不显示标题的缓存key
    final static  String NOT_SHOW_CONTENT="not_show";
    //当前显示的标题的顺序
    String lastTile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        initSubviews(view);
        setup();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        pages = new ArrayList<>();

        FrameLayout layout = getActivity().findViewById(R.id.tabs);
        layout.addView(View.inflate(getActivity(), R.layout.include_tab, null));

        smartTabLayout = getActivity().findViewById(R.id.smart_tab);
        viewPager =  getActivity().findViewById(R.id.viewpager);

        //需要显示的
        String conetnt = SharePrenceUtil.getString(getActivity(),SHOW_CONTENT);
        //不需要显示的
        String not_content = SharePrenceUtil.getString(getActivity(),NOT_SHOW_CONTENT);
        //缓存为空,没有修改过标题
        String[] titles;
        if(TextUtils.isEmpty(conetnt)){
            titles = getResources().getStringArray(R.array.news_titles);
            StringBuilder builder = new StringBuilder();
            for(int i = 0 ;i<titles.length;i++){
                builder.append(titles[i]);
                if(i!=titles.length-1){
                    builder.append("-");
                }
            }
            //获取到上次显示的标题
            lastTile = builder.toString();
        }else{
            titles =  conetnt.split("-");
            //获取到上次显示的标题
            lastTile = conetnt;
        }

        for (int i = 0; i < titles.length; i++) {
            FragmentInfo info;
            if (i == 0) {
                info = new FragmentInfo(new HotFragment(), titles[i]);
            } else {
                info = new FragmentInfo(new EmptyFragment(), titles[i]);
            }
            pages.add(info);
        }

        showAdapter = new ShowAdapter(titles, getContext());

        if(TextUtils.isEmpty(not_content)){
            not_showAdapter = new ShowAdapter(getContext());
        }else{
            String [] not =  not_content.split("-");
            not_showAdapter = new ShowAdapter(not,getContext());
        }

        show.setAdapter(showAdapter);
        not_show.setAdapter(not_showAdapter);

        mNewAdapter = new NewAdapter(getFragmentManager(), pages);
        viewPager.setAdapter(mNewAdapter);

        //!!!!关键代码,自动绑定数据
        smartTabLayout.setViewPager(viewPager);
        smartTabLayout.setDividerColors(Color.TRANSPARENT);

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void initSubviews(View view) {
        add = view.findViewById(R.id.add);
        menu_title = view.findViewById(R.id.menu_title);
        menu = view.findViewById(R.id.menu);

        // 显示or隐藏的栏目
        show = view.findViewById(R.id.show);
        not_show = view.findViewById(R.id.not_show);

        // 排序按钮
        sort = view.findViewById(R.id.sort);
    }

    public void setup() {

        show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isShowDel = showAdapter.isShowDel();
                if(isShowDel){
                    if(position==0){
                        Toast.makeText(getContext(),"热门栏目不能删除!!!!",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String title = showAdapter.delATitle(position);
                    not_showAdapter.addADate(title);
                }
            }
        });

        not_show.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                boolean isShowDel = showAdapter.isShowDel();
                if(isShowDel){
                    String title =   not_showAdapter.delATitle(position);
                    showAdapter.addADate(title);
                }
            }
        });

        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdapter.setShowDel();
                boolean isShow = showAdapter.isShowDel();

                if(isShow){
                    sort.setText("完成");
                }else{
                    sort.setText("删除排序");
                }

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //menu没显示
                if(!isShowMenu){

                    EventBus.getDefault().post(new ShowTabEvent(false));

                    Animation add_up = AnimationUtils.loadAnimation(getContext(), R.anim.add_up);
                    //控件放置在动画的最后一帧
                    add_up.setFillAfter(true);
                    add.startAnimation(add_up);

                    //顶部的标签先显示
                    menu_title.setVisibility(View.VISIBLE);
                    Animation top_menu_show = AnimationUtils.loadAnimation(getContext(),R.anim.top_menu_show);
                    menu_title.startAnimation(top_menu_show);

                    menu.setVisibility(View.VISIBLE);
                    Animation from_top = AnimationUtils.loadAnimation(getContext(),R.anim.from_top);
                    menu.startAnimation(from_top);

                    isShowMenu = true;
                } else {
                    //让删除按钮回复状态
                    showAdapter.setShowDelUnable();
                    EventBus.getDefault().post(new ShowTabEvent(true));

                    //menu没显示
                    Animation add_down = AnimationUtils.loadAnimation(getContext(),R.anim.add_down);
                    add_down.setFillAfter(true);
                    add.startAnimation(add_down);

                    //顶部的标签先隐藏
                    menu_title.setVisibility(View.VISIBLE);
                    Animation top_menu_hide = AnimationUtils.loadAnimation(getContext(),R.anim.top_menu_hide);
                    top_menu_hide.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            //执行完动画后,隐藏控件
                            menu_title.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    menu_title.startAnimation(top_menu_hide);

                    Animation to_top = AnimationUtils.loadAnimation(getContext(),R.anim.to_top);
                    to_top.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            menu.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    menu.startAnimation(to_top);

                    isShowMenu = false;
                    //修改后的内容
                    String content = showAdapter.getContent();
                    String not_content =not_showAdapter.getContent();
                    //
                    String[] newTiles = content.split("-");

                    //缓存修改后的内容
                    SharePrenceUtil.saveString(getContext(),SHOW_CONTENT,content);
                    SharePrenceUtil.saveString(getContext(),NOT_SHOW_CONTENT,not_content);

                    //做一个判断
                    //做了改变->刷新
                    //不刷新
                    if(lastTile.equals(content)){
                        return;
                    }
                    pages.clear();
                    for (int i = 0; i < newTiles.length; i++) {
                        FragmentInfo info;
                        if (i == 0) {
                            info = new FragmentInfo(new HotFragment(), newTiles[i]);
                        } else {
                            info = new FragmentInfo(new EmptyFragment(), newTiles[i]);
                        }
                        pages.add(info);
                    }
                    mNewAdapter.setDate(pages);
                    smartTabLayout.setViewPager(viewPager);
                    lastTile = content;
                }
            }
        });
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void isShowTab(ShowTabEvent event) {
        boolean isShow = event.isShow();
        if (isShow) {
            sort.setText("删除排序" );
        }
    }
}
