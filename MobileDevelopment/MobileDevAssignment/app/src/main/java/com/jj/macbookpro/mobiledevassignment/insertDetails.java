package com.jj.macbookpro.mobiledevassignment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by macbookpro on 15/11/15.
 * This class is used to inser the details entered in by the user into the database.
 */
public class insertDetails extends Activity{
    // pass our context
    DBManager db = new DBManager(this);

    EditText bookName;
    EditText bookAuthor;
    EditText bookCategory;
    EditText bookComment;
    EditText ISBN;

    CheckBox checkedRead,checkCurrentlyReading;

    String strngCheckedRead = null;
    String stringCheckCurrentlyReading = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_details_layout);

        checkedRead = (CheckBox) findViewById(R.id.editText_bookhave);
        //checkWantToRead = (CheckBox) findViewById(R.id.wantToRead);
        checkCurrentlyReading = (CheckBox) findViewById(R.id.currently_reading);



        Button setButton = (Button) findViewById(R.id.button_submit);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    // Checkbox for if the user has read the book
                    if(checkedRead.isChecked()) {
                        strngCheckedRead = "True";
                    }
                    else{
                        strngCheckedRead = "False";
                    }


                    // checkbox for user to tick if they are currenlty reading the book
                    if(checkCurrentlyReading.isChecked()) {
                        stringCheckCurrentlyReading = "True";
                    }
                    else{
                        stringCheckCurrentlyReading = "False";
                    }

                    db.open();

                    bookName = (EditText) findViewById(R.id.editText_bookname);
                    bookAuthor = (EditText) findViewById(R.id.editText_booknameauthor);
                    bookCategory = (EditText) findViewById(R.id.editText_bookcategory);
                    bookComment = (EditText) findViewById(R.id.editText_comment);
                    ISBN = (EditText) findViewById(R.id.editText_ISBN);


                    // Promp the user with a confiramtion box on whether or not to insert the details.
                    insertMessage(bookName.getText().toString(),
                            bookAuthor.getText().toString(),
                            bookCategory.getText().toString(),
                            bookComment.getText().toString(),
                            ISBN.getText().toString(),
                            stringCheckCurrentlyReading,
                            strngCheckedRead);

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
    }

    // prompt essage displayed to the user, the book details are passed over, if the user selects "yes",
    // the book is added to the database, if clicked "no", user is returned to the adding screen.

    public void insertMessage(final String bookName, final String bookAuthor, final String bookCateogry, final String bookComment,
                              final String isbn,  final String cReading, final String hRead) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        db.insertBook(bookName,
                                bookAuthor,
                                bookCateogry,
                                bookComment,
                                isbn,
                                cReading,
                                hRead);

                        db.close();

                        Intent returnScreen = new Intent(insertDetails.this,listBooks.class);
                        startActivity(returnScreen);
                        Toast.makeText(insertDetails.this, "Book Inserted",Toast.LENGTH_LONG).show();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Insert Book \nBook Title: "+ bookName +"\n" +
                "Book Author: "+bookAuthor)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }



    // An intent for the user to go back to the main screen. Button is displayed in the top left hand corner.
    public void goBackScreen(View view) {
        try {
            Intent lastScreen = new Intent(this, MainActivity.class);
            startActivity(lastScreen);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
