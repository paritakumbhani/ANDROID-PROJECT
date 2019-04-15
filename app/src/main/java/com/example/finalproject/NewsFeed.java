package com.example.finalproject;

public class NewsFeed {

    private long id;
    private String title;
    private String news;
    private String url;

    public NewsFeed() {
        id=0;
        title=null;
        news=null;
        url=null;
    }

    public NewsFeed(String title, String news, String url) {
        this.title = title;
        this.news = news;
        this.url = url;
    }
    public NewsFeed(String title, String news, long id) {
        this.title = title;
        this.news = news;
        this.news = news;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
