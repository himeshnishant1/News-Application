package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnSelectCategory, OnSelectNews{

    private RecyclerView rvCategory;
    private RecyclerView rvNews;
    private ArrayList<CategoryModel> categories;
    private ArrayList<Article> articles;
    private String URL = "https://newsapi.org/v2/everything?q=all&language=en&apiKey=f6b7a74dad3840b5a27ccb9877370e72";
    newsRVAdapter newsRVAdapter;
    categoryRVAdapter categoryRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCategory = findViewById(R.id.rvCategory);
        rvNews = findViewById(R.id.rvNews);

        categories = new ArrayList<>();
        categories.add(new CategoryModel("https://cdn.pixabay.com/photo/2020/02/02/17/24/travel-4813658_1280.jpg", "India"));
        categories.add(new CategoryModel("https://cdn.pixabay.com/photo/2021/11/14/18/36/telework-6795505_1280.jpg", "Business"));
        categories.add(new CategoryModel("https://cdn.pixabay.com/photo/2017/12/25/05/59/technology-3038005_1280.jpg", "Technology"));
        categories.add(new CategoryModel("https://cdn.pixabay.com/photo/2011/12/14/12/21/orion-nebula-11107_1280.jpg", "Science"));
        categories.add(new CategoryModel("https://cdn.pixabay.com/photo/2015/03/01/22/27/women-655353_1280.jpg", "Sports"));

        categoryRVAdapter = new categoryRVAdapter(categories, this, this::onClick);
        rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategory.setAdapter(categoryRVAdapter);

        articles = new ArrayList<>();
        newsRVAdapter = new newsRVAdapter(articles, MainActivity.this, this::onClick);
        rvNews.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvNews.setAdapter(newsRVAdapter);

        FetchNews("business");
    }

    private void FetchNews(String category) {
        NewsApiClient newsApiClient = new NewsApiClient("f6b7a74dad3840b5a27ccb9877370e72");
        if(category.equals("india")){
            newsApiClient.getTopHeadlines(new TopHeadlinesRequest.Builder().country("in").language("en").build(), new NewsApiClient.ArticlesResponseCallback() {
                @Override
                public void onSuccess(ArticleResponse response) {
                    setNewsDataToAdapter(response);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Toast.makeText(MainActivity.this, "News Cannot be loaded", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            newsApiClient.getTopHeadlines(new TopHeadlinesRequest.Builder().category(category).language("en").build(), new NewsApiClient.ArticlesResponseCallback() {
                @Override
                public void onSuccess(ArticleResponse response) {
                    setNewsDataToAdapter(response);
                }

                @Override
                public void onFailure(Throwable throwable) {
                    Toast.makeText(MainActivity.this, "News Cannot be loaded", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    void setNewsDataToAdapter(ArticleResponse response){
        //Toast.makeText(MainActivity.this , "Set Data", Toast.LENGTH_SHORT).show();
        try {
            articles.clear();
            List<com.kwabenaberko.newsapilib.models.Article> a = response.getArticles();
            for (int i = 0; i < a.size(); i++) {
                if (a.get(i).getUrlToImage() == null) continue;
                Log.d("response", a.get(i).getTitle());
                articles.add(new Article(a.get(i).getSource().getId(), a.get(i).getSource().getName(), a.get(i).getAuthor(), a.get(i).getTitle(), a.get(i).getDescription(), a.get(i).getUrl(), a.get(i).getUrlToImage(), a.get(i).getPublishedAt(), a.get(i).getContent()));
            }
            newsRVAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.e("Response error", e.toString());
        }
    }

    //OnClickCategory
    @Override
    public void onClick(View view, int position) {
        //Toast.makeText(MainActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
        if(position == 0)   FetchNews("india");
        if(position == 1)   FetchNews("business");
        if(position == 2)   FetchNews("technology");
        if(position == 3)   FetchNews("science");
        if(position == 4)   FetchNews("sports");
    }

    //OnClickNews
    @Override
    public void onClick(int position) {
        Article article = articles.get(position);
        Intent i = new Intent(MainActivity.this, NewsActivity.class);
        i.putExtra("url", article.getUrl());
        i.putExtra("urlToImage", article.getUrlToImage());
        i.putExtra("title", article.getTitle());
        i.putExtra("content", article.getContent());
        i.putExtra("description", article.getDescription());
        i.putExtra("publishedAt", article.getPublishedAt());
        startActivity(i);
    }
}