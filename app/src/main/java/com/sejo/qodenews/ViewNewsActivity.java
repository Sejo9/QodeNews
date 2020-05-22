package com.sejo.qodenews;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ViewNewsActivity extends AppCompatActivity {

    //Variables for this activity
    private Intent intent;
    private ImageView newsImg;
    private TextView newsTitle;
    private TextView newsAuthor;
    private TextView newsDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);

        init();

        setViews();

    }

    //Instantiating the variables for this activity
    private void init(){
        intent = getIntent();

        newsImg = findViewById(R.id.news_img);
        newsTitle = findViewById(R.id.news_title);
        newsAuthor = findViewById(R.id.news_author);
        newsDescription = findViewById(R.id.news_description);
    }

    //Sets the views in the activity with thee info received from intent
    private void setViews(){
        //Retrieve info from intent
        String title = intent.getStringExtra("TITLE");
        String author = intent.getStringExtra("AUTHOR");
        String description = intent.getStringExtra("DESCRIPTION");
        String imageUrl = intent.getStringExtra("IMAGE");

        //Set the views with retrieved info
        Picasso.get().load(imageUrl).fit().centerInside().into(newsImg);
        newsTitle.setText(title);
        newsAuthor.setText(author);
        newsDescription.setText(description);
    }
}
