package edu.aucegypt.getup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {
    ArrayList<String> options = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        options.add("Gym Card");
        options.add("Subscriptions");
        options.add("Rewards");
        options.add("Workout Guides");
        options.add("Nutrition and Wellness");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.listlayout,
                R.id.text1,
                options);

        ListView list = findViewById(R.id.menu);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(options.get(i))
                {
                    case "Rewards":
                    {
                        Intent intent = new Intent(HomePageActivity.this, RewardsActivity.class);
                        startActivity(intent);
                        break;
                    }
                    case "Subscriptions":
                    {
                        Intent intent = new Intent(HomePageActivity.this, SubscriptionActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
               // Toast.makeText(getApplicationContext(),"Clicked "+options.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onSignOutClick(View view) {
        //start the HomePage Activity
        Intent intent = new Intent(HomePageActivity.this, MainActivity.class);
        startActivity(intent);
    }
}