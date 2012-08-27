package com.sagen.balloonlander.client.util;

import com.sagen.balloonlander.client.balloon.BalloonController;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;

public class ScoreUtil {
    public static int getScoreAfterLanding(BalloonController balloon){
        return (int) (1000000 - ((100 - balloon.fuel()) * 6700) - (balloon.dx() * 330000) - (balloon.dy() * 230000));
    }

    public static void publishHighScore(int score){
        HttpClient client = new DefaultHttpClient();
        HttpParams params = new BasicHttpParams();
        params.setParameter("name", "user");
        params.setIntParameter("score", score);
        HttpPost post = new HttpPost("http://cookiejar.no:9998/highscore");
        post.setParams(params);
        try {
            client.execute(post);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
