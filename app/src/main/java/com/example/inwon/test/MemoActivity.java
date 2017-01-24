package com.example.inwon.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

/**
 * Created by inwon on 2017-01-18.
 */

public class MemoActivity extends AppCompatActivity {

    EditText title, memo;
    String _title, _memo;
    Intent intent;
    Dbhelper dbhelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);
        title = (EditText) findViewById(R.id.title_edit);
        memo = (EditText) findViewById(R.id.memo_edit);
        dbhelper = new Dbhelper(getApplicationContext(),"memo.db",null,1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                Intent intent = new Intent();
                _title = title.getText().toString();
                _memo = memo.getText().toString();
                dbhelper.insert("insert into memo values ('"+_title+"','"+_memo+"')");
                intent.putExtra("title",_title);
                setResult(RESULT_OK,intent);
                finish();


        }
        return super.onOptionsItemSelected(item);
    }
}
