package com.example.hp.quizappacad;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Main3Activity extends AppCompatActivity {
    ArrayList<String> notes,notes1;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        listView=(ListView)findViewById(R.id.listview);
        getNotes();
    }

    void getNotes(){
        notes= new ArrayList<String >();
        notes1= new ArrayList<String >();
        try{
            SQLiteDatabase db=this.openOrCreateDatabase("notesDBB",MODE_PRIVATE,null);
            Cursor c=db.rawQuery("select * from scores ",null);
            int noteIndex= c.getColumnIndex("corr");
            int noteIndex1= c.getColumnIndex("incorr");
            int noteIndex2= c.getColumnIndex("scor");
            int noteIndex3= c.getColumnIndex("per");
            int noteIndex4= c.getColumnIndex("tot");
            c.moveToFirst();
            while (c!=null)
            {
                Log.i("Notes",c.getString(noteIndex));
                Log.i("Notes1",c.getString(noteIndex1));
                Log.i("Notes2",c.getString(noteIndex2));
                Log.i("Notes3",c.getString(noteIndex3));
                Log.i("Notes4",c.getString(noteIndex4));
                notes.add("Correct:"+c.getString(noteIndex)+" Incorrect:"+c.getString(noteIndex1)+" Score:"+c.getString(noteIndex2)+" %age:"+c.getString(noteIndex3)+" Total:"+c.getString(noteIndex4));
                c.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,notes);
            listView.setAdapter(arrayAdapter);
        }

    }
}
