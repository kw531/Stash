package com.example.kati.stash;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.value;
import static android.R.attr.y;
import static android.R.id.list;
import static android.provider.Contacts.SettingsColumns.KEY;
import static android.view.View.Y;
import static com.example.kati.stash.R.id.listView;

public class MainActivity extends AppCompatActivity {

    DBHandler myDB = new DBHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);

        myDB.deleteAllYarn();
        myDB.addYarn(new Yarn(0, "Red Heart", "Comfy", "Red", "100% Acrylic", 5.3, 400.0));
        myDB.addYarn(new Yarn(1, "Baron", "Baby", "Blue", "100% Acrylic", 3, 400.0));
        myDB.addYarn(new Yarn(2, "Knit Picks", "Lux", "Purple", "100% Silk", 2, 50));
        refreshDB();
    }

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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.yarn_longclick_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Context menu button has been clicked
        switch (item.getItemId()) {
            case R.id.lc_Edit:
                Toast.makeText(this, "EDIT GOES HERE", Toast.LENGTH_LONG).show();
                return true;
            case R.id.lc_Delete:
                Toast.makeText(this, "DELETE GOES HERE", Toast.LENGTH_LONG).show();
            return true;
            default:
                return super.onContextItemSelected(item);
        }
    }



    public void refreshDB() {
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayList<String> theList = new ArrayList<>();
        final Cursor data = myDB.getCursor();
        //Toast.makeText(this, "count:" + myDB.findLastID(), Toast.LENGTH_LONG).show();

        if (data.getCount() == 0) {
            Toast.makeText(this, "There are no contents in this list!", Toast.LENGTH_LONG).show();
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
                //parent.getItemAtPosition(position)
                int rowID=(int)parent.getItemIdAtPosition(position);
                Toast.makeText(getBaseContext(), "ID sent: " + rowID  + "", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, yarnView.class);
                intent.putExtra("key",rowID);
                startActivity(intent);
            }
        });
    }


}

