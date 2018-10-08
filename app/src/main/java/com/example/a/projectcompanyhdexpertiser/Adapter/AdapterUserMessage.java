package com.example.a.projectcompanyhdexpertiser.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a.projectcompanyhdexpertiser.Class.UserMessage;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/11/2017.
 */

public class AdapterUserMessage extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<UserMessage> arrayList;
    View.OnClickListener click,click2;

    public AdapterUserMessage(Context context, ArrayList<UserMessage> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
//        if (convertView == null) {
//            convertView = inflater.inflate(R.layout.listview_khohang, null);
//            viewHolder = new ViewHolder();
//            viewHolder.txt_title = (TextView) convertView.findViewById(R.id.txt_sanpham_khohang);
//            convertView.setTag(viewHolder);
//
//
//        } else {
//            viewHolder = (ViewHolder) convertView.getTag();
//        }
//
//
//        viewHolder.txt_title.setText(arrayList.get(position).getTen_sp());




        return convertView;
    }

    public class ViewHolder {
        TextView txt_title, txt_soluong,txt_price,txt_price_sale,txt_quantily;
        ImageView imageView;
        Button nhaphang, chinhsua;


    }
}
