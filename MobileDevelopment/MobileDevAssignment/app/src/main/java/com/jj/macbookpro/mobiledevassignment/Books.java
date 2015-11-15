package com.jj.macbookpro.mobiledevassignment;

/**
 * Created by macbookpro on 14/11/15.
 */
public class Books {

    String bookName;
    String bookAuthor;
    String category;
    String haveRead;

    public Books(String bookName, String bookAuthor, String category, String haveRead) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.category = category;
        this.haveRead = haveRead;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHaveRead() {
        return haveRead;
    }

    public void setHaveRead(String haveRead) {
        this.haveRead = haveRead;
    }
}
