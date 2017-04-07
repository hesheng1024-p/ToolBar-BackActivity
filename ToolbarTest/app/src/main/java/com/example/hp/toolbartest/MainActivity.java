package com.example.hp.toolbartest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hp.toolbartest.Activity.SecondActivity;
import com.example.hp.toolbartest.Adapter.FruitAdapter;
import com.example.hp.toolbartest.Fruit.Fruit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private Fruit[] mFruits = {
            new Fruit("菠萝", R.drawable.boluo), new Fruit("大枣",R.drawable.dazhao),
            new Fruit("哈密瓜", R.drawable.hamigua), new Fruit("火龙果", R.drawable.huolongguo),
            new Fruit("金桔", R.drawable.jinju), new Fruit("榛子", R.drawable.zhenzi),
            new Fruit("樱桃", R.drawable.yingtao), new Fruit("杨桃", R.drawable.yangtao),
            new Fruit("西梅", R.drawable.ximei), new Fruit("西瓜", R.drawable.xigua),
            new Fruit("桃", R.drawable.tao), new Fruit("圣女果", R.drawable.shengnvguo),
            new Fruit("蛇果", R.drawable.sheguo), new Fruit("山楂", R.drawable.shanzha),
            new Fruit("桑椹", R.drawable.sangshen), new Fruit("荸荠", R.drawable.biqi),
            new Fruit("猕猴桃", R.drawable.mihoutao), new Fruit("枇杷", R.drawable.pipa),
            new Fruit("龙眼", R.drawable.longyan), new Fruit("栗子", R.drawable.lizi)};

    private List<Fruit> mFruitList = new ArrayList<>();
    private FruitAdapter mFruitAdapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);//设置app logo
        toolbar.setTitle("Title");//设置主标题
        toolbar.setSubtitle("Subtitle");//设置子标题
        setSupportActionBar(toolbar);
        //侧滑
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        setDrawerLeftEdgeSize(this, mDrawerLayout, 1.0f);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);  //让图标按钮展示出来
            actionBar.setHomeAsUpIndicator(R.drawable.menu);  //设置图标在toolbar上
        }
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
        final NavigationView navigationView2 = (NavigationView) findViewById(R.id.nav_view2);
        navigationView2.setCheckedItem(R.id.nav_call);
        navigationView2.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

        //悬浮按钮
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //与Toast类似的提示工具，拥有可交互按钮
                Snackbar.make(v, "Data deleted", Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //提示信息中的可交互按钮
                                Toast.makeText(MainActivity.this, "restored",
                                        Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(MainActivity.this,SecondActivity.class));
                                startActivity(SecondActivity.class);
                            }
                        }).show();
            }
        });

        initFruit();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        mFruitAdapter = new FruitAdapter(mFruitList);
        recyclerView.setAdapter(mFruitAdapter);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimaryDark);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });
    }

    private void refreshFruits() {
        //开启一个线程，将线程沉睡
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //返回主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruit();
                        mFruitAdapter.notifyDataSetChanged();  //更新数据
                        swipeRefresh.setRefreshing(false);  //刷新结束
                    }
                });
            }
        }).start();
    }

    private void initFruit() {
        mFruitList.clear();
        for (int i=0; i<50; i++) {
            Random random = new Random();
            int index = random.nextInt(mFruits.length);
            mFruitList.add(mFruits[index]);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //HomeAsUp按钮
                mDrawerLayout.openDrawer(GravityCompat.START);  //展示侧滑
                break;
            case R.id.backup:
                Toast.makeText(this, "Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    //通过反射设置滑动距离
    public static void setDrawerLeftEdgeSize(Activity activity, DrawerLayout drawerLayout,
                                             float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            //当传入键值为mRightDragger时，实现右拉全局滑动，获得右边的距离，为mLeftDragger时，为左边
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int)
                    (dm.widthPixels * displayWidthPercentage)));
        } catch (Exception e) {
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    //启动活动时，添加到AppManager，并设置返回时的动画
    public void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_left);
    }

}
