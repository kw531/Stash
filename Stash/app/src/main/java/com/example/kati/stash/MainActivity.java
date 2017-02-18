package com.example.kati.stash;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.y;
import static android.R.id.list;
import static android.view.View.Y;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHandler myDB = new DBHandler(this);
        myDB.addYarn(new Yarn(1,"Red Heart","Comfy","Red","100% Acrylic",5.3,400.0));
        myDB.addYarn(new Yarn(2,"Baron","Comfy","Blue","100% Acrylic",3,400.0));
        myDB.addYarn(new Yarn(3,"Knit Picks","Lux","Purple","100% Silk",2,50));

        ListView listView = (ListView) findViewById(R.id.listView);

        ArrayList<String> theList = new ArrayList<>();

        Cursor data = myDB.getYarnCount();

        if(data.getCount()==0){
            Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                theList.add(data.getString(1));
                ListAdapter listAdapter=new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        }

        myDB.deleteAllYarn();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection in the main menu
        switch (item.getItemId()) {
            case R.id.menu_about:
                Intent intent = new Intent(this, about.class);
                startActivity(intent);
                return true;
            case R.id.menu_add_item:
                Intent addIntent = new Intent(this, AddYarnActivity.class);
                startActivity(addIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
