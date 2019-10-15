package com.example.harrypotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Intent intent = getIntent();

        long itemId =  intent.getLongExtra("itemId", 0);
        String title = intent.getStringExtra("title");
        String isbn = intent.getStringExtra("isbn");
        String[] authors = intent.getStringArrayExtra("authors");
        String publisher = intent.getStringExtra("publisher");
        String publishDate = intent.getStringExtra("publish_date");
        String description = intent.getStringExtra("description");


        TextView txtTitle = findViewById(R.id.txtTitle);
        txtTitle.setText(title);

        TextView txtAuthors = findViewById(R.id.txtAuthors);
        txtAuthors.setText(normalizeStringArray(authors));

        TextView txtISBN = findViewById(R.id.txtISBN);
        txtISBN.setText(isbn);

        TextView txtPublisher = findViewById(R.id.txtPublisher);
        txtPublisher.setText(publisher);

        TextView txtPublishDate = findViewById(R.id.txtPublishDate);
        txtPublishDate.setText(publishDate);

        TextView txtDescription = findViewById(R.id.txtDescription);
        txtDescription.setText(description);

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
