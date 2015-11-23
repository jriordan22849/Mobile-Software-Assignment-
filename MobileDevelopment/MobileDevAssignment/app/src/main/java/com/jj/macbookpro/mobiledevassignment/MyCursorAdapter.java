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
        return LayoutInflater.from(context).inflate(R.layout.authorrow, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template

        TextView bookAuthor = (TextView) view.findViewById(R.id.TextView_author);
        // Extract properties from cursor
        String author = cursor.getString(cursor.getColumnIndexOrThrow(DBManager.KEY_TASK_AUTHOR));
        // Populate fields with extracted properties
        bookAuthor.setText(author);

    }
}
