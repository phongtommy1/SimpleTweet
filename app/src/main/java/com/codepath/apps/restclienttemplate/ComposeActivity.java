package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcel;
import org.parceler.Parcels;

import okhttp3.Headers;

public class ComposeActivity extends AppCompatActivity {

    public static final int MAX_TWEET_LENGTH = 280;
    public static final String TAG = "ComposeActivity";

    EditText etCompose;
    Button btnTweet;
    TextView etCount;

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        client = TwitterApp.getRestClient(this);

        etCount = findViewById(R.id.etCount);
        etCompose = findViewById(R.id.etCompose);
        btnTweet = findViewById(R.id.btnTweet);

        // set on click listener
        btnTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make an API call to Twitter to publish the tweet
                String tweetContent = etCompose.getText().toString();
                if(tweetContent.isEmpty()){
                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tweetContent.length() > MAX_TWEET_LENGTH){
                    Toast.makeText(ComposeActivity.this, "Sorry, your tweet is too long", Toast.LENGTH_SHORT).show();
                    return;
                }
                client.publishTweet(tweetContent, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.i(TAG, "onScuess to publsih tweet");
                            try {
                                Tweet tweet = Tweet.fromJson(json.jsonObject);
                                Log.i(TAG, "Published tweet says:" + tweet);
                                Intent intent = new Intent();
                                intent.putExtra("tweet", Parcels.wrap(tweet));
                                setResult(RESULT_OK, intent);
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                            Log.e(TAG, "onFailure to publish", throwable);
                        }

                });

            }
        });
        etCompose.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String wordCount = charSequence.length() + "/280";
                etCount.setText(wordCount);

                if(charSequence.length() > 280 ){
                    btnTweet.setEnabled(false);
                    etCompose.setTextColor(Color.RED);
                }
                else {
                    btnTweet.setEnabled(true);
                    etCompose.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}