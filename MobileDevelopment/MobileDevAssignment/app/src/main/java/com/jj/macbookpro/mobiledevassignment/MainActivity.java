package com.jj.macbookpro.mobiledevassignment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;



public class MainActivity extends Activity {
    // pass our context
    DBManager db = new DBManager(this);
    EditText bookName;
    EditText bookAuthor;
    EditText bookCategory;
    EditText haveRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button setButton = (Button) findViewById(R.id.button_submit);
        Button getButton = (Button) findViewById(R.id.button_receive);

        final ListView listView = (ListView) findViewById(R.id.listView_books);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.open();
                    bookName = (EditText) findViewById(R.id.editText_bookname);
                    bookAuthor = (EditText) findViewById(R.id.editText_booknameauthor);
                    bookCategory = (EditText) findViewById(R.id.editText_bookcategory);
                    haveRead = (EditText) findViewById(R.id.editText_bookhave);

                    db.insertBook(bookName.getText().toString(), bookAuthor.getText().toString()
                            , bookCategory.getText().toString(), haveRead.getText().toString());

                    db.close();
                } catch (Exception e) {
                    e.printStackTrace();

                    Context context = getApplicationContext();
                    CharSequence text = "Error opening database";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });

        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.open();
                    Cursor result = db.getAll();
                    MyCursorAdapter cursorAdapter = new MyCursorAdapter(MainActivity.this, result);
                    listView.setAdapter(cursorAdapter);
                    db.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long arg) {
                try {
                    Cursor mycursor = (Cursor) av.getItemAtPosition(position);
                    String selection = mycursor.getString(5);

                    Context context = getApplicationContext();
                    CharSequence text = selection;
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
