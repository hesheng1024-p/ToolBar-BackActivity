package com.example.hp.toolbartest.Activity;

import android.os.Bundle;

import com.example.hp.toolbartest.R;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

public class SecondActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        //用于设置向右滑动为返回，该库默认是向上滑动为返回
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
    }

}
