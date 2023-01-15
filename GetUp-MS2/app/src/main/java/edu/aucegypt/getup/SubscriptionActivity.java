package edu.aucegypt.getup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class SubscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_subscription);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowCustomEnabled(true);
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.header_image, null);
//        actionBar.setCustomView(view);
    }
    public void onHomeClick(View view) {
        //start the HomePage Activity
        finish();
    }
}