package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.adapter.adapterCT;
import com.example.myapplication.databasecongthuc.DatabaseCTnauan;
import com.example.myapplication.model.CongThuc;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class CapNhat extends AppCompatActivity {
    private ListView lstCapNhatItem;
    private EditText ten,nd;
    private ImageView img;
    private Button capnhat, xoa;
    adapterCT adtCT;
    DatabaseCTnauan dtbCT;
    ArrayList<CongThuc> lstCTnauan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap_nhat);
        //anh xa view
        //lay du lieu tu databsae vao list view

        lstCapNhatItem= findViewById(R.id.listdscapnhat);
        getData();
        lstCapNhatItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                delete(position);

            }
        });


    }
    private void getData(){
        lstCTnauan = new ArrayList<>();
        dtbCT = new DatabaseCTnauan(this);
        Cursor cursor =  dtbCT.getData();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String nd= cursor.getString(2);
            byte[] anh = cursor.getBlob(3);

            lstCTnauan.add(new CongThuc(id,ten,nd,anh));
            adtCT = new adapterCT(getApplicationContext(), lstCTnauan);
            lstCapNhatItem.setAdapter(adtCT);
        }
        cursor.moveToFirst();
        cursor.close();

    }
    //thuc hien xoa
    private void delete(int p){

        AlertDialog.Builder builder =new AlertDialog.Builder(CapNhat.this);
        View view = LayoutInflater.from(CapNhat.this).inflate(R.layout.activity_chi_tiet_cap_nhat,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        ten= view.findViewById(R.id.tenUD);
        nd= view.findViewById(R.id.noidungUD);
        img= view.findViewById(R.id.imgUD);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);

            }
        });
        CongThuc congThuc = lstCTnauan.get(p);
        ten.setText(congThuc.getTenCT());
        nd.setText(congThuc.getNoiDung());
        byte[] ha = congThuc.getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(ha,0, ha.length);
        img.setImageBitmap(bitmap);


        xoa=dialog.findViewById(R.id.delete);
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idCT= lstCTnauan.get(p).getID();
                //xoa trong sqlite
                dtbCT.Delete(idCT);

                //Cập nhật lại listview
                Intent intent = new Intent(CapNhat.this,CapNhat.class);
                finish();
                startActivity(intent);
                Toast.makeText(CapNhat.this,"Xóa truyện thành công",Toast.LENGTH_SHORT).show();
            }
        });
        capnhat = dialog.findViewById(R.id.update);
        capnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) img.getDrawable();
                Bitmap bitmap=bitmapDrawable.getBitmap();

                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] hinhanh= stream.toByteArray();

                String tenCtNA = ten.getText().toString();
                String ndCT=  nd.getText().toString();

                congThuc.setTenCT(tenCtNA);
                congThuc.setNoiDung(ndCT);
                congThuc.setAnh(hinhanh);

                dtbCT.update(congThuc);

                //Cập nhật lại listview
                Intent intent = new Intent(CapNhat.this,CapNhat.class);
                finish();
                startActivity(intent);
                Toast.makeText(CapNhat.this,"Lưu truyện thành công",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //xu ly anh
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