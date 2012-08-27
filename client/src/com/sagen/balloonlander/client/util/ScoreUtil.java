package com.sagen.balloonlander.client.util;

import com.sagen.balloonlander.client.balloon.BalloonController;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ScoreUtil {
    public static int getScoreAfterLanding(BalloonController balloon){
        return (int) (1000000 - ((100 - balloon.fuel()) * 6700) - (balloon.dx() * 330000) - (balloon.dy() * 230000));
    }

    public static void publishHighScore(int score){
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://cookiejar.no:9998/highscore");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("name", "user"));
        nvps.add(new BasicNameValuePair("score", Integer.toString(score)));
        try {
            post.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        try {
            client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
