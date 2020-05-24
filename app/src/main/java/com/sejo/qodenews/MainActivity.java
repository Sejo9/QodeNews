package com.sejo.qodenews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Variables
    private RecyclerView newsRecycler;
    private NewsRecyclerAdapter adapter;
    private ArrayList<NewsItem> newsItems;
    private RequestQueue requestQueue;
    private ProgressBar pb;
    private LinearLayout retryLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        if(checkConnection()){
            retrieveNews();
        }else{
            pb.setVisibility(View.GONE);
            retryLayout.setVisibility(View.VISIBLE);
        }

    }


    //Instanciating variables for this activity
    private void init(){
        newsItems = new ArrayList<>();

        pb = findViewById(R.id.pb);
        retryLayout = findViewById(R.id.retry_layout);
        Button retry = findViewById(R.id.retry);
        retry.setOnClickListener(this);

        newsRecycler = findViewById(R.id.newsRecycler);
        newsRecycler.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = Volley.newRequestQueue(this);
    }

    private boolean checkConnection(){
        ConnectivityManager cm = (ConnectivityManager) MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null;
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

                    pb.setVisibility(View.GONE);
                    newsRecycler.setVisibility(View.VISIBLE);

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
                error.printStackTrace();
            }
        });

        //Adding string request to request queue
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.retry){
            if (checkConnection()){
                pb.setVisibility(View.VISIBLE);
                retryLayout.setVisibility(View.GONE);
                retrieveNews();
            }
        }
    }
}
