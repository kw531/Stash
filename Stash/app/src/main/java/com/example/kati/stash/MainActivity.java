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
import android.widget.Toast;


import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    DBHandler myDB = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);
        listView.setLongClickable(true);

        // These are for testing purposes only, will be removed in final version
        myDB.deleteAllYarn();
        myDB.addYarn(new Yarn(0, "Red Heart", "Comfy", "Red", "100% Acrylic", 5.3, 400.0));
        myDB.addYarn(new Yarn(1, "Baron", "Baby", "Blue", "100% Acrylic", 3, 400.0));
        myDB.addYarn(new Yarn(2, "Knit Picks", "Lux", "Purple", "100% Silk", 2, 50));
        refreshDB();
    }


    @Override
    public void onRestart() {
        // This updates the displayed list anytime this activity is restarted (e.g. a new yarn is added)
        super.onRestart();
        refreshDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.yarn_longclick_menu, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Handles the context menu on a long click on a yarn
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();

        int yarnID = info.position;

        switch (item.getItemId()) {
            case R.id.lc_Edit:
                //Placeholder until I create this activity.
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

    public void refreshDB() {
        // Handles the display of the inventory list
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> theList = new ArrayList<>();

        final Cursor data = myDB.getCursor();

        if (data.getCount() == 0) {
            Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
            theList.add("No yarns in inventory! Add some.");
            ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, theList);
            listView.setAdapter(listAdapter);
        } else {
            while (data.moveToNext()) {
                theList.add(data.getString(1) + " - " + data.getString(2) + " - " + data.getString(3));
                ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, theList);
                listView.setAdapter(listAdapter);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Displays information about the yarn on click
                int rowID = (int) parent.getItemIdAtPosition(position);
                // Toast is a thing for development only, will be removed in final version
                Toast.makeText(getBaseContext(), "ID sent: " + rowID + "", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, yarnView.class);
                intent.putExtra("key", rowID);
                startActivity(intent);
            }
        });

    }


    public void removeYarn(int ID) {
        // Deletes a yarn from the database
        myDB.deleteYarn(myDB.getYarn(ID));
        correctKeys(ID);
    }

    public void correctKeys(int removedID) {
        // Updates the primary key in the database
        for (int i = removedID + 1; i <= myDB.findLastID(); i++) {
            Yarn temp = myDB.getYarn(i);
            myDB.deleteYarn(myDB.getYarn(i));
            temp.setId(i - 1);
            myDB.addYarn(temp);
        }
    }
}

