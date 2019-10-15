package com.example.harrypotter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String isbn = intent.getStringExtra("isbn");
        String[] authors = intent.getStringArrayExtra("authors");
        String publisher = intent.getStringExtra("publisher");
        String publishDate = intent.getStringExtra("publish_date");
        String description = intent.getStringExtra("description");
        String url = intent.getStringExtra("url");

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

        if(description.isEmpty()){
            TextView lblDescription = findViewById(R.id.lblDescription);
            lblDescription.setText("No description.");
        }

        txtDescription.setMovementMethod(new ScrollingMovementMethod());

        if(url != null){
            ImageView img = findViewById(R.id.imgBookCover);
            new ImageDownloaderTask(img).execute(url);
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
