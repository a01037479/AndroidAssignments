package com.example.harrypotter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ListView lv;
    // URL to get book data in JSON
    private static String SERVICE_URL = "https://www.googleapis.com/books/v1/volumes?q=harry+potter";
    private ArrayList<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(getFlag()?R.style.DarkMode: R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList = new ArrayList<Book>();
        lv = findViewById(R.id.bookList);
        new GetContacts().execute();

        Button chg_theme_btn = findViewById(R.id.changeThemeBtn);
        chg_theme_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v){
                saveFlag(!getFlag());

                //refresh this activity to get updated theme
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(MainActivity.this, BookDetailsActivity.class);
                String theme = (getFlag()?"dark":"light");
                intent.putExtra("theme", theme);

                Bundle bundle = new Bundle();
                bundle.putSerializable("book", bookList.get(position));
                intent.putExtra("bundle", bundle);
                
                MainActivity.this.startActivity(intent);
            }
        });

    }

    private void saveFlag(boolean flag){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("dark",flag);
        editor.commit();
    }

    private boolean getFlag() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        return pref.getBoolean("dark", false);
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            String jsonStr = null;

            // Making a request to url and getting response
            jsonStr = sh.makeServiceCall(SERVICE_URL);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    // Getting JSON Array node
                    JSONObject obj = new JSONObject(jsonStr);
                    JSONArray bookJsonArray = obj.getJSONArray("items");


                    // looping through All Contacts
                    for (int i = 0; i < bookJsonArray.length(); i++) {
                        JSONObject c = bookJsonArray.getJSONObject(i);
                        try{
                            String id = c.getString("id");
                            JSONObject volumeinfo = c.getJSONObject("volumeInfo");

                            String title = volumeinfo.getString("title");
                            JSONArray authorsJson = volumeinfo.getJSONArray("authors");
                            String[] authors = new String[authorsJson.length()];

                            for(int j = 0; j < authorsJson.length(); j++){
                                authors[j] = authorsJson.getString(j);
                            }

                            String publisher = volumeinfo.getString("publisher");
                            String publishedDate = volumeinfo.getString("publishedDate");

                            String description = volumeinfo.has("description") ? volumeinfo.getString("description") : "";


                            JSONArray industry = volumeinfo.getJSONArray("industryIdentifiers");
                            String ISBN = "";
                            for(int x = 0; x < industry.length(); x++){

                                JSONObject info = industry.getJSONObject(x);
                                String type = info.getString("type");
                                if(type.equals("ISBN_10")){
                                    ISBN = info.getString("identifier");
                                }
                            }

                            JSONObject image = volumeinfo.getJSONObject("imageLinks");
                            String url = image.getString("thumbnail");

                            //build book
                            Book book = new Book(id, title, authors, publisher, publishedDate, description, ISBN, url);

                            // adding book to books list
                            bookList.add(book);

                        }catch(Exception e){
                            continue;
                        }

                    }
                } catch (Exception e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();


            BookAdapter adapter = new BookAdapter(MainActivity.this, bookList);

            // Attach the adapter to a ListView
            lv.setAdapter(adapter);
        }

    }

}
