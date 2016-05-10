package com.example.myandroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MyAdapter extends ArrayAdapter {

    ArrayList<HashMap<String, Object>> list;
    Context context;

    public MyAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.list = (ArrayList<HashMap<String, Object>>) objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View inflater = LayoutInflater.from(context).inflate(R.layout.activity_main, null, false);

        //声明需要的控件
        ImageView imv = (ImageView) inflater.findViewById(R.id.imageView1);
        TextView tv1 = (TextView) inflater.findViewById(R.id.textView1);
        TextView tv2 = (TextView) inflater.findViewById(R.id.textView2);

        HashMap<String, Object> hasmap = list.get(position);
        String str1 = hasmap.get(MainActivity.Name1).toString();
        String str2 = hasmap.get(MainActivity.Name2).toString();
        int imgRes = (Integer) hasmap.get(MainActivity.ImageName);
        imv.setImageResource(imgRes);
        tv1.setText(str1);
        tv2.setText(str2);

        if (position % 2 == 0) {
            tv1.setTextColor(Color.RED);
            tv2.setTextColor(Color.RED);
        } else {
            tv1.setTextColor(Color.GREEN);
            tv2.setTextColor(Color.GREEN);
        }
        return inflater;


//		return super.getView(position, convertView, parent);
    }


}
