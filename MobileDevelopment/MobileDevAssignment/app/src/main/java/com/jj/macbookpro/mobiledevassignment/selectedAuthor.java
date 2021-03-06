package com.jj.macbookpro.mobiledevassignment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by macbookpro on 15/11/15.
 * Student ID: C13432152
 * Student Name: Jonathan Riordan
 */
public class selectedAuthor extends Activity {

    DBManager db = new DBManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_layout);
        final ListView authorBooksList = (ListView) findViewById(R.id.listView_author_books);

        // Get info passed with intent
        try {
            db.open();
            String authorName = getIntent().getExtras().getString("SELECTED_AUTHOR");
            Cursor result = db.selectAuthorDB(authorName);

            // Display the Author Name at the top of the screen
            TextView authorNameAtTop = (TextView) findViewById(R.id.AuthorNameLAbel);
            authorNameAtTop.setText("Author: " +authorName);

            MyAuthorAdapter cursorAdapter = new MyAuthorAdapter(selectedAuthor.this, result);
            authorBooksList.setAdapter(cursorAdapter);

            db.close();

            // MyCursorAdapter2 cursorAdapter = new MyCursorAdapter2(selectedAuthor.this, result);
           // authorBooksList.setAdapter(cursorAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // create a cursor, call a function to the database class with the book name
        // that was selected and return the book information and then printed to the user screen

        // select book name from the list and create an intent
        authorBooksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View view, int position, long arg) {
                try {
                    Cursor myCursor = (Cursor) av.getItemAtPosition(position);
                    String selection = myCursor.getString(1);
                    String passISBN = myCursor.getString(5);

                    Intent selectedBookName = new Intent(selectedAuthor.this, selectedBookDetails.class);
                    selectedBookName.putExtra("SELECTED_NAME", selection);
                    selectedBookName.putExtra("SELECTED_ISBN", passISBN);
                    startActivity(selectedBookName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        authorBooksList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { //list is my listView

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int pos, long id) {
                //Retrive the informatiom from the cursor, delete the item from the database and toast the book name deleted.
                Cursor myCursor = (Cursor) arg0.getItemAtPosition(pos);
                String bookName = myCursor.getString(1);
                String passISBN = myCursor.getString(5);
                // alertMessage(passISBN);
                try {
                    db.open();

                    db.delete(passISBN);
                    Toast.makeText(selectedAuthor.this, "Deleted book: " + bookName, Toast.LENGTH_LONG).show();

                    Intent refreshPage = new Intent(selectedAuthor.this, MainActivity.class);
                    startActivity(refreshPage);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                db.close();

                return true;
            }
        });
    }
}
