package com.example.thepranami.donation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    EditText amountEditText, nameEditText, otherEditText, addressEditText, mobileEditText;
    Button submitButton, viewButton;
    TextView infoTextView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    LinearLayout homeLinearLayout, subLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeLinearLayout=(LinearLayout)findViewById(R.id.homeLayout_id);
        subLinearLayout=(LinearLayout)findViewById(R.id.sub_linear_id);
        amountEditText=(EditText)findViewById(R.id.amountEditText_id);
        nameEditText=(EditText)findViewById(R.id.nameEditText_id);
        otherEditText=(EditText)findViewById(R.id.otherEditText_id);
        addressEditText=(EditText)findViewById(R.id.addressEditText_id);
        mobileEditText=(EditText)findViewById(R.id.mobileEditText_id);
        submitButton=(Button)findViewById(R.id.submitButton_id);
        infoTextView=(TextView)findViewById(R.id.infoTextView_id);
        /*viewButton=(Button)findViewById(R.id.get_button);
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.homeLayout_id, new DonorListFragment());
                fragmentTransaction.commit();
                Toast.makeText(HomeActivity.this, "hi.............", Toast.LENGTH_SHORT).show();
            }
        });  */

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String AMOUNT= amountEditText.getText().toString();
                String NAME= nameEditText.getText().toString();
                String OTHER= otherEditText.getText().toString();
                String ADDRESS= addressEditText.getText().toString();
                String MOBILE= mobileEditText.getText().toString();

                if (AMOUNT.isEmpty() || NAME.isEmpty() || ADDRESS.isEmpty() || MOBILE.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "Please fill all required field", Toast.LENGTH_SHORT).show();
                    infoTextView.setText("Please fill all required field");
                }
                else {
                    new InsertData().execute(AMOUNT, NAME, OTHER, ADDRESS, MOBILE);
                }
            }
        });
    }

    ////////for inser data////
    public class InsertData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            infoTextView.setText("");
            progressDialog = new ProgressDialog(HomeActivity.this);
            progressDialog.setTitle("Data is inserting...");
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String RES = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(AppUrlLink.BASE_URL + "add_data.php");

                ArrayList<NameValuePair> arrayList = new ArrayList<>();
                arrayList.add(new BasicNameValuePair("amount", strings[0]));
                arrayList.add(new BasicNameValuePair("name", strings[1]));
                arrayList.add(new BasicNameValuePair("other", strings[2]));
                arrayList.add(new BasicNameValuePair("address", strings[3]));
                arrayList.add(new BasicNameValuePair("mobile", strings[4]));

                httpPost.setEntity(new UrlEncodedFormEntity(arrayList));
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                RES = EntityUtils.toString(httpEntity);

            } catch (Exception e) {
                RES = e.toString();
            }
            return RES;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            nameEditText.setText("");
            amountEditText.setText("");
            otherEditText.setText("");
            addressEditText.setText("");
            mobileEditText.setText("");
            infoTextView.setText("Data inserted successfully");
            Toast.makeText(HomeActivity.this, "Data inserted"+s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_page_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        switch (menuId) {
            case R.id.logout_menu_id:
                AlertDialog.Builder a_builder = new AlertDialog.Builder(HomeActivity.this);
                a_builder.setMessage("Are you sure for logout ?").setCancelable(false)
                        .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent loginIntent = new Intent(HomeActivity.this, AdminLoginActivity.class);
                                startActivity(loginIntent);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = a_builder.create();
                alertDialog.show();
                break;
            case R.id.view_menu_id:
                //Toast.makeText(HomeActivity.this, "hi.............", Toast.LENGTH_SHORT).show();
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.sub_linear_id, new DonorListFragment());
                fragmentTransaction.addToBackStack("TAG");
                fragmentTransaction.commit();
                homeLinearLayout.setVisibility(View.GONE);
                break;
            case R.id.update_menu_id:
                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.sub_linear_id, new DonorListFragment());
                fragmentTransaction.commit();
                subLinearLayout.setVisibility(View.GONE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        homeLinearLayout.setVisibility(View.VISIBLE);
    }
}
