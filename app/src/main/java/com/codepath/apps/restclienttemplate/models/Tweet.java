package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.codepath.apps.restclienttemplate.TimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId"))
public class Tweet {

    @PrimaryKey
    @ColumnInfo
    public Long id;

    @ColumnInfo
    public String dateCreated;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public String createdAt;

    @ColumnInfo
    public long userId;

    @Ignore
    public User user;

    // empty constructor
    public Tweet(){}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("text");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.id = jsonObject.getLong("id");
        tweet.dateCreated = dateFormat(tweet.createdAt);
        User user = User.fromJson(jsonObject.getJSONObject("user"));
        tweet.user = user;

        tweet.userId = user.id;
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
