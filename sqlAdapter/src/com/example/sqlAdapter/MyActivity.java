package com.example.sqlAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.*;
import android.widget.*;

import java.util.ArrayList;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    public static final int CAMERA  = 0x01;
    private String newPicString = "";

    private ListView listView;
    private ArrayList<String> names,phones;
    private ArrayList<String>pics;

    private SQLiteDatabase db;
    private Cursor mCursor;

    private static final int ITEM0 = Menu.FIRST;
    private static final int ITEM1 = Menu.FIRST + 1;
    private static final int ITEM2 = Menu.FIRST + 2;
    private static final int ITEM3 = Menu.FIRST + 3;

    private int index = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lab05);

        listView = (ListView)findViewById(R.id.listView1);
        db=(new SqlAdapter(this)).getWritableDatabase();

        mCursor=db.rawQuery("SELECT _id, Name,Phone,PicNum FROM constants",null);

        ListAdapter adapter=new SimpleCursorAdapter(this,
                R.layout.lv_layout_lab05, mCursor,
                new String[] {"Name", "Phone","PicNum"},
                new int[] {R.id.textView1, R.id.textView2,R.id.imageView1});
        listView.setAdapter(adapter);
        registerForContextMenu(listView);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA && resultCode == Activity.RESULT_OK && null != data){

            Uri selectedImage = data.getData();
            String[] filePathColumns={MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath= c.getString(columnIndex);
            newPicString = picturePath;
            c.close();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        //menu.setHeaderIcon(R.drawable.p0);

        menu.add(0,ITEM0,0,"编辑");
        menu.add(0,ITEM1,0,"删除");
        menu.add(0,ITEM2,0,"添加");
        menu.add(0,ITEM3,0,"取消");

        index = ((AdapterView.AdapterContextMenuInfo)menuInfo).position;

        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @SuppressLint("NewApi") @Override
    public boolean onContextItemSelected(MenuItem mi){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout dialogLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.sqlmain, null,false);
        builder.setView(dialogLayout);
        final EditText et_name,et_phone;
        final Button btn;

        switch (mi.getItemId()) {

            case ITEM0:
                final AdapterView.AdapterContextMenuInfo info=
                        (AdapterView.AdapterContextMenuInfo)mi.getMenuInfo();
                et_name = (EditText)dialogLayout.findViewById(R.id.editText1);
                et_phone = (EditText)dialogLayout.findViewById(R.id.editText2);
                btn = (Button)dialogLayout.findViewById(R.id.button1);
                btn.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Intent picture = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(picture, 1);
                    }
                });

                mCursor = db.rawQuery("select _id,Name,Phone,PicNum From constants where _id=" + info.id, null);
                mCursor.moveToFirst();
                et_name.setText(mCursor.getString(mCursor.getColumnIndex("Name")));
                et_phone.setText(mCursor.getString(mCursor.getColumnIndex("Phone")));

                builder.setTitle("��Ϣ�޸�");
                builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        ContentValues cv=new ContentValues(3);
                        cv.put("name", et_name.getText().toString());
                        cv.put("phone", et_phone.getText().toString());
                        if(newPicString != null && newPicString != ""){
                            cv.put("picnum", newPicString);
                            newPicString = "";
                        }

                        try{
                            String[] wherevalue = {Long.toString(info.id)};
                            db.update("constants", cv, "_id=?",wherevalue);

                            mCursor=db.rawQuery("SELECT _id, Name,Phone,PicNum FROM constants",null);
                            ListAdapter adapter=new SimpleCursorAdapter(MyActivity.this,
                                    R.layout.lv_layout_lab05, mCursor,
                                    new String[] {"Name", "Phone","PicNum"},
                                    new int[] {R.id.textView1, R.id.textView2,R.id.imageView1});
                            listView.setAdapter(adapter);
                        }
                        catch(Exception e){
                            android.util.Log.w("database_errors", e.toString());

                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();

                break;
            case ITEM1:
                final AdapterView.AdapterContextMenuInfo info1=
                        (AdapterView.AdapterContextMenuInfo)mi.getMenuInfo();
                db.execSQL("delete from constants where _id=" + info1.id);

                mCursor=db.rawQuery("SELECT _id, Name,Phone,PicNum FROM constants",null);
                ListAdapter adapter=new SimpleCursorAdapter(MyActivity.this,
                        R.layout.lv_layout_lab05, mCursor,
                        new String[] {"Name", "Phone","PicNum"},
                        new int[] {R.id.textView1, R.id.textView2,R.id.imageView1});
                listView.setAdapter(adapter);

                break;
            case ITEM2:
                //�����ϵ��
                et_name = (EditText)dialogLayout.findViewById(R.id.editText1);
                et_phone = (EditText)dialogLayout.findViewById(R.id.editText2);
                btn = (Button)dialogLayout.findViewById(R.id.button1);
                btn.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
                        // TODO Auto-generated method stub
                        Intent picture = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(picture, 1);
                    }
                });

                builder.setTitle("�������ϵ��");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        ContentValues cv=new ContentValues(3);
                        cv.put("name", et_name.getText().toString());
                        cv.put("phone", et_phone.getText().toString());
                        //cv.put("picnum", R.drawable.p0);
                        if(newPicString != null && newPicString != ""){
                            cv.put("picnum", newPicString);
                            newPicString = "";
                        }
                        else{
                            cv.put("picnum", R.drawable.p0);
                        }

                        try{
                            db.insert("constants", "name", cv);

                            mCursor=db.rawQuery("SELECT _id, Name,Phone,PicNum FROM constants",null);
                            ListAdapter adapter=new SimpleCursorAdapter(MyActivity.this,
                                    R.layout.lv_layout_lab05, mCursor,
                                    new String[] {"Name", "Phone","PicNum"},
                                    new int[] {R.id.textView1, R.id.textView2,R.id.imageView1});
                            listView.setAdapter(adapter);
                        }
                        catch(Exception e){
                            android.util.Log.w("database_errors", e.toString());

                        }
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;
            case ITEM3:
                //���Ͷ���
                final AdapterView.AdapterContextMenuInfo info2=
                        (AdapterView.AdapterContextMenuInfo)mi.getMenuInfo();

                mCursor = db.rawQuery("select _id,Name,Phone,PicNum From constants where _id=" + info2.id, null);
                mCursor.moveToFirst();

                String toUserPhone = mCursor.getString(mCursor.getColumnIndex("Phone"));
                SmsManager smsManager = SmsManager.getDefault();
                String serviceCenterAddress=null;
                smsManager.sendTextMessage(toUserPhone, serviceCenterAddress, "��������", null, null);

                break;
            default:
                break;
        }

        return true;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mCursor.close();
        db.close();
    }
}
