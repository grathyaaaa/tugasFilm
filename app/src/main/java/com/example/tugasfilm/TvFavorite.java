package com.example.tugasfilm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TvFavorite extends RealmObject {
    @PrimaryKey
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    private String name;
    private String description;
    private String poster;

    public String getLanguange() {
        return languange;
    }

    public void setLanguange(String languange) {
        this.languange = languange;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    private String languange;
    private double rating;
    private  String date;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


}
