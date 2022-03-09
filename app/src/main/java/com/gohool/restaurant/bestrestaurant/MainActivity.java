package com.gohool.restaurant.bestrestaurant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gohool.restaurant.bestrestaurant.DatabaseHandler.DBOrderItem;

public class MainActivity extends AppCompatActivity {

    Button btnSignIn;
    Button btnAboutUs;
    TextView txtSlogan;
    DBOrderItem db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        btnAboutUs = (Button) findViewById(R.id.btnAboutUs);
        txtSlogan = (TextView) findViewById(R.id.txtSlogan);
        Typeface nabilafont = Typeface.createFromAsset(getAssets(), "fonts/Nabila.ttf");
        txtSlogan.setTypeface(nabilafont);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                finish();

                startActivity(loginIntent);
            }
        });
    }
}
