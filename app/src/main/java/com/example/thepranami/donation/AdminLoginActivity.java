package com.example.thepranami.donation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity {
    ImageView loginImageView;
   private EditText loginEmailEditText, loginPasswordEditText;
   TextView forgotTextView, aboutUsTextView;
   private Button loginButton;
   SharedPreferences preferences;
   ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        actionBar = getSupportActionBar();
        actionBar.setSubtitle("Admin Login Panel");
       // loginImageView=(ImageView)findViewById(R.id.loginImageView_id);
        loginEmailEditText=(EditText)findViewById(R.id.loginEmailEditText_id);
        loginPasswordEditText=(EditText)findViewById(R.id.loginPasswordEditText_id);
        forgotTextView=(TextView)findViewById(R.id.forgotPasswordTextView_id);
        aboutUsTextView=(TextView)findViewById(R.id.aboutUsTextView_id);
        loginButton=(Button)findViewById(R.id.loginButton_id);
        preferences = getSharedPreferences("ADMIN_DATA", MODE_PRIVATE);
         // set image in imageView
        //loginImageView.buildDrawingCache();
      /*  String SavedImage = preferences.getString("IMAGE", "");
        Bitmap bit_img = decodeBase64(SavedImage.toString());
        loginImageView.setImageBitmap(bit_img);   */
        forgotTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotIntent = new Intent(AdminLoginActivity.this, ForgotPassword.class);
                startActivity(forgotIntent);
            }
        });
        aboutUsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String savedEmail = preferences.getString("EMAIL", "");
                String savedPass = preferences.getString("PASSWORD", "");
                String inputEmail= loginEmailEditText.getText().toString();
                String inputPass= loginPasswordEditText.getText().toString();
                if (inputEmail.isEmpty()|| inputPass.isEmpty()){
                    Toast.makeText(AdminLoginActivity.this, "Please fill email and password", Toast.LENGTH_SHORT).show();
                }
                else if (savedEmail.equals(inputEmail) && savedPass.equals(inputPass)){

                    Toast.makeText(AdminLoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AdminLoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    loginEmailEditText.setText("");
                    loginPasswordEditText.setText("");
                }
                else{
                    Toast.makeText(AdminLoginActivity.this, "You entered wrong email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // // method for base64 to bitmap
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_page_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.exit_menu_id:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdminLoginActivity.this);
                alertBuilder.setMessage("Do you want to exit ?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = alertBuilder.create();
                alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
