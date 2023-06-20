package com.example.newsapp;

public class CategoryModel {
    private String urlToImage;
    private String name;

    public CategoryModel(String urlToImage, String name) {
        this.urlToImage = urlToImage;
        this.name = name;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
