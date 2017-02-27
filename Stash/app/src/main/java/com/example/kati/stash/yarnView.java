package com.example.kati.stash;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.data;
import static android.R.attr.y;

public class yarnView extends AppCompatActivity {
    DBHandler myDB = new DBHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yarn_view);

        Bundle extras = getIntent().getExtras();
        int rowID=-99;

        if (extras != null) {
            rowID = extras.getInt("key");
            // and get whatever type user account id is
        }

        if(rowID==-99) { Toast.makeText(getBaseContext(), "NOOOOOOO", Toast.LENGTH_LONG).show();
        } else { Toast.makeText(getBaseContext(), "ID rec: " + rowID, Toast.LENGTH_LONG).show();}
        Yarn yarn=myDB.getYarn(rowID);


        TextView bn = (TextView) findViewById(R.id.tv_yv_BrandName);
        bn.setText(yarn.getBrandName());

        TextView yn = (TextView) findViewById(R.id.tv_yv_YarnName);
        yn.setText(yarn.getYarnName());

        TextView co = (TextView) findViewById(R.id.tv_yv_Color);
        co.setText(yarn.getColor());

        TextView fb = (TextView) findViewById(R.id.tv_yv_Fiber);
        fb.setText(yarn.getFiber());

        TextView yd = (TextView) findViewById(R.id.tv_yv_Yardage);

        yd.setText(String.valueOf(yarn.getYardage()));


        TextView ba = (TextView) findViewById(R.id.tv_yv_SkeinsAvail);
        ba.setText(String.valueOf(yarn.getBallsAvailable()));

        TextView tot = (TextView) findViewById(R.id.tv_yv_TotalYards);
        tot.setText(String.valueOf(yarn.getYardage()*yarn.getBallsAvailable()));

    }


}
