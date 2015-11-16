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
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

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

    public void alertMessage() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        Toast.makeText(selectedBookDetails.this, "Yes Clicked",Toast.LENGTH_LONG).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        Toast.makeText(selectedBookDetails.this, "No Clicked",Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    // Delete book from database. A dialog box will appear, if the user selects yes, the book will be deleted, if the
    // user selects no, the book wont be deleted and a break will occur.
    public void DeleteBook(View view) {
        try {
            //alertMessage();
            db.open();
            String bookName = getIntent().getExtras().getString("SELECTED_NAME");

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch(which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            Toast.makeText(selectedBookDetails.this, "Book Deleted",Toast.LENGTH_LONG).show();

                            String bookid = getIntent().getExtras().getString("SELECTED_ISBN");
                            Cursor deleteBookInfo = db.delete(bookid);
                            db.close();

                            Intent returnScreen = new Intent(selectedBookDetails.this,listBooks.class);
                            startActivity(returnScreen);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            Toast.makeText(selectedBookDetails.this, "No Clicked",Toast.LENGTH_LONG).show();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to delete the book called: "+ bookName)
                    .setPositiveButton("Yes",dialogClickListener)
                    .setNegativeButton("No",dialogClickListener).show();




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
