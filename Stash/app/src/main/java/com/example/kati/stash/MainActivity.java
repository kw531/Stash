package com.example.kati.stash;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Adapter;
import android.widget.Toast;
import android.app.ListActivity;


import java.util.ArrayList;
import java.util.Hashtable;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;


public class MainActivity extends AppCompatActivity{

    DBHandler myDB = new DBHandler(this);
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);
        listView.setLongClickable(true);

        myDB.deleteAllYarn();
        myDB.addYarn(new Yarn(0, "Red Heart", "Comfy", "Red", "100% Acrylic", 5.3, 400.0));
        myDB.addYarn(new Yarn(1, "Baron", "Baby", "Blue", "100% Acrylic", 3, 400.0));
        myDB.addYarn(new Yarn(2, "Knit Picks", "Lux", "Purple", "100% Silk", 2, 50));
        refreshDB();
    }


/*    @Override
    public boolean onItemLongClick(AdapterView<?> l, View v,
                                   final int position, long id) {

        yarnID=position;
        Toast.makeText(this, "long clicked pos: " + position, Toast.LENGTH_LONG).show();

        return true;
    }*/

    @Override
    public void onRestart() {
        super.onRestart();
        refreshDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.yarn_longclick_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        int yarnID=info.position;

        switch (item.getItemId()) {
            case R.id.lc_Edit:
                Toast.makeText(this, "EDIT GOES HERE", Toast.LENGTH_LONG).show();
                return true;
            case R.id.lc_Delete:
                removeYarn(yarnID);
                refreshDB();
                Toast.makeText(this, "Yarn Deleted", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    //Hashtable<Integer, String> yarnTable = new Hashtable<>();
    public void refreshDB() {
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> theList = new ArrayList<>();

        final Cursor data = myDB.getCursor();
        //Toast.makeText(this, "count:" + myDB.findLastID(), Toast.LENGTH_LONG).show();

        if (data.getCount() == 0) {
            Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
        } else {
            while (data.moveToNext()) {
                //yarnTable.put(data.getInt(0),data.getString(2));
                theList.add(data.getString(1) + " - " + data.getString(2) + " - " + data.getString(3));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int rowID=(int)parent.getItemIdAtPosition(position);
                Toast.makeText(getBaseContext(), "ID sent: " + rowID  + "", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, yarnView.class);
                intent.putExtra("key",rowID);
                startActivity(intent);
            }
        });

    }


    public void removeYarn(int ID){
        myDB.deleteYarn(myDB.getYarn(ID));
    }
}

