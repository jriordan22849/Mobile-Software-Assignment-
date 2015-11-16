package com.jj.macbookpro.mobiledevassignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by macbookpro on 12/11/15.
 */
public class DBManager {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "BookTable";
    static final String TABLE_NAME = "Book";

    static final String KEY_ID = "_id";
    static final String KEY_TASK_NAME = "Book_name";
    static final String KEY_TASK_AUTHOR = "Author";
    static final String KEY_TASK_CATEGORY = "Category";
    static final String KEY_TASK_READ = "Have_Read";

    final Context context;
    MyDatabaseHelper DBHelper;
    SQLiteDatabase db;

    private static final String CREATE_BOOK_TABLE = "create table Book (_id integer primary key autoincrement, " +
            "Book_name  TEXT, " +
            "Author  TEXT, " +
            "Category  TEXT, " +
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

    public long insertBook(String name, String author, String category, String read) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TASK_NAME, name);
        initialValues.put(KEY_TASK_AUTHOR, author);
        initialValues.put(KEY_TASK_CATEGORY, category);
        initialValues.put(KEY_TASK_READ, read);
        return db.insert(TABLE_NAME, null, initialValues);
    }


    public Cursor getAll()
    {
        Cursor mCursor = db.rawQuery(
                "SELECT * FROM Book where Have_Read = 'Y';", null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    // Return a cursor contain distinct author names to be displayed.
    public Cursor getDistinctAuthor()
    {
        //String[] column = new String[] {"Author"};
        //String whereClause = "Author = 'Y'";
        //Cursor mCursor = db.query(true,TABLE_NAME,column,null,null,null,null,null,null);
        Cursor mCursor = db.rawQuery("SELECT DISTINCT "+KEY_TASK_AUTHOR+" as _id from Book where Have_Read = 'Y';",null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;

    }

    public Cursor selectAuthorDB(String name)
    {
        Cursor bookInfo = db.rawQuery("SELECT * FROM BOOK where Author = '"+ name + "';",null);

        if (bookInfo != null) {
            bookInfo.moveToFirst();
        }

        return bookInfo;

    }


    public Cursor getBookInformation(String bookName) {
        Cursor bookInfo = db.rawQuery("SELECT * FROM BOOK where Book_Name = '"+ bookName + "';",null);

        if (bookInfo != null) {
            bookInfo.moveToFirst();
        }

        return bookInfo;
    }

    public Cursor delete(String name) {
        Cursor deleteBook = db.rawQuery("DELETE FROM BOOK WHERE Book_Name ='"+ name + "';",null);

        if (deleteBook != null) {
            deleteBook.moveToFirst();
        }

        return  deleteBook;
    }
}
