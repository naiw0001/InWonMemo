package com.example.inwon.test;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by inwon on 2017-01-18.
 */

public class TestActivity extends Service {
    private View mView;
    private WindowManager mManager;
    private float mTouchX, mTouchY;
    private int mViewX, mViewY;
    private String title, content;
    private TextView text_title, text_content;
    WindowManager.LayoutParams mParams;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        text_title.setText(title);
        text_content.setText(content);


        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mManager != null) {
            if (mView != null) mManager.removeView(mView);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = mInflater.inflate(R.layout.test, null);

        text_title = (TextView) mView.findViewById(R.id.view_title);
        text_content = (TextView) mView.findViewById(R.id.view_content);

        mView.setOnTouchListener(mViewTouchListener);
        mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE, // 터치 O 항상 최상위
//                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY, <- 터치 x 항상 최상위
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, // 포커스 x
//                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH, 해당 view 터치 x
                PixelFormat.TRANSLUCENT); //투명 처리
        mParams.gravity = Gravity.TOP | Gravity.LEFT;

        mManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        mManager.addView(mView, mParams);


    }

    private View.OnTouchListener mViewTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mTouchX = event.getRawX();
                    mTouchY = event.getRawY();
                    mViewX = mParams.x;
                    mViewY = mParams.y;

                    break;

                case MotionEvent.ACTION_UP:
                    break;

                case MotionEvent.ACTION_MOVE:
                    int x = (int) (event.getRawX() - mTouchX);
                    int y = (int) (event.getRawY() - mTouchY);

                    mParams.x = mViewX + x;
                    mParams.y = mViewY + y;

                    mManager.updateViewLayout(mView, mParams);

                    break;
            }

            return true;
        }
    };
}
