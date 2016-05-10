package com.example.linyuan_03;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.linyuan_03.*;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
    public String[] sence;
    TypedArray imgList;
    public static final String KEY_NAME1 = "sence";
    public static final String KEY_NAME2 = "imgList";
    public int contextPosition;
    ListView listview;
    TextView textview, textview_eidt;
    ImageView img;
    EditText editText;
    SimpleAdapter simpleadapter;
    AlertDialog.Builder dialog;
    static ArrayList<HashMap<String, Object>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = this.getResources();
        sence = res.getStringArray(R.array.sence);
        imgList = res.obtainTypedArray(R.array.image);
        listview = (ListView) findViewById(R.id.listView1);
        textview = (TextView) findViewById(R.id.textView1);
        img = (ImageView) findViewById(R.id.imageView1);
        list = new ArrayList<HashMap<String, Object>>();


        list.clear();
        for (int i = 0; i < sence.length; i++) {
            HashMap<String, Object> listItem = new HashMap<String, Object>();
            listItem.put(KEY_NAME1, sence[i] + "风景区");
            listItem.put(KEY_NAME2, imgList.getDrawable(i));
            list.add(listItem);
        }
        simpleadapter = new SimpleAdapter(this, list, R.layout.list_item, new String[]{KEY_NAME1}, new int[]{R.id.textView1});
        listview.setAdapter(simpleadapter);
        registerForContextMenu(listview);//���������Ĳ˵�

        listview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                String str = ((TextView) arg1.findViewById(R.id.textView1)).getText().toString();
                textview.setText(str);
                for (int i = 0; i < list.size(); i++) {
                    if (str.equals(list.get(i).get(KEY_NAME1).toString()))
                        img.setImageDrawable((Drawable) list.get(i).get(KEY_NAME2));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.item1:
                dialog = new AlertDialog.Builder(this).setTitle("添加城市");
                LinearLayout dialogLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dialog, null, false);
                dialog.setView(dialogLayout);
                editText = (EditText) dialogLayout.findViewById(R.id.editText1);

                dialog.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                String str = editText.getText().toString() + "市风景区";
                                HashMap<String, Object> listItem = new HashMap<String, Object>();
                                listItem.put(KEY_NAME1, str);
                                listItem.put(KEY_NAME2, imgList.getDrawable(2));
                                list.add(listItem);
                                simpleadapter.notifyDataSetChanged();
                            }
                        });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                });

                dialog.show();
                break;
            case R.id.item2:
                list.clear();
                for (int i = 0; i < sence.length; i++) {
                    HashMap<String, Object> listItem = new HashMap<String, Object>();
                    listItem.put(KEY_NAME1, sence[i] + "风景区");
                    listItem.put(KEY_NAME2, imgList.getDrawable(i));
                    list.add(listItem);
                }
                textview.setText(list.get(0).get(KEY_NAME1).toString());
                img.setImageDrawable((Drawable) list.get(0).get(KEY_NAME2));
                simpleadapter.notifyDataSetChanged();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.context_menu, menu);
        contextPosition = ((AdapterView.AdapterContextMenuInfo) menuInfo).position;

        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.contextitem1:
                dialog = new AlertDialog.Builder(this).setTitle("修改城市");
                final LinearLayout dialogLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.dialog, null, false);
                dialog.setView(dialogLayout);
                String str = list.get(contextPosition).get(KEY_NAME1).toString();
                editText = (EditText) dialogLayout.findViewById(R.id.editText1);
                editText.setText(str);
                dialog.setPositiveButton("确认",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                //list.get(contextPosition).get(KEY_NAME1) = textview.getText().toString();
                                String str = editText.getText().toString();
                                HashMap<String, Object> listItem = list.get(contextPosition);
                                listItem.put(KEY_NAME1, str);
                                list.set(contextPosition, listItem);
                                simpleadapter.notifyDataSetChanged();
                            }
                        });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                    }
                });

                dialog.show();
                break;
            case R.id.contextitem2:
                list.remove(contextPosition);
                simpleadapter.notifyDataSetChanged();
                break;
        }

        return super.onContextItemSelected(item);
    }

}
