package com.harrydrippin.hitchhiker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by harryhong on 2016. 11. 19..
 */
public class ValueSetActivity extends AppCompatActivity {

    ImageView type;
    TextView type_text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valueset);
    }
}
