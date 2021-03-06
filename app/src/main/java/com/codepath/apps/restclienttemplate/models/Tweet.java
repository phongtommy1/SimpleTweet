package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Tweet {
    public String dateCreated;
    public String body;
    public String createdAt;
    public Long id;
    public User user;


    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.id = jsonObject.getLong("id");
        tweet.dateCreated = dateFormat(tweet.createdAt);
        return tweet;
    }
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length();i++){
            tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
    public String getFormattedTimestamp(){
        return TimeFormatter.getTimeDifference(createdAt);
    }

    private static String dateFormat(String createdAt){
        String dateCreatedv1;
        dateCreatedv1 = createdAt.substring(8,10) + " ";
        dateCreatedv1 += createdAt.substring(4,7) + " ";
        dateCreatedv1 += createdAt.substring(26,30);
        return dateCreatedv1;

    }
}
