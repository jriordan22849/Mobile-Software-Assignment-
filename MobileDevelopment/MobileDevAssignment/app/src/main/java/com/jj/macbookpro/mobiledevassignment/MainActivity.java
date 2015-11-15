/*
Student Name: Jonathan Riordan
Student ID: C13432152

 */
package com.jj.macbookpro.mobiledevassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;


// This is the first screen. It displays the authors in a list to the user
public class MainActivity extends Activity {
    // pass our context
    DBManager db = new DBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.listView_books);

        //
        try {
            db.open();
            Cursor result = db.getAll();
            MyCursorAdapter cursorAdapter = new MyCursorAdapter(MainActivity.this, result);
            listView.setAdapter(cursorAdapter);
            db.close();
        }
        // catch an exception if it occurs
        catch (Exception e) {
            e.printStackTrace();
        }



    }

    // New intent to display a list containing the book names.
    public void listBookNames(View view) {
        try {
            Intent myNewActivity = new Intent(this, listBooks.class);
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

    //
}
