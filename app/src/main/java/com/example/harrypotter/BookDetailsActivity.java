package com.example.harrypotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        //update theme based on main theme
	    String theme = intent.getStringExtra("theme");
        this.setTheme(theme.equals("dark")?R.style.DarkMode_NoActionBar:R.style.AppTheme_NoActionBar);
        
        setContentView(R.layout.activity_book_details);


        //get book data
        Bundle bundle = intent.getBundleExtra("bundle");
        Book book = (Book)bundle.get("book");


        updateViewText(book);

    }


    private void updateViewText(Book book){
        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(book.getTitle());

        TextView txtAuthors = findViewById(R.id.txtAuthors);
        txtAuthors.setText(normalizeStringArray(book.getAuthors()));

        TextView txtISBN = findViewById(R.id.txtISBN);
        txtISBN.setText(book.getISBN());

        TextView txtPublisher = findViewById(R.id.txtPublisher);
        txtPublisher.setText(book.getPublisher());

        TextView txtPublishDate = findViewById(R.id.txtPublishDate);
        txtPublishDate.setText(book.getPublishedDate());

        TextView txtDescription = findViewById(R.id.txtDescription);
        txtDescription.setText(book.getDescription());

        if(book.getDescription().isEmpty()){
            TextView lblDescription = findViewById(R.id.lblDescription);
            lblDescription.setText("No description.");
        }

        txtDescription.setMovementMethod(new ScrollingMovementMethod());

        if(book.getThumbNailUrl() != null){
            ImageView img = findViewById(R.id.imgBookCover);
            new ImageDownloaderTask(img).execute(book.getThumbNailUrl());
        }
    }


    private String normalizeStringArray(String[] arr){
        StringBuilder result = new StringBuilder();
        for(int i = 0; i < arr.length; i++){
            if(i == arr.length - 1){
                result.append(arr[i]);
            } else{
                result.append(arr[i] + ", ");
            }
        }
        return result.toString();
    }

}
