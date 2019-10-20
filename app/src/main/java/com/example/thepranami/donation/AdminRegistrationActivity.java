package com.example.thepranami.donation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class AdminRegistrationActivity extends AppCompatActivity {

    EditText nameEditText, mobileEditText, emailEditText, passwordEditText, con_passwordEditText;
    Button registerButton;
    TextView loginTextView;
    ImageView adminImageView;
    SharedPreferences sharedPreferences;
    int PICK_IMAGE_REQUEST =1;
    Uri imgUri;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registration);

        adminImageView=(ImageView)findViewById(R.id.imageView_id);
        nameEditText=(EditText)findViewById(R.id.nameEditText_id);
        mobileEditText=(EditText)findViewById(R.id.mobileEditText_id);
        emailEditText=(EditText)findViewById(R.id.emailEditText_id);
        passwordEditText=(EditText)findViewById(R.id.passwordEditText_id);
        con_passwordEditText=(EditText)findViewById(R.id.con_passwordEditText_id);

        registerButton=(Button)findViewById(R.id.registerButton_id);
        loginTextView=(TextView)findViewById(R.id.loginTextView_id);

        adminImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFile();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(AdminRegistrationActivity.this, AdminLoginActivity.class);
                startActivity(loginIntent);
            }
        });
      SharedPreferences sharedPreferences = getSharedPreferences("ADMIN_DATA", MODE_PRIVATE);
       boolean check = sharedPreferences.getBoolean("REG_STATUS", false);
        if (check){
            Intent i = new Intent(AdminRegistrationActivity.this, AdminLoginActivity.class );
            startActivity(i);
            finish();
        }
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminRegister();
            }
        });

    }

    public void adminRegister(){
       Log.d("Reg", "Register.....");
        if (!validate()){
            onRegisterFailed();
            return;
        }
        registerButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(AdminRegistrationActivity.this,
                R.style.Theme_AppCompat_DayNight_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating.....");
        progressDialog.show();

        String name = nameEditText.getText().toString();
        String mobile = mobileEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String con_pass = con_passwordEditText.getText().toString();
        ///img to string
        adminImageView.buildDrawingCache();
        Bitmap image_bitmap = adminImageView.getDrawingCache();
        //String image = image_bitmap.toString();

        sharedPreferences = getSharedPreferences("ADMIN_DATA", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (password.equals(con_pass)){
            editor.putString("NAME", name);
            editor.putString("MOBILE", mobile);
            editor.putString("EMAIL", email);
            editor.putString("PASSWORD", password);
            editor.putBoolean("REG_STATUS", true);
            editor.putString("IMAGE", encodeTobase64(image_bitmap));
            editor.commit();
            Toast.makeText(AdminRegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(AdminRegistrationActivity.this, "Password is not matched?", Toast.LENGTH_SHORT).show();
        }

        //  Implement your own signup logic here.
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // On complete call either onSignupSuccess or onSignupFailed
                // depending on success
                onRegisterSuccess();
                Toast.makeText(AdminRegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                // onSignupFailed();
                progressDialog.dismiss();
            }
        }, 2000);
    }
    public void onRegisterSuccess(){
        registerButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }
    public void onRegisterFailed(){
        Toast.makeText(this, "Registration failed!", Toast.LENGTH_SHORT).show();
        registerButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String con_pass = con_passwordEditText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameEditText.setError("Name should be at least 3 characters");
            valid = false;
        }
        else {
            nameEditText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email address");
            valid = false;
        }
        else {
            emailEditText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordEditText.setError("Password length should be between 4 to 10 alphanumeric characters");
            valid = false;
        }
        else {
            passwordEditText.setError(null);
        }
        if (con_pass.isEmpty() || !con_pass.equals(password)) {
            con_passwordEditText.setError("Password is not matched");
            valid = false;
        }
        else {
            con_passwordEditText.setError(null);
        }

        return valid;
    }

    //// method for bitmap to base64
    public static String encodeTobase64(Bitmap bitmap) {
        Bitmap immage = bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }

    // for image
    public void OpenFile(){
        Intent imgIntent = new Intent();
        imgIntent.setType("image/*");
        imgIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(imgIntent, PICK_IMAGE_REQUEST);
        ///

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            imgUri = data.getData();
            Picasso.with(this).load(imgUri).into(adminImageView);
        }
    }

}
