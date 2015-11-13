package com.jj.macbookpro.mobiledevassignment;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by macbookpro on 12/11/15.
 */
public class MyCursorAdapter extends CursorAdapter {

    public MyCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.rows, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView bookName = (TextView) view.findViewById(R.id.TextView_label_book_name);
        TextView bookCategory = (TextView) view.findViewById(R.id.TextView_label_book_category);
        TextView bookAuthor = (TextView) view.findViewById(R.id.TextView_label_book_author);
        TextView bookRead = (TextView) view.findViewById(R.id.TextView_label_book_read);

        // Extract properties from cursor


        String name = cursor.getString(cursor.getColumnIndexOrThrow(DBManager.KEY_TASK_NAME));
        String author = cursor.getString(cursor.getColumnIndexOrThrow(DBManager.KEY_TASK_AUTHOR));
        String category = cursor.getString(cursor.getColumnIndexOrThrow(DBManager.KEY_TASK_CATEGORY));
        String Read = cursor.getString(cursor.getColumnIndexOrThrow(DBManager.KEY_TASK_READ));

        // Populate fields with extracted properties
        bookName.setText("Book Name: " + name);
        bookCategory.setText("Category: " + category);
        bookAuthor.setText("Author: " + author);
        bookRead.setText("Has Read: "+ Read);

    }
}
