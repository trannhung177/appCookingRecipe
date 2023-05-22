package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class ChiTietCTNauAn extends AppCompatActivity {
    TextView tenct, noidung,solx;
    ImageView anhCT;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ctnau_an);

        // anh xa view

        tenct= findViewById(R.id.tenctCT);
        noidung= findViewById(R.id.ndctCT);
        anhCT= findViewById(R.id.anh);
        //solx= findViewById(R.id.slx);

        Intent intent = getIntent();
        String tenCT  = intent.getStringExtra("ct");
        String NoiDung = intent.getStringExtra("noidung");
        byte[] anh= intent.getByteArrayExtra("anh");
        Bitmap bitmap = BitmapFactory.decodeByteArray(anh,0, anh.length);
        //int slx= intent.getIntExtra("slx",0);
        tenct.setText(tenCT);
        noidung.setText(NoiDung);
        anhCT.setImageBitmap(bitmap);
        //solx.setText(slx);

        // cho phep cuan khi doc
        noidung.setMovementMethod(new ScrollingMovementMethod());

    }
}