package com.example.harrypotter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends ArrayAdapter<Book> {
    Context _context;
    public BookAdapter(Context context, ArrayList<Book> books) {
        super(context, 0, books);
        _context = context;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;
        // Get the data item for this position
        Book book = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvBookTitle = convertView.findViewById(R.id.bookTitle);
        // Populate the data into the template view using the data object
        tvBookTitle.setText(book.getTitle());

        ImageView imgOnePhoto = convertView.findViewById(R.id.thumbImage);
        //DownloadImageTask dit = new DownloadImageTask(_context, imgOnePhoto);
        //dit.execute(toon.getPicture());
        if (book.getThumbNailUrl() != null) {
            new ImageDownloaderTask(imgOnePhoto).execute(book.getThumbNailUrl());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}

