package org.yanzi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.yanzi.playcamera.R;

import static android.support.v7.appcompat.R.styleable.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(android.view.View view){
        startActivity(new Intent(this, CameraActivity.class));
    }
}
