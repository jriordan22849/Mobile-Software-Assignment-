package com.jj.macbookpro.mobiledevassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbookpro on 15/11/15.
 * Student ID: C13432152
 * Student Name: Jonathan Riordan
 */

// This class displays the book names in a list to the user.
public class listBooks extends Activity {
    DBManager db = new DBManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Spinner spinner = (Spinner) findViewById(R.id.Category_spinner);

        final ListView listView = (ListView) findViewById(R.id.listView_books);
        try {
            db.open();

            // set the list to the result from the database query
            Cursor result = db.getAll();
            MyCursorAdapter2 cursorAdapter = new MyCursorAdapter2(listBooks.this, result);
            listView.setAdapter(cursorAdapter);


            // Receive the distinct category from the database and display it to the spinner.
            Cursor myDistinctCategory = db.getDistinctCategory();

            ArrayList<String> adapter = new ArrayList<String>();
            if(myDistinctCategory.moveToFirst()) {
                do {
                    String cat = myDistinctCategory.getString(3);
                    adapter.add(cat);
                }while(myDistinctCategory.moveToNext());

                // set the array to the size of the adapter.
                String[] arraySpinner = new String[adapter.size()];
                arraySpinner = adapter.toArray(arraySpinner);

                ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arraySpinner);
                spinner.setAdapter(myAdapter);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // gets the seleced category, and returns all the books who category is selected.
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String selected = parentView.getItemAtPosition(position).toString();

                try {
                    db.open();
                    // get the results from the category selected and display it to the list.
                    Cursor getData = db.sortByCategory(selected);
                    MyCursorAdapter2 cursorAdapter = new MyCursorAdapter2(listBooks.this, getData);
                    listView.setAdapter(cursorAdapter);
                    db.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {}

        });



        // select book name from the list and create an intent
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long arg) {
                try {
                    Cursor myCursor = (Cursor) av.getItemAtPosition(position);
                    String selection = myCursor.getString(1);
                    String passISBN = myCursor.getString(5);
                    Context context = getApplicationContext();
                    CharSequence text = selection;
                    int dur = Toast.LENGTH_LONG;

//                    Toast toast = Toast.makeText(context,text,dur);
//                    toast.show();

                    Intent selectedBookName = new Intent(listBooks.this, selectedBookDetails.class);
                    selectedBookName.putExtra("SELECTED_NAME", selection);
                    selectedBookName.putExtra("SELECTED_ISBN", passISBN);
                    startActivity(selectedBookName);
                } catch (Exception e) {
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

    // if the list by book name is clicked, refresh the page.
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
}
