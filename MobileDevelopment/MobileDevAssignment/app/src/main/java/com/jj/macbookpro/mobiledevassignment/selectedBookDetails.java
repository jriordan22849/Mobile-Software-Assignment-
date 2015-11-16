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
        TextView name = (TextView) findViewById(R.id.displayname);
        name.setText(bookName);
        // create a cursor, call a function to the database class witht the book name
        // that was selected and return the book information and then printed to the user screen
        try {
            db.open();
            Cursor information = db.getBookInformation(bookName);
            String author = information.getString(2);
            String category = information.getString(3);
            String comment = information.getString(4);
            String isbn = information.getString(5);
            String creading = information.getString(6);
            String wreading = information.getString(7);
            String haveRead = information.getString(8);

            TextView bookAuthor = (TextView) findViewById(R.id.displayauthor);
            TextView bookCategory = (TextView) findViewById(R.id.displaycategorytextView2);
            TextView bookComment = (TextView) findViewById(R.id.displayComment);
            TextView bookisbn = (TextView) findViewById(R.id.dipslayisbn);
            TextView bookcReading = (TextView) findViewById(R.id.displycReading);
            TextView bookwReading = (TextView) findViewById(R.id.displayWReading);
            TextView havRead = (TextView) findViewById(R.id.displayhRead);

            bookAuthor.setText(author);
            bookCategory.setText(category);
            bookComment.setText(comment);
            bookisbn.setText(isbn);
            bookcReading.setText(creading);
            bookwReading.setText(wreading);
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
            //String bookName = getIntent().getExtras().getString("SELECTED_NAME");
            String bookid = getIntent().getExtras().getString("SELECTED_ISBN");
            Cursor deleteBookInfo = db.delete(bookid);
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
