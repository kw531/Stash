package com.example.kati.stash;

/**
 * The activity where the user edits an exisiting yarn
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.kati.stash.R.id.et_ey_Color;


public class EditYarnActivity extends AppCompatActivity {
    EditText brandName;
    EditText yarnName;
    EditText color;
    EditText fiber;
    EditText yardage;
    EditText balls;
    Button add;
    DBHandler myDB = new DBHandler(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_yarn);

        Bundle extras = getIntent().getExtras();
        int rowID = -99; // Default bad value

        if (extras==null) throw new AssertionError("Null value in edit yarn intent");

        rowID = extras.getInt("key"); // get the id from the intent

        if (rowID==-99) throw new AssertionError("Bad value in edit yarn row ID");

        final int retrievedID = rowID;
        Yarn yarn = myDB.getYarn(rowID);

        // The EditText fields
        brandName = (EditText) findViewById(R.id.et_ey_BrandName);
        yarnName = (EditText) findViewById(R.id.et_ey_YarnName);
        color = (EditText) findViewById(et_ey_Color);
        fiber = (EditText) findViewById(R.id.et_ey_Fiber);
        yardage = (EditText) findViewById(R.id.et_ey_yardage);
        balls = (EditText) findViewById(R.id.et_ey_TotalBalls);

        // Display the current information to the user
        brandName.setText(yarn.getBrandName());
        yarnName.setText(yarn.getYarnName());
        color.setText(yarn.getColor());
        fiber.setText(yarn.getFiber());
        yardage.setText(Double.toString(yarn.getYardage()));
        balls.setText(Double.toString(yarn.getBallsAvailable()));

        add = (Button) findViewById(R.id.EditYarn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (brandName.length() != 0 && fiber.length() != 0 && color.length() != 0 &&
                        yarnName.length() != 0 && balls.length() != 0) {
                    myDB.deleteYarn(myDB.getYarn(retrievedID)); // Delete the exisiting yarn

                    // Replace deleted yarn with a new yarn at the same location
                    myDB.addYarn(new Yarn(retrievedID,
                            brandName.getText().toString(),
                            yarnName.getText().toString(),
                            color.getText().toString(),
                            fiber.getText().toString(),
                            Double.parseDouble(balls.getText().toString()),
                            Double.parseDouble(balls.getText().toString())));
                    Toast.makeText(EditYarnActivity.this, "Yarn successfully updated", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(EditYarnActivity.this, "You're missing something...", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
