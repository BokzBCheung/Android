package com.example.myandroid;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

    public static final String Name1 = "names1";
    public static final String Name2 = "names2";
    public static final String ImageName = "images";//定义名称

    String[] names1 = {"No1", "No2", "no3", "No4"};
    String[] names2 = {"Id1", "Id2", "Id3", "Id4"};
    int[] images = {R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4};//参数赋值
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newlayout);

        lv = (ListView) findViewById(R.id.listView1);
        final ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();//定义哈希表
        for (int i = 0; i < names1.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(ImageName, images[i]);
            map.put(Name1, names1[i]);
            map.put(Name2, names2[i]);//传值

            list.add(map);
        }

//        SimpleAdapter simpleAdapter=new SimpleAdapter(this,list, R.layout.activity_main, new String[]{Name1,Name2,ImageName}, new int[]{R.id.textView1,R.id.textView2,R.id.imageView1});
        MyAdapter myAdapter = new MyAdapter(this, 0, list);//自定义适配器
        lv.setAdapter(myAdapter);

        //listview的点击监听事件
        lv.setOnItemClickListener(new ListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
                Toast.makeText(MainActivity.this, list.get(arg2).get(Name2).toString(), Toast.LENGTH_LONG).show();
                //显示当前点中的条目
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
