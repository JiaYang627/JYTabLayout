# JYTabLayout

> 使用TabLayout实现类似网易选项卡的动态滑动效果

先上UI图(无图说个XX啊)

## AndroidUI

![JYTabLayout-3](http://a3.qpic.cn/psb?/V14YlNrL2eQEkW/kjnPjQO6.GPUTJo5wF0WYCHRACbFwE.gsXtQIR3EiWQ!/b/dBABAAAAAAAA&bo=VQJ.A1UCfgMCCCw!&rf=viewer_4)

配置build.gradle:

```
dependencies {
    ...

    compile 'com.android.support:appcompat-v7:25.3.1'
    compile 'com.android.support:design:25.3.1'
    compile 'com.android.support:recyclerview-v7:25.3.1'
    compile 'com.android.support:cardview-v7:25.3.1'
}
```

此Demo中需使用RecyclerView和CardView，所有这里引用com.android.support:recyclerview和com.android.support:cardview。

主要说一下主界面的布局如何搭建的，先上布局代码：

```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/activity_main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    >


    <android.support.design.widget.AppBarLayout
        android:id="@+id/appBarLayout"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">


        <android.support.v7.widget.Toolbar
            android:id="@+id/toolBar"
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            app:layout_scrollFlags="scroll|enterAlways"
            app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>


        <android.support.design.widget.TabLayout
            android:id="@+id/tabLayout"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:tabIndicatorColor="#ADBE107E"
            app:tabMode="scrollable">

        </android.support.design.widget.TabLayout>

    </android.support.design.widget.AppBarLayout>

    <android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior">

    </android.support.v4.view.ViewPager>

</LinearLayout>

```

此布局用到了AppBarLayout 和 Toolbar，而AppBarLayout是Android Design Support Library 新加入的控件，继承自LinearLayout。

此处用AppBarLayout是将Toolbar和TabLayout组合起来作为一个整体。

此布局中最关键的一点主要是：android.support.design.widget.TabLayout 标签中的 app:tabMode="scrollable" ，意思是设置Tab的模式为"可滑动的"。此标签还能设置不可滑动：app:tabMode="fixed"。如果我们的Tab的数量过多的话，如果设置不可滑动，选项就会被彼此压缩。例如：此Demo中设置了13个，如果设置为不可滑动的话，就会出现此情况：


![JYTabLayout-1](http://a3.qpic.cn/psb?/V14YlNrL2eQEkW/76UytzyBlAK1aj4uXgO5rrCP*On1xlKN*w2aikcEWAA!/b/dBMBAAAAAAAA&bo=WgKkA1oCpAMDACU!&rf=viewer_4)

***

这种情况肯定不是我们想要的那种，所以我们要将标签设置为"可滑动的"。

![JYTabLayout-2](http://a1.qpic.cn/psb?/V14YlNrL2eQEkW/jWbsUJjeQSq*XgZRi3WVPfjasH2.NsUx8TCX2w1N4mk!/b/dEIAAAAAAAAA&bo=WgKkA1oCpAMDACU!&rf=viewer_4)

可以查看两种效果。

好了，布局写好了，接下来要在MainActivity中写我们的主要逻辑代码了。逻辑代码也很简单，此Demo中设定了13个标题并创建了相应的TabLayout和Fragment，然后设置ViewPager适配器和TabLayout适配器，将TabLayout和ViewPager联动起来。


```
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
```

此处自创建一ListFragment作为每个Tab的界面。


```
@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.list_fragment, container, false);
        return mRecyclerView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getActivity()));
    }
```

Fragment引入的布局中就一个RecyclerView，关于RecyclerView的创建、点击事件此处就不再说了。
