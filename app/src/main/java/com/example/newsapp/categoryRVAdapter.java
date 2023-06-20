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
import com.squareup.picasso.Target;

import java.util.ArrayList;

public class categoryRVAdapter extends RecyclerView.Adapter<categoryRVAdapter.ViewHolder> {

    private ArrayList<CategoryModel> categories;
    private Context context;
    OnSelectCategory onSelectCategory;

    public categoryRVAdapter(ArrayList<CategoryModel> categories, Context context, OnSelectCategory onSelectCategory) {
        this.categories = categories;
        this.context = context;
        this.onSelectCategory = onSelectCategory;
    }

    @NonNull
    @Override
    public categoryRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_rv_activity, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull categoryRVAdapter.ViewHolder holder, int position) {

        Picasso.get().load(categories.get(position).getUrlToImage()).into(holder.ivCategoryImage);
        holder.tvCategoryName.setText(categories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView ivCategoryImage;
        private TextView tvCategoryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(onSelectCategory != null)    onSelectCategory.onClick(view, getAdapterPosition());
        }
    }
}
