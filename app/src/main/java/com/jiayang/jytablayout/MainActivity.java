package com.jiayang.jytablayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private String[] mStrings = new String[]{"精选", "体育", "巴萨", "购物", "明星", "视频", "健康", "励志", "图文", "本地", "动漫", "搞笑", "精选"};
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        initViewPager();
    }

    private void initViewPager() {
        List<String> titles = new ArrayList<>();

        for (int i = 0 ; i < mStrings.length ; i ++) {
            titles.add(mStrings[i]);
        }


        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0 ; i < mStrings.length ; i ++) {
            fragments.add(new ListFragment());
        }


        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), titles, fragments);

        //给ViewPager设置适配器
        mViewPager.setAdapter(fragmentAdapter);
        //将TabLayout和ViewPager关联起来。
        mTabLayout.setupWithViewPager(mViewPager);
        //给TabLayout设置适配器
        mTabLayout.setTabsFromPagerAdapter(fragmentAdapter);
    }
}
