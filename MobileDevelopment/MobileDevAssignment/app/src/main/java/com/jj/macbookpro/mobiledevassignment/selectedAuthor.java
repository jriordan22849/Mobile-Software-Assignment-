package com.jj.macbookpro.mobiledevassignment;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by macbookpro on 15/11/15.
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
//            TextView bookAuthor = (TextView) findViewById(R.id.TextView_bookAuthor);
//            bookAuthor.setText(authorName);selectAuthorDB
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



    }


}
