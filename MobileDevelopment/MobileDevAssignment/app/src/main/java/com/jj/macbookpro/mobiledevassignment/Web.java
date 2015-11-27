package com.jj.macbookpro.mobiledevassignment;

/**
 * Created by macbookpro on 23/11/2015.
 * Student ID: C13432152
 * Student Name: Jonathan Riordan
 * This class is used to receive the book name, once the book name has been received, all the spaces are removed in the string
 * and replaced with plus signs, Then we ammend the url with the book name and to be dispalyed in the webview.
 */
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_layout);

        // Receive the book name passed over from selectedbookdetails class.
        String bookName = getIntent().getExtras().getString("NAME_TO_WEB");

        // Replace the book names that have spaces to plus signs, so it can be used to search for the book.
        try {
            bookName = bookName.replaceAll(" ","+");
        } catch (Exception e) {
            e.printStackTrace();
        }

        WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.setWebViewClient(new WebViewClient());

        // ammeneded the URL, i placed the book name into the url so the site will look for the book.
        myWebView.loadUrl("http://onlinebooks.library.upenn.edu/webbin/book/search?author=&amode=words&title="+ bookName +"&tmode=words");
    }

}

