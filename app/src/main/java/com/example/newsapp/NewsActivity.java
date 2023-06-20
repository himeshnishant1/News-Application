package com.example.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;


public class NewsActivity extends AppCompatActivity {
    private ImageView ivImage;
    private TextView tvTitle, tvPublishedAt, tvDesc, tvContent;
    private Button btnMore;
    private String urlToImage;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        ivImage = findViewById(R.id.ivImage);
        tvTitle = findViewById(R.id.tvTitle);
        tvPublishedAt = findViewById(R.id.tvPublishedAt);
        tvDesc = findViewById(R.id.tvDesc);
        tvContent = findViewById(R.id.tvContent);
        btnMore = findViewById(R.id.btnMore);

        Intent i = getIntent();

        urlToImage = i.getStringExtra("urlToImage");
        if(urlToImage != null)  Picasso.get().load(urlToImage).into(ivImage);

        url = i.getStringExtra("url");

        tvTitle.setText(i.getStringExtra("title"));
        tvDesc.setText(i.getStringExtra("description"));
        String content = i.getStringExtra("content");
        content = content.substring(0, content.indexOf("[+") - 1);
        tvContent.setText(content);

        String publishedAt = i.getStringExtra("publishedAt");
        publishedAt =  publishedAt.substring(publishedAt.indexOf('T') + 1, publishedAt.length() - 1) + " " +publishedAt.substring(0, publishedAt.indexOf('T'));
        tvPublishedAt.setText(publishedAt);

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}