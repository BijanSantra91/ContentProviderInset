package com.example.bijan.contentproviderinsertinex1;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText1, editText2;
    Button insert, read;
    TextView see;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText1 = (EditText) findViewById(R.id.name);
        editText2 = (EditText) findViewById(R.id.sub);
        insert = (Button) findViewById(R.id.insert);
        read = (Button) findViewById(R.id.read);
        see = (TextView) findViewById(R.id.see);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //prepare resolver
                ContentResolver contentResolver = getContentResolver();

                //read value form edittext
                String name = editText1.getText().toString();
                String sub = editText2.getText().toString();

                //prepare content value
                ContentValues contentValues = new ContentValues();
                contentValues.put(UriProvider.NAME, name);
                contentValues.put(UriProvider.SUM, sub);

                //now insert
                contentResolver.insert(UriProvider.STIDENT_URI, contentValues);
                Toast.makeText(MainActivity.this, "Values Inserted", Toast.LENGTH_SHORT).show();
                editText1.setText("");
                editText2.setText("");
                editText1.requestFocus();
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //prepare resolver
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(UriProvider.STIDENT_URI, null, null, null, null);
                StringBuilder stringBuilder = new StringBuilder();
                if (cursor != null){
                    while (cursor.moveToNext()){
                        int no = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String sub = cursor.getString(2);
                        stringBuilder.append("No : "+no+" Name : "+name+" Subject : "+sub);
                    }
                    see.setText(stringBuilder.toString());
                }
            }
        });
    }
}
