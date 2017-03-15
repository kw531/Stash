package com.example.kati.stash;

/**
 * The activity where the user adds a new yarn
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.kati.stash.R.id.et_add_Color;


public class AddYarnActivity extends AppCompatActivity {
    EditText brandName;
    EditText yarnName;
    EditText color;
    EditText fiber;
    EditText yardage;
    EditText balls;
    DBHandler db;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(this);
        setContentView(R.layout.activity_add_yarn);
        brandName = (EditText) findViewById(R.id.et_add_BrandName);
        yarnName = (EditText) findViewById(R.id.et_add_YarnName);
        color = (EditText) findViewById(et_add_Color);
        fiber = (EditText) findViewById(R.id.et_add_Fiber);
        yardage = (EditText) findViewById(R.id.et_add_yardage);
        balls = (EditText) findViewById(R.id.et_add_TotalBalls);

        add=(Button) findViewById(R.id.AddYarn);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(brandName.length()!= 0 && fiber.length()!= 0 && color.length()!= 0 &&
                        yarnName.length()!= 0 && balls.length()!= 0 ) {
                    db.addYarn(new Yarn(db.findLastID() + 1,
                            brandName.getText().toString(),
                            yarnName.getText().toString(),
                            color.getText().toString(),
                            fiber.getText().toString(),
                            Double.parseDouble(balls.getText().toString()),
                            Double.parseDouble(balls.getText().toString())));
                    Toast.makeText(AddYarnActivity.this, "Successfully Added",Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(AddYarnActivity.this, "You're missing something...",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
