package com.example.inwon.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by inwon on 2017-01-18.
 */

public class MainList extends AppCompatActivity {

    ListView list;
    ArrayAdapter adapter;
    ArrayList<String> item;
    Dbhelper dbhelper;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create) {
            Intent intent = new Intent(MainList.this, MemoActivity.class);
            startActivityForResult(intent, 1);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int index = info.position;

        String title = this.item.get(index);
        Log.d("ccccccccccccccc",title);
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(MainList.this,MainActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
                break;
            case 2:
                this.item.remove(index);
                adapter.notifyDataSetChanged();
                dbhelper.delete("delete from memo where title = '"+title+"' ");
                break;
            case 3:
                break;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("선택하세요");
        menu.add(0, 1, 100, "띄우기");
        menu.add(0, 2, 100, "삭제");
        menu.add(0, 3, 100, "취소");

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_list);
        dbhelper = new Dbhelper(getApplicationContext(), "memo.db", null, 1);
        item = new ArrayList<String>();
        int i = dbhelper.row_count();
        String[] array_title = dbhelper.select_title();
        String[] temp = new String[i];

        for (int a = 0; a < i; a++) {
            temp[a] = array_title[a];
            item.add(temp[a]);
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, item);
        list = (ListView) findViewById(R.id.main_list);
        list.setAdapter(adapter);
        registerForContextMenu(list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainList.this, ShowActivity.class);
                String title = item.get(position);
                intent.putExtra("title", title);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                String title = data.getStringExtra("title");
                item.add(title);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
