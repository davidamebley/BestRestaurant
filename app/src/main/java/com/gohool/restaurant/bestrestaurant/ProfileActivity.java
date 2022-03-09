package com.gohool.restaurant.bestrestaurant;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.TextView;
import com.gohool.restaurant.bestrestaurant.Model.User;

/** Here we are only displaying the User data and we have
        a button that will let the user logout from the app.*/

public class ProfileActivity extends AppCompatActivity {

           TextView textViewId, textViewUsername, textViewEmail, textViewGender;

           @Override
           protected void onCreate(Bundle savedInstanceState) {
               super.onCreate(savedInstanceState);
               setContentView(R.layout.activity_profile);
               AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

               //if the user is not logged in
               //starting the login activity
               if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
                   finish();
                   startActivity(new Intent(this, LoginActivity.class));
               }


               textViewId = (TextView) findViewById(R.id.textViewId);
               textViewUsername = (TextView) findViewById(R.id.textViewUsername);
               textViewEmail = (TextView) findViewById(R.id.textViewEmail);


               //getting the current user
               User user = SharedPrefManager.getInstance(this).getUser();

               //setting the values to the textviews
               textViewId.setText(String.valueOf(user.getId()));
               textViewUsername.setText(user.getUsername());
               textViewEmail.setText(user.getEmail());

               //when the user presses logout button
               //calling the logout method
               findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       finish();
                       SharedPrefManager.getInstance(getApplicationContext()).logout();
                   }
               });
           }
}
