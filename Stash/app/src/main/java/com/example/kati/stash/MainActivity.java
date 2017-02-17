package com.example.kati.stash;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import static android.R.attr.y;
import static android.R.id.list;

public class MainActivity extends AppCompatActivity {
    int lastVisibleItem = 0;
    DBHandler db = new DBHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db.addYarn(new Yarn(1,"Red Heart","Comfy","Red","100% Acrylic",5.3,400.0));
        db.addYarn(new Yarn(2,"Red Heart","Comfy","Blue","100% Acrylic",3,400.0));
        db.addYarn(new Yarn(3,"Knit Picks","Lux","Purple","100% Silk",2,50));

        List<Yarn> inventory= db.getAllYarns();



       // View emptyView = findViewById(R.id.empty_view);
       // listView.setEmptyView(emptyView);


       /** //Handles the endless scrolling
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == 0) return;
                final int currentFirstVisibleItem = view.getFirstVisiblePosition();
                //Something here?
                lastVisibleItem = currentFirstVisibleItem;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });*/
        /** Dummy Data for the next minute*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
