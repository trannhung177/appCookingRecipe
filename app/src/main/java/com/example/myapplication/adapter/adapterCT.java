package com.example.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.R;
import com.example.myapplication.model.CongThuc;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapterCT extends BaseAdapter {
    private Context context;
    private ArrayList<CongThuc> listCT;

    public adapterCT(Context context, ArrayList<CongThuc> listCT){
        this.context= context;
        this.listCT = listCT;
    }
    @Override
    public int getCount() {
        return listCT.size();
    }

    @Override
    public Object getItem(int position) {
        return listCT.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void filterList(ArrayList<CongThuc> ftArr) {
        listCT= ftArr;
        notifyDataSetChanged();
    }

    public class ViewHolder{
        TextView txtTenCT;
        ImageView imgCT;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder= null;
        viewHolder= new ViewHolder();

        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.them_congthuc, null);
        //CongThuc congThuc = (CongThuc) getItem(position);
        viewHolder.txtTenCT = convertView.findViewById(R.id.txtthemCT);
        viewHolder.imgCT = convertView.findViewById(R.id.imgthemCT);
        convertView.setTag(viewHolder);

        CongThuc congThuc = listCT.get(position);

        viewHolder.txtTenCT.setText(congThuc.getTenCT());
        //chuyển byte -->bitmap
        byte[] ha = congThuc.getAnh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(ha,0, ha.length);
        viewHolder.imgCT.setImageBitmap(bitmap);

        //Picasso.get().load(congThuc.getAnh()).placeholder(R.drawable.ic_load).error(R.drawable.ic_launcher_foreground).into(viewHolder.imgCT);
        //Picasso.get().load(congThuc.getAnh()).placeholder(R.drawable.ic_loadd).error(R.drawable.ic_err_img).into(viewHolder.imgCT);
        /*TextView txtTen= convertView.findViewById(R.id.txtthemCT);
        ImageView img = convertView.findViewById(R.id.imgthemCT);

        txtTen.setText(congThuc.getTenCT());
        img.setImageURI(Uri.parse(congThuc.getAnh()));*/
        /*if (congThuc.Anh != null && !congThuc.getAnh().isEmpty()){
            Picasso.get().load(congThuc.getAnh()).into(viewHolder.imgCT);
        }else{
            viewHolder.imgCT.setImageResource(R.drawable.ic_image);
        }*/

        return convertView;
    }
}
