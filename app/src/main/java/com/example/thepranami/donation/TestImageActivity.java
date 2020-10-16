package com.example.thepranami.donation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class TestImageActivity extends AppCompatActivity {
    Button okBtn, getBtn;
    ImageView imageTest, imageTest1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_image);
        imageTest=findViewById(R.id.imageTest);
        imageTest1=findViewById(R.id.imageTest1);
        okBtn=findViewById(R.id.testButton);
        getBtn=findViewById(R.id.getButton);

        imageTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imgIntent = new Intent();
                imgIntent.setType("image/*");
                imgIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(imgIntent, 111);
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences = getSharedPreferences("ADMIN_DATA", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                imageTest.buildDrawingCache();
                Bitmap image_bitmap = imageTest.getDrawingCache();
                editor.putString("IMAGE", encodeToBase64(image_bitmap));
                editor.commit();
            }
        });
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("ADMIN_DATA", MODE_PRIVATE);
                Bitmap bit_img = decodeBase64(sp.getString("IMAGE", ""));
                imageTest1.setImageBitmap(bit_img);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK && data != null && data.getData() != null){
           // imgUri = data.getData();
            Picasso.with(this).load(data.getData()).into(imageTest);
        }
    }

    public static String encodeToBase64(Bitmap bitmap) {
        Bitmap image = bitmap;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.d("Image Log:", imageEncoded);
        return imageEncoded;
    }
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
