package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.adapterCT;
import com.example.myapplication.databasecongthuc.DatabaseCTnauan;
import com.example.myapplication.model.CongThuc;

import java.security.PrivateKey;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView khaivi,monchinh,douong,trangmieng;
    private ListView listCTGy;
    private Button themCT,timkiem, dangnhap;
    private EditText tk,tendn,mkhau;
    private LinearLayout linearLayout;
    ArrayList<CongThuc> CTArrayList;
    adapterCT adapterCT;
    DatabaseCTnauan databaseCTnauan;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tk=findViewById(R.id.tk);

        linearLayout= findViewById(R.id.lnlayout);
        //linearLayout.setMovementMethod(new ScrollingMovementMethod());

        databaseCTnauan = new DatabaseCTnauan(this);
        AnhXa();
        //bat su kien click item
        listCTGy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ChiTietCTNauAn.class);

                String tenCT = CTArrayList.get(position).getTenCT();
                String noidung = CTArrayList.get(position).getNoiDung();
                byte[] anh = CTArrayList.get(position).getAnh();
                //int SoLuotXem= CTArrayList.get(position).getSolxem();
                //SoLuotXem +=1;

                //databaseCTnauan.slx(SoLuotXem,CTArrayList.get(position).getID());
                intent.putExtra("ct", tenCT);
                intent.putExtra("noidung", noidung);
                intent.putExtra("anh", anh);
                //intent.putExtra("slx",SoLuotXem);

                startActivity(intent);
            }
        });
        tk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5= new Intent(MainActivity.this, TimKiem.class);
                startActivity(intent5);
            }
        });




        //intent
        /*khaivi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(MainActivity.this,khaiviAct.class);
                startActivity(intent1);
            }
        });
        monchinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2= new Intent(MainActivity.this,monchinh.class);
                startActivity(intent2);
            }
        });*/

    }
    private void AnhXa(){
        /*khaivi= findViewById(R.id.khaivi);
        monchinh= findViewById(R.id.monchinh);
        douong = findViewById(R.id.douong);
        trangmieng = findViewById(R.id.trangmieng);*/
        //themCT= findViewById(R.id.themct);
        listCTGy= findViewById(R.id.listCTgoiy);

        CTArrayList = new ArrayList<>();
        Cursor cursor= databaseCTnauan.getData();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String ct= cursor.getString(1);
            String noidung= cursor.getString(2);
            byte[] anh = cursor.getBlob(3);
            //int solx = cursor.getInt(4);

            CTArrayList.add(new CongThuc(id,ct,noidung,anh));
            adapterCT = new adapterCT(getApplicationContext(),CTArrayList);
            listCTGy.setAdapter(adapterCT);
        }
        cursor.moveToFirst();
        cursor.close();
    }

    //menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ct, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.ql:
                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.activity_dang_nhap,null);
                builder.setView(view);
                Dialog dialog = builder.create();
                dialog.show();
                dangnhap= dialog.findViewById(R.id.dn);
                tendn= view.findViewById(R.id.tendangnhap);
                mkhau= view.findViewById(R.id.matkau);
                dangnhap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ten= tendn.getText().toString();
                        String mk= mkhau.getText().toString();
                        if (ten.equals("admin") & mk.equals("admin")){
                            Intent it= new Intent(MainActivity.this, DangTaiCT.class);
                            startActivity(it);
                        }else {
                            Toast.makeText(MainActivity.this, "Đăng nhập không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;

            /*case R.id.add:
                Intent intent = new Intent(MainActivity.this,DangTaiCT.class);
                startActivity(intent);
                break;
            case R.id.update:
                Intent intent1= new Intent(MainActivity.this, CapNhat.class);
                startActivity(intent1);
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }


}