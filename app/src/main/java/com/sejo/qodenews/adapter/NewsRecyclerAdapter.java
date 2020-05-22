package com.sejo.qodenews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sejo.qodenews.R;
import com.sejo.qodenews.model.NewsItem;

import java.util.ArrayList;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.NewsViewHolder> {

    //Variables
    private Context mContext;
    private ArrayList<NewsItem> mNewsItems;
    private OnNewsItemClickListener mListener;


    //Constructor
    public NewsRecyclerAdapter(Context mContext, ArrayList<NewsItem> mNewsItems) {
        this.mContext = mContext;
        this.mNewsItems = mNewsItems;
    }

    //Sets the listener for the onClick event
    public void setOnNewsItemClickListener(OnNewsItemClickListener listener){
        mListener = listener;
    }

    //Inflates the view for each item
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.news_list_item,parent,false);
        return new NewsViewHolder(view,mListener);
    }

    //Populates the views with data
    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsItem newsItem = mNewsItems.get(position);

        holder.list_item_title.setText(newsItem.getTitle());
        holder.list_item_author.setText(newsItem.getAuthor());
    }

    //Returns the number of items in the recycler
    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }


    //Interface for when a news item is clicked
    public interface OnNewsItemClickListener{
        void onNewsItemClicked(int position);
    }

    //Viewholder class
    public class NewsViewHolder extends RecyclerView.ViewHolder {

        //Views in item layout
        public TextView list_item_title;
        public TextView list_item_author;

        public NewsViewHolder(@NonNull View itemView, final OnNewsItemClickListener listener) {
            super(itemView);

            list_item_author = itemView.findViewById(R.id.list_item_author);
            list_item_title = itemView.findViewById(R.id.list_item_title);

            //Getting the position of the clicked item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onNewsItemClicked(position);
                        }
                    }
                }
            });
        }
    }



}
