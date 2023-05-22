package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.databasecongthuc.DatabaseCTnauan;
import com.example.myapplication.model.CongThuc;
//import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.normal.TedPermission;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class DangTaiCT extends AppCompatActivity {
    EditText tenCT, CachLam;
    ImageView anh;
    Button dangbai;
    DatabaseCTnauan databaseCTnauan;
    CongThuc congThuc;

    ArrayList<String> lstItemImg;

    Uri image;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_tai_ct);

        anh=findViewById(R.id.imgdb);
        tenCT= findViewById(R.id.tenctdb);
        CachLam= findViewById(R.id.cachlamdb);
        dangbai= findViewById(R.id.db);
        databaseCTnauan = new DatabaseCTnauan(this);

        dangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TenCt = tenCT.getText().toString();
                String cachLam= CachLam.getText().toString();
                String img = anh.toString();

                CongThuc congThuc = CreateCT();

                if (TenCt.equals("")|| cachLam.equals("")){
                    Toast.makeText(DangTaiCT.this, "Nhap day du thong tin", Toast.LENGTH_SHORT).show();
                    Log.e("ERR", "Nhap day du tt");
                }else {
                    databaseCTnauan.AddCT(congThuc);
                    Intent intent= new Intent(DangTaiCT.this, MainActivity.class);
                    finish();
                    startActivity(intent);

                }

            }
        });

        //lay anh tu thu vuien
        anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);

            }
        });


    }

    private CongThuc CreateCT(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) anh.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] hinhanh= stream.toByteArray();

        String TenCt = tenCT.getText().toString();
        String cachLam= CachLam.getText().toString();


        //Intent intent = getIntent();
        //int id = intent.getIntExtra("Id",0);
        CongThuc congThuc = new CongThuc(TenCt,cachLam,hinhanh);
        return congThuc;
    }


        //xu ly hinh anh

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10){
            if (data != null){
                image = data.getData();
                //anh.setImageURI(image);
                try {
                    InputStream inputStream =getContentResolver().openInputStream(image);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    anh.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }






}