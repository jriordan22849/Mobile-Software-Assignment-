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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by macbookpro on 15/11/15.
 */
public class selectedBookDetails extends Activity {
    DBManager db = new DBManager(this);

    CheckBox checkedRead, haveReadCheckBox;

    String strngCheckedRead = null;
    String stringCompletedBook = null;

    Button onlineButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rows);
        onlineButton = (Button) findViewById(R.id.button_book_online);

        // Get info passed with intent
        final String bookName = getIntent().getExtras().getString("SELECTED_NAME");
        TextView name = (TextView) findViewById(R.id.displayname);
        name.setText(bookName);
        // create a cursor, call a function to the database class witht the book name
        // that was selected and return the book information and then printed to the user screen
        try {
            /*
            1. book name
            2. author
            3. category
            4. comment
            5. isbn
            6. Currently Reading
            7. Has the user completed the book
            */

            db.open();
            Cursor information = db.getBookInformation(bookName);
            String author = information.getString(2);
            String category = information.getString(3);
            String comment = information.getString(4);
            String isbn = information.getString(5);
            String creading = information.getString(6);
            String haveRead = information.getString(7);

            TextView bookAuthor = (TextView) findViewById(R.id.displayauthor);
            TextView bookCategory = (TextView) findViewById(R.id.displaycategorytextView2);
            TextView bookComment = (TextView) findViewById(R.id.displayComment);
            TextView bookisbn = (TextView) findViewById(R.id.dipslayisbn);
            //TextView havRead = (TextView) findViewById(R.id.displayhRead);

            // Check boxes for update
            checkedRead = (CheckBox) findViewById(R.id.checkbox_reading);
            haveReadCheckBox = (CheckBox) findViewById(R.id.checkbox_have_read);

            // if the the user is currently reading the book, set it the check box to true.
            if(creading.equals("True")) {
                checkedRead.setChecked(true);
            }

            if(haveRead.equals("True")) {
                haveReadCheckBox.setChecked(true);
            }



            bookAuthor.setText(author);
            bookCategory.setText(category);
            bookComment.setText(comment);
            bookisbn.setText(isbn);
           // havRead.setText(haveRead);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ImageButton editButton = (ImageButton) findViewById(R.id.button3);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.open();

                    String bookName = getIntent().getExtras().getString("SELECTED_NAME");
                    Cursor information = db.getBookInformation(bookName);
                    String isbn = information.getString(5);

                    // 1st Checkbox for if the user is currently reading the book.
                    if(checkedRead.isChecked()) {
                        strngCheckedRead = "True";
                    }
                    else {
                        strngCheckedRead = "False";
                    }

                    // 2nd checkbox to check if the user has completed the book.
                    if(haveReadCheckBox.isChecked()) {
                        stringCompletedBook = "True";
                    }
                    else {
                        stringCompletedBook = "False";
                    }

                    alertMessage(isbn, bookName);
                    db.updateStauts(strngCheckedRead, stringCompletedBook, isbn);

                    db.close();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        });


        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webViewIntent = new Intent(selectedBookDetails.this,Web.class);
                webViewIntent.putExtra("NAME_TO_WEB", bookName);
                startActivity(webViewIntent);

            }
        });
        }

    public void alertMessage(final String isbn, String bookName) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        db.updateStauts(strngCheckedRead,stringCompletedBook,isbn);
                        Intent returnScreen = new Intent(selectedBookDetails.this,listBooks.class);
                        startActivity(returnScreen);
                        Toast.makeText(selectedBookDetails.this, "Updated book",Toast.LENGTH_LONG).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Update Book: "+ bookName)
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
                            db.delete(bookid);
                            db.close();

                            Intent returnScreen = new Intent(selectedBookDetails.this,listBooks.class);
                            startActivity(returnScreen);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
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
}
