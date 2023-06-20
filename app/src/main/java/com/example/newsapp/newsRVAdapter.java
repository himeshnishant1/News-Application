package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class newsRVAdapter extends RecyclerView.Adapter<newsRVAdapter.ViewHolder>{

    private ArrayList<Article> articles;
    private Context context;
    private OnSelectNews onSelectNews;

    public newsRVAdapter(ArrayList<Article> articles, Context context, OnSelectNews onSelectNews) {
        this.articles = articles;
        this.context = context;
        this.onSelectNews = onSelectNews;
    }

    @NonNull
    @Override
    public newsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_rv_activity, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull newsRVAdapter.ViewHolder holder, int position) {
        Article article = articles.get(position);
        Picasso.get().load(article.getUrlToImage()).into(holder.ivNewsImage);
        holder.tvNewsHeading.setText(article.getTitle());
        holder.tvNewsContent.setText(article.getDescription());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView ivNewsImage;
        private TextView tvNewsContent;
        private TextView tvNewsHeading;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivNewsImage = itemView.findViewById(R.id.ivNewsImage);
            tvNewsHeading = itemView.findViewById(R.id.tvNewsHeading);
            tvNewsContent = itemView.findViewById(R.id.tvNewsContent);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onSelectNews != null)    onSelectNews.onClick(getAdapterPosition());
        }
    }
}
