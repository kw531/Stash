package com.example.kati.stash;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Toast;


import java.util.ArrayList;

import static android.R.attr.start;
import static android.R.attr.y;


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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection in the main menu
        switch (item.getItemId()) {
            case R.id.menu_about:
                Intent aboutIntent = new Intent(this, about.class);
                startActivity(aboutIntent);
                return true;
            case R.id.menu_add_item:
                Intent addIntent = new Intent(this, AddYarnActivity.class);
                startActivity(addIntent);
                return true;
            case R.id.sort_brand_AtoZ:
                sortByBrand();
                refreshDB();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                // Sends the intent to the listener in the Edit Yarn Activity
                Intent intent = new Intent(MainActivity.this, EditYarnActivity.class);
                int rowID = yarnID;
                intent.putExtra("key", rowID);
                startActivity(intent);
                //Toast.makeText(this, "EDIT GOES HERE", Toast.LENGTH_LONG).show();
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

    public void sortByBrand(){
        boolean swapFlag=true;
        while(swapFlag) {
            swapFlag=false;
            for (int i = 0; i < myDB.findLastID()-1; i++) {
                if(myDB.getYarn(i+1).getBrandName().compareTo(myDB.getYarn(i).getBrandName())<0){
                    // If the next yarn has a lower letter than the current, swap
                    Yarn temp = myDB.getYarn(i);
                    Yarn temp2 = myDB.getYarn(i + 1);

                    myDB.deleteYarn(temp);
                    myDB.deleteYarn(temp2);

                    temp.setId(i + 1); // Swap the IDs
                    temp2.setId(i);

                    myDB.addYarn(temp);
                    myDB.addYarn(temp2);

                    swapFlag=true;
                }
            }
        }
    }
}

