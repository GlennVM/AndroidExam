package com.example.glenn.reddit.models;

/**
 * Created by Glenn on 23/08/2016.
 */
public class Post {
    private String title, imageUrl, author, permalink, thumbnail;
    private int upvotes;

    public Post(String title, int upvotes){
        this(title,upvotes,null,null,null,null);
    }

    public Post(String title, int upvotes, String thumbnail, String permalink, String author, String imageUrl) {
        setTitle(title);
        setUpvotes(upvotes);
        setThumbnail(thumbnail);
        setPermalink(permalink);
        setAuthor(author);
        setImageUrl(imageUrl);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }
}
