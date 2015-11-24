package com.jj.macbookpro.mobiledevassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.sql.SQLException;

/**
 * Created by macbookpro on 12/11/15.
 */
public class DBManager {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "MyDataBase";
    static final String TABLE_NAME = "Book";

    static final String KEY_ID = "_id";
    static final String KEY_TASK_NAME = "Book_name";
    static final String KEY_TASK_AUTHOR = "Author";
    static final String KEY_TASK_CATEGORY = "Category";
    static final String KEY_TASK_COMMENT = "Comment";
    static final String KEY_TASK_ISBN = "ISBN";
    static final String KEY_TASK_CREADING = "Currently_Reading";
    static final String KEY_TASK_WREADING = "Want_To_Read";
    static final String KEY_TASK_READ = "Have_Read";

    final Context context;
    MyDatabaseHelper DBHelper;
    SQLiteDatabase db;

    private static final String CREATE_BOOK_TABLE = "create table Book (_id integer primary key autoincrement, " +
            "Book_name  TEXT, " +
            "Author  TEXT, " +
            "Category  TEXT," +
            "Comment    TEXT," +
            "ISBN   TEXT," +
            "Currently_Reading  TEXT," +
            "Have_Read  TEXT)";

    public DBManager(Context ctx) {
        this.context = ctx;
        DBHelper = new MyDatabaseHelper(context);
    }

    private static class MyDatabaseHelper extends SQLiteOpenHelper {


        public MyDatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_BOOK_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

            onCreate(db);
        }
    }

    public DBManager open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        DBHelper.close();
    }

    public long insertBook(String name, String author, String category, String comment,
                           String isbn, String cReading, String read) {
        ContentValues initialValues = new ContentValues();

        if(!name.equals("") || !author.equals("") || !isbn.equals("")|| !category.equals("")) {
            initialValues.put(KEY_TASK_NAME, name);
            initialValues.put(KEY_TASK_AUTHOR, author);
            initialValues.put(KEY_TASK_CATEGORY, category);
            initialValues.put(KEY_TASK_COMMENT, comment);
            initialValues.put(KEY_TASK_ISBN, isbn);
            initialValues.put(KEY_TASK_CREADING, cReading);
            //initialValues.put(KEY_TASK_WREADING, wRead);
            initialValues.put(KEY_TASK_READ, read);
        }
        return db.insert(TABLE_NAME, null, initialValues);
    }


    public Cursor getAll() {
        Cursor mCursor = db.rawQuery(
                "SELECT DISTINCT * FROM '"+ TABLE_NAME + "';", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    // Return a cursor contain distinct author names to be displayed.
    public Cursor getDistinctAuthor()
    {
        String[] column = new String[] {KEY_TASK_AUTHOR};
        //String whereClause = "Author = 'Y'";
        //Cursor mCursor = db.query(true,TABLE_NAME,column,null,null,null,null,null,null);
        Cursor mCursor = db.query(true, TABLE_NAME, null, null, null, KEY_TASK_AUTHOR, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    public Cursor selectAuthorDB(String name)
    {
        Cursor bookInfo = db.rawQuery("SELECT * FROM '"+ TABLE_NAME +"' where Author = '" + name + "';", null);

        if (bookInfo != null) {
            bookInfo.moveToFirst();
        }

        return bookInfo;

    }


    public Cursor getBookInformation(String bookName) {
        Cursor bookInfo = db.rawQuery("SELECT * FROM '"+ TABLE_NAME + "' where Book_Name = '"+ bookName + "';",null);

        if (bookInfo != null) {
            bookInfo.moveToFirst();
        }

        return bookInfo;
    }

    public void delete(String bookid) {
        Cursor deleteBook = db.rawQuery("DELETE FROM '"+ TABLE_NAME + "' WHERE ISBN ='"+ bookid + "';",null);

        if (deleteBook != null) {
            deleteBook.moveToFirst();
        }

        //return  deleteBook;
    }

    public void updateStauts(String cReading,String Completed, String isbn) {

        try {
            String updateSQL = "UPDATE Book SET Currently_Reading ='" + cReading + "' WHERE ISBN ='" + isbn + "'";
            String updateSQL2 = "UPDATE Book SET Have_Read ='" + Completed + "' WHERE ISBN ='" + isbn + "'";
            //String updateSQL3 = "UPDATE Book SET Comment ='" + comment + "' WHERE ISBN ='" + isbn + "'";

            db.execSQL(updateSQL);
            db.execSQL(updateSQL2);
            //db.execSQL(updateSQL3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
