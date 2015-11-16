package com.jj.macbookpro.mobiledevassignment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    EditText currentlyRading;
    EditText wanttoread;
    EditText haveRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.insert_details_layout);

        Button setButton = (Button) findViewById(R.id.button_submit);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    db.open();

                    bookName = (EditText) findViewById(R.id.editText_bookname);
                    bookAuthor = (EditText) findViewById(R.id.editText_booknameauthor);
                    bookCategory = (EditText) findViewById(R.id.editText_bookcategory);
                    bookComment = (EditText) findViewById(R.id.editText_comment);
                    ISBN = (EditText) findViewById(R.id.editText_ISBN);
                    currentlyRading = (EditText) findViewById(R.id.currently_reading);
                    wanttoread = (EditText) findViewById(R.id.wantToRead);
                    haveRead = (EditText) findViewById(R.id.editText_bookhave);


                    db.insertBook(bookName.getText().toString(), bookAuthor.getText().toString(),
                            bookCategory.getText().toString(),bookComment.getText().toString(),
                            ISBN.getText().toString(), currentlyRading.getText().toString(),
                            wanttoread.getText().toString(), haveRead.getText().toString());
                    db.close();

                    // return to the home screen.
                    Intent returnScreen = new Intent(insertDetails.this, MainActivity.class);
                    startActivity(returnScreen);
                } catch(Exception e) {

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
