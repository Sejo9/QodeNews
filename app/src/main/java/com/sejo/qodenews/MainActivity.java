package com.sejo.qodenews;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sejo.qodenews.adapter.NewsRecyclerAdapter;
import com.sejo.qodenews.model.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Variables
    private RecyclerView newsRecycler;
    private NewsRecyclerAdapter adapter;
    private ArrayList<NewsItem> newsItems;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        retrieveNews();
    }


    //Instanciating variables for this activity
    private void init(){
        newsItems = new ArrayList<>();

        newsRecycler = findViewById(R.id.newsRecycler);
        newsRecycler.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);
    }


    //Parsing JSON file to retrieve data to populate recycler view
    private void retrieveNews() {
        String url = "https://learnappmaking.com/ex/news/articles/Apple?secret=CHWGk3OTwgObtQxGqdLvVhwji6FsYm95oe87o3ju";

        //String request for processing data from url
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject responseObject = new JSONObject(response);
                    JSONArray articlesArray = responseObject.getJSONArray("articles");

                    //For loop to add news items to the news items arraylist
                    for(int i = 0; i < articlesArray.length(); i++){

                        JSONObject article = articlesArray.getJSONObject(i);

                        String image  = article.getString("image");
                        String title = article.getString("title");
                        String author = article.getString("author");
                        String description = article.getString("text");

                        newsItems.add(new NewsItem(title,author,description,image));
                    }

                    //Instantiating adapter and setting it to the recyclerview
                    adapter = new NewsRecyclerAdapter(MainActivity.this, newsItems);
                    newsRecycler.setAdapter(adapter);

                    //When a news item is clicked
                    adapter.setOnNewsItemClickListener(new NewsRecyclerAdapter.OnNewsItemClickListener() {
                        @Override
                        public void onNewsItemClicked(int position) {
                            NewsItem newsItem = newsItems.get(position);

                            //Passing news info to new activity though intent
                            Intent intent = new Intent(MainActivity.this, ViewNewsActivity.class);
                            intent.putExtra("AUTHOR", newsItem.getAuthor());
                            intent.putExtra("TITLE", newsItem.getTitle());
                            intent.putExtra("IMAGE", newsItem.getImageURL());
                            intent.putExtra("DESCRIPTION", newsItem.getDescription());

                            startActivity(intent);
                        }
                    });

                } catch(JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //Adding string request to request queue
        requestQueue.add(stringRequest);
    }

}
