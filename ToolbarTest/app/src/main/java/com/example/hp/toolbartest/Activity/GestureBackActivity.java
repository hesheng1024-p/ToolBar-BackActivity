package com.example.hp.toolbartest.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.hp.toolbartest.AppManager;

/**
 * Created by HP on 2017/3/21.
 */

public class GestureBackActivity extends AppCompatActivity {

    private GestureDetector myDectector;
    private static final String TAG = "GestureBackActivity";
    boolean flingFinishEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        initGestureDetector();
        AppManager.getAppManager().addActivity(this);
    }

    private void initGestureDetector() {
        if (myDectector == null) {
            myDectector = new GestureDetector(this, (GestureDetector.OnGestureListener) new MyGestureListener());
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (flingFinishEnabled) {
            return myDectector.onTouchEvent(ev) || super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 手势监听器
     *
     */
    public class MyGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            // Toast.makeText(getApplicationContext(),"down",Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            // Toast.makeText(getApplicationContext(),"onSingleTapUp",Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            // TODO Auto-generated method stub

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            //通过获取手势的位置，来判定怎么滑动的
            if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 0) {
                Log.d(TAG, "向左滑动");
            } else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 0) {
                Log.d(TAG, "向右滑动");
                AppManager.getAppManager().finishActivity();
            }
            return false;
        }

    }
}
