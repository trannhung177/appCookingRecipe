package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.adapter.adapterCT;
import com.example.myapplication.databasecongthuc.DatabaseCTnauan;
import com.example.myapplication.model.CongThuc;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChiTietCapNhat extends AppCompatActivity {
    private EditText ten,nd;
    private ImageView img;
    private Button capnhat, xoa;

    DatabaseCTnauan dataCT;
    ArrayList<CongThuc> arrlCT;
    adapterCT adtCT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_cap_nhat);



    }
    public void delete(int p){
        arrlCT = new ArrayList<>();

        CongThuc congThuc = arrlCT.get(p);
        ten= findViewById(R.id.tenUD);
        nd= findViewById(R.id.noidungUD);
        img= findViewById(R.id.imgUD);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);

            }
        });
        ten.setText(congThuc.getTenCT());
        nd.setText(congThuc.getNoiDung());
        byte[] ha = congThuc.getAnh();
        //Bitmap bitmap = BitmapFactory.decodeByteArray(ha,0, ha.length);
        BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap=bitmapDrawable.getBitmap();

        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        ha= stream.toByteArray();

        xoa=findViewById(R.id.delete);
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idCT= arrlCT.get(p).getID();
                //xoa trong sqlite
                dataCT.Delete(idCT);

                //Cập nhật lại listview
                Intent intent = new Intent(ChiTietCapNhat.this, CapNhat.class);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10){
            if (data != null){
                Uri image = data.getData();
                //anh.setImageURI(image);
                try {
                    InputStream inputStream =getContentResolver().openInputStream(image);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    img.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
