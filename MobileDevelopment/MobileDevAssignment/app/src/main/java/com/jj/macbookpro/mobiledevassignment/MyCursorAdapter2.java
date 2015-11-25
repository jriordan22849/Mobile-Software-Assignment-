package com.jj.macbookpro.mobiledevassignment;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by macbookpro on 12/11/15.
 */
public class MyCursorAdapter2 extends CursorAdapter {

    public MyCursorAdapter2(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.booknames, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView bookName = (TextView) view.findViewById(R.id.TextView_name);
        TextView bookCategory = (TextView) view.findViewById(R.id.TextView_category_name);

        ImageView haveRead = (ImageView) view.findViewById(R.id.imageRead);

        // Extract properties from cursor
        String books = cursor.getString(cursor.getColumnIndexOrThrow(DBManager.KEY_TASK_NAME));
        String category = cursor.getString(cursor.getColumnIndexOrThrow(DBManager.KEY_TASK_CATEGORY));


        String userCurrentlyReading = cursor.getString(cursor.getColumnIndexOrThrow(DBManager.KEY_TASK_CREADING));
        String userCompletedBook = cursor.getString(cursor.getColumnIndexOrThrow(DBManager.KEY_TASK_READ));




        // to check to see if the user is currently reading the book,
        // if so, display image on the list view beside the book name.
        if((userCurrentlyReading.equals("True") &&
                (userCompletedBook.equals("True") ) ) || (userCurrentlyReading.equals("True")) ) {
            haveRead.setImageResource(R.drawable.imageread);
        }

        if(userCompletedBook.equals("True") && (userCurrentlyReading.equals("False") ) ) {
            haveRead.setImageResource(R.drawable.tick_50_x_50);
        }
        // Populate fields with extracted properties
        bookName.setText(books);
        bookCategory.setText(category);

    }
}
