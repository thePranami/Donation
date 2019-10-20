package com.example.thepranami.donation;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ForgotPassword extends AppCompatActivity {
    private static EditText emailEditText, mobileEditText, otpEditText, newPassEditText, conPassEditText;
    private static Button doneButton, nextButton, changePassButton;
    LinearLayout linearOne, linearTwo, linearThree;
    SharedPreferences sharedPreferences;
    SmsManager smsManager;
    TelephonyManager telephonyManager;
    Random random;
    private SharedPreferences preferences;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailEditText=(EditText)findViewById(R.id.forgotEmailEditText_id);
        mobileEditText=(EditText)findViewById(R.id.forgotMobileEditText_id);
        doneButton=(Button)findViewById(R.id.forgot_doneButton_id);
        otpEditText=(EditText)findViewById(R.id.otpEditText_id);
        nextButton=(Button)findViewById(R.id.nextButton_id);
        linearOne=(LinearLayout)findViewById(R.id.linear_one_id);
        linearTwo=(LinearLayout)findViewById(R.id.linear_two_id);
        linearThree=(LinearLayout)findViewById(R.id.linear_three_id);

        newPassEditText=(EditText)findViewById(R.id.newPasswordEditText_id);
        conPassEditText=(EditText)findViewById(R.id.con_newPasswordEditText_id);
        changePassButton=(Button)findViewById(R.id.change_passButton_id);
        textView=(TextView)findViewById(R.id.textView_id);

        linearTwo.setVisibility(View.GONE);
        linearThree.setVisibility(View.GONE);
        final String generatedOTP[] = {null};

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("ADMIN_DATA", MODE_PRIVATE);
                String savedEmail = sharedPreferences.getString("EMAIL", "");
                String savedMobile = sharedPreferences.getString("MOBILE", "");

                String Femail = emailEditText.getText().toString();
                String Fmobile = mobileEditText.getText().toString();

                if (Femail.isEmpty()|| Fmobile.isEmpty()){
                    Toast.makeText(ForgotPassword.this, "Please fill email and mobile", Toast.LENGTH_SHORT).show();
                }
                else if (Femail.equals(savedEmail) && Fmobile.equals(savedMobile)){
                    Random random = new Random();
                    generatedOTP[0] = String.format("%04d", random.nextInt(10000));
                    Log.d(">>>>>>>>>>MyApp>>>>>", "Generated Password====>> : " + generatedOTP[0]);
                    telephonyManager = (TelephonyManager)getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
                    smsManager = SmsManager.getDefault();
                    //smsManager.sendTextMessage(Fmobile, null, "Your OTP is: " + generatedOTP[0], null, null);
                     textView.setText(generatedOTP[0]);
                    linearTwo.setVisibility(View.VISIBLE);
                    linearOne.setVisibility(View.GONE);

                }
                else {
                    Toast.makeText(ForgotPassword.this, "Email or Mobile does not exist", Toast.LENGTH_SHORT).show();
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String otp = otpEditText.getText().toString();
               if (otp.equals(generatedOTP[0])){
                   linearThree.setVisibility(View.VISIBLE);
                   linearTwo.setVisibility(View.GONE);
               }
            }
        });

        changePassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = newPassEditText.getText().toString();
                String conNewPass = conPassEditText.getText().toString();
              if (newPass.isEmpty() || conNewPass.isEmpty()){
                  Toast.makeText(ForgotPassword.this, "Fill password field", Toast.LENGTH_SHORT).show();
              }
                else if (!newPass.equals(conNewPass)){
                    Toast.makeText(ForgotPassword.this, "Password is not matched", Toast.LENGTH_SHORT).show();
                }
                else if (newPass.equals(conNewPass)){
                    preferences = getSharedPreferences("ADMIN_DATA", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("PASSWORD", newPass);
                    editor.commit();
                    Toast.makeText(ForgotPassword.this, "Password has been changed successfully", Toast.LENGTH_SHORT).show();
                    newPassEditText.setText("");
                    conPassEditText.setText("");
                    finish();
                }
            }
        });
    }
}
