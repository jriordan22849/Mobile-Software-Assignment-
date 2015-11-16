package com.jj.macbookpro.mobiledevassignment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by macbookpro on 15/11/15.
 */
public class selectedBookDetails extends Activity {
    DBManager db = new DBManager(this);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rows);

        // Get info passed with intent
        String bookName = getIntent().getExtras().getString("SELECTED_NAME");
        TextView name = (TextView) findViewById(R.id.TextView_bookName);
        name.setText(bookName);
        // create a cursor, call a function to the database class witht the book name
        // that was selected and return the book information and then printed to the user screen
        try {
            db.open();
            Cursor information = db.getBookInformation(bookName);
            String author = information.getString(2);
            String category = information.getString(3);
            String haveRead = information.getString(4);

            TextView bookAuthor = (TextView) findViewById(R.id.TextView_bookAuthor);
            TextView bookCategory = (TextView) findViewById(R.id.TextView_bookCategory);
            TextView havRead = (TextView) findViewById(R.id.TextView_bookHaveRead);

            bookAuthor.setText(author);
            bookCategory.setText(category);
            havRead.setText(haveRead);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Delete book from database. **Must add in ISBN for exact book to be deleted.***
    public void DeleteBook(View view) {
        try {
            db.open();
            String bookName = getIntent().getExtras().getString("SELECTED_NAME");
            Cursor deleteBookInfo = db.delete(bookName);
            db.close();

            Intent returnScreen = new Intent(selectedBookDetails.this,listBooks.class);
            startActivity(returnScreen);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void EditBook(View view) {
        try {
            db.open();

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
