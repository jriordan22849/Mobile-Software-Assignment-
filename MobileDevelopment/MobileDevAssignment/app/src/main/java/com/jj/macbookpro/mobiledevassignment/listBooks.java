package com.jj.macbookpro.mobiledevassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by macbookpro on 15/11/15.
 */
public class listBooks extends Activity {
    DBManager db = new DBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final ListView listView = (ListView) findViewById(R.id.listView_books);
        try {
            db.open();
            Cursor result = db.getAll();
            MyCursorAdapter2 cursorAdapter = new MyCursorAdapter2(listBooks.this, result);
            listView.setAdapter(cursorAdapter);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


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

    public void listBookAuthor(View view) {
        try {
            Intent myNewActivity = new Intent(this, MainActivity.class);
            startActivity(myNewActivity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // New intent to to add book details to the database.
    public void addBook(View view) {
        try {
            Intent addIntent = new Intent(this, insertDetails.class);
            startActivity(addIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
