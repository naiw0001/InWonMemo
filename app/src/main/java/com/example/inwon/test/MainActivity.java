package com.example.inwon.test;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    Dbhelper dbhelper;
    String title,content;
    String temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbhelper = new Dbhelper(getApplicationContext(),"memo.db",null,1);
        intent = getIntent();
        temp = intent.getStringExtra("title");
        title = dbhelper.select_title(temp);
        content = dbhelper.select_content(temp);

        hasWindowOverlay(this);
    }



    public void start(View v){
        Intent intent = new Intent(this,TestActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("content",content);
        startService(intent);
    }
    public void end(View v){
        stopService(new Intent(this,TestActivity.class));
    }
    public final static int REQUEST_CODE = 3333;
    public void hasWindowOverlay(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:"+getPackageName()));
            startActivityForResult(intent, REQUEST_CODE);
            }
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if(requestCode == REQUEST_CODE){
             if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
                 if(Settings.canDrawOverlays(this)){
                     Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_SHORT).show();
                 }else {
                     Toast.makeText(MainActivity.this,"No",Toast.LENGTH_SHORT).show();
                 }
             }
         }
    }
}
