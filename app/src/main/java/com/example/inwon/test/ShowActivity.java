package com.example.inwon.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by inwon on 2017-01-18.
 */

public class ShowActivity extends AppCompatActivity {
    Dbhelper dbhelper;
    Intent intent;
    String temp;
    String title,content;
    TextView text_title,text_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        intent = getIntent();
        text_title = (TextView)findViewById(R.id.show_title);
        text_content = (TextView)findViewById(R.id.show_memo);
        temp = intent.getStringExtra("title");
        dbhelper = new Dbhelper(getApplicationContext(),"memo.db",null,1);
        title = dbhelper.select_title(temp);
        content = dbhelper.select_content(temp);

        text_title.setText(title);
        text_content.setText(content);

    }
}
