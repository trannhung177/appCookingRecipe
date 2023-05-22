package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.myapplication.adapter.adapterCT;
import com.example.myapplication.databasecongthuc.DatabaseCTnauan;
import com.example.myapplication.model.CongThuc;

import java.util.ArrayList;

public class TimKiem extends AppCompatActivity {
    EditText edTimkiem;
    ListView lstTK;
    ArrayList<CongThuc> arrlstCT;
    ArrayList<CongThuc> listArrCT;
    adapterCT adapterCT;
    DatabaseCTnauan databaseCTnauan;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        // anh xa view
        edTimkiem= findViewById(R.id.tkiem);
        lstTK= findViewById(R.id.listTK);

        //lay du lieutu database
        initList();

        lstTK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TimKiem.this, ChiTietCTNauAn.class);
                String tenCT = arrlstCT.get(position).getTenCT();
                String noidung = arrlstCT.get(position).getNoiDung();
                byte[] anh = arrlstCT.get(position).getAnh();
                intent.putExtra("ct", tenCT);
                intent.putExtra("noidung", noidung);
                intent.putExtra("anh", anh);
                startActivity(intent);
            }
        });
        edTimkiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });
    }
    //tim kiem
    private void filter(String txt){
        //xoa du lieumang
        arrlstCT.clear();
        ArrayList<CongThuc> ftArr= new ArrayList<>();
        for (CongThuc item: listArrCT ){
            if(item.getTenCT().toLowerCase().contains(txt.toLowerCase())){
                //them item vao filtrelst
                ftArr.add(item);

                //them vao mang
                arrlstCT.add(item);
            }
        }
        adapterCT.filterList(ftArr);

    }

    private void initList() {
        arrlstCT= new ArrayList<>();
        listArrCT =new ArrayList<>();
        databaseCTnauan = new DatabaseCTnauan(this);
        Cursor cursor = databaseCTnauan.getData();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String ct= cursor.getString(1);
            String noidung= cursor.getString(2);
            byte[] anh = cursor.getBlob(3);

            arrlstCT.add(new CongThuc(id,ct,noidung,anh));
            listArrCT.add(new CongThuc(id,ct,noidung,anh));
            adapterCT = new adapterCT(getApplicationContext(),arrlstCT);
            lstTK.setAdapter(adapterCT);
        }
        cursor.moveToFirst();
        cursor.close();
    }
}