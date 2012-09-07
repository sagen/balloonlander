package com.sagen.balloonlander.client;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.*;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.LayoutParams;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new Panel(this));
        Button button = new Button(this);
        RelativeLayout ll = new RelativeLayout(this);

        ll.setLayoutParams(new LayoutParams(MATCH_PARENT, MATCH_PARENT));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        button.setLayoutParams(params);
        button.setText("Submit highscore");
        button.setTop(100);

        ll.addView(button);
        addContentView(ll, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }
}
