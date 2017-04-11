package com.example.kati.stash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class yarnView extends AppCompatActivity {
    DBHandler myDB = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yarn_view);

        Bundle extras = getIntent().getExtras();
        int rowID = -99; // Default bad value

        if (extras==null) throw new AssertionError("Null value in yarn view intent");

        rowID = extras.getInt("key"); // get the id from the intent

        if (rowID==-99) throw new AssertionError("Bad value in yarn view row ID");


        Yarn yarn = myDB.getYarn(rowID);

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
        tot.setText(String.valueOf(yarn.getYardage() * yarn.getBallsAvailable()));

    }


}
