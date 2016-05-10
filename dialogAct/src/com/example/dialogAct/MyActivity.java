package com.example.dialogAct;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;


public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    ListView lv;
    public static final String Name = "name";
    public static final String Img = "img";
    String[] city = {"温州", "杭州", "广州", "长沙"};
    int[] img = {R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};
    TextView tv;
    ImageView iv;
    private ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();//定义哈希表
    private SimpleAdapter simpleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Resources Resources = getResources();
        lv = (ListView) findViewById(R.id.listView);

        TypedArray pics = Resources.obtainTypedArray(R.array.pics);//该数组用于存放图片


        for (int i = 0; i < city.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();

            map.put(Name, city[i]);

            list.add(map);
        }

        simpleAdapter = new SimpleAdapter(this, list, R.layout.newlayout, new String[]{Name}, new int[]{R.id.textView});
        lv.setAdapter(simpleAdapter);


        lv.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                tv = (TextView) findViewById(R.id.textView2);
                iv = (ImageView) findViewById(R.id.imageView);
                //修改文字显示并修改图片
                tv.setText(list.get(position).get(Name).toString() + "市风景区");
                iv.setImageDrawable(pics.getDrawable(position));
            }
        });
        //上下文菜单
        registerForContextMenu(lv);
    }

    //上下文菜单方法重写
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("选项菜单：");
        menu.add(0, Menu.FIRST, 0, "修改");
        menu.add(0, Menu.FIRST + 1, 0, "删除");

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case Menu.FIRST:
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialog));
                final int position = menuInfo.position;

                new AlertDialog.Builder(this).setTitle("修改城市").setView(layout)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })//点击取消的响应函数
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText editText = (EditText) findViewById(R.id.editText);
                                String string = list.get(position).get(Name).toString();
                                editText.setText(string);
                                tv.setText(string);
                            }
                        })//点击确定的响应函数
                        .show();
                break;
            case Menu.FIRST + 1:
                int number = lv.getSelectedItemPosition();
                list.remove(number);
                simpleAdapter.notifyDataSetChanged();
                break;
        }
        return super.onContextItemSelected(item);
    }

    //选项菜单方法重写
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu1:
//                AlertDialog.Builder builder=new AlertDialog.Builder(MyActivity.this);
//                builder.setTitle("增加城市");
//                builder.setMessage("请输入增加的城市名称：");
//                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.show();
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.dialog, (ViewGroup) findViewById(R.id.dialog));
                EditText editText = (EditText) findViewById(R.id.editText);
                new AlertDialog.Builder(this).setTitle("添加城市").setView(layout)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })//点击取消的响应函数
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                HashMap<String, Object> map = new HashMap<String, Object>();//声明HashMap来加Listveiw数据
                                map.put(Name, editText.getText().toString());
                                list.add(map);

                                simpleAdapter.notifyDataSetChanged();

                            }
                        })//点击确定的响应函数
                        .show();

                break;
            case R.id.menu2:
                Toast.makeText(this, "选中重置", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
