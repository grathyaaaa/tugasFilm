package com.example.tugasfilm.model;

import android.net.Uri;

import com.example.tugasfilm.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

public class Api {
    private static final String API_KEY = BuildConfig.MOVIEDB_API_KEY;
    private static final String BASE_URL = BuildConfig.BASE_URL;
    private static final String LOOKUP_ALL_TEAM = "lookup_all_teams.php";
    private static final String ID = "api_key";
    private static final String PREMIERE_LEAGUE_ID = "4328";

    public static URL getAiring(){

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("tv")
                .appendPath("airing_today")
                .appendQueryParameter(ID, API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .build();

        URL url = null;
        try{
            url = new URL(uri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static URL getPopular(){

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("tv")
                .appendPath("popular")
                .appendQueryParameter(ID, API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .build();

        URL url = null;
        try{
            url = new URL(uri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    public static URL getTopRated(){

        Uri uri = Uri.parse(BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("tv")
                .appendPath("top_rated")
                .appendQueryParameter(ID, API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .build();

        URL url = null;
        try{
            url = new URL(uri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }




}


