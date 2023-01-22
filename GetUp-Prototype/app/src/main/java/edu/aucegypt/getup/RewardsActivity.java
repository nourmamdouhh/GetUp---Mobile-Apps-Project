package edu.aucegypt.getup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class RewardsActivity extends AppCompatActivity {

    ArrayList<String> strings = new ArrayList<String>();
    ArrayList<Integer> imgs = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imgs.add(R.drawable.workoutvideo);
        imgs.add(R.drawable.booksession);
        imgs.add(R.drawable.friend);
        imgs.add(R.drawable.streak);

        strings.add("Follow a Workout Video - 300 Points");
        strings.add("Book a Workout Session - 400 Points");
        strings.add("Invite a Friend - 300 Points");
        strings.add("5 Day Streak - 500 Points");

        GridAdapter adapter = new GridAdapter(this, imgs, strings);
        GridView grid = findViewById(R.id.grid);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"Clicked "+strings.get(i), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onHomeClick(View view) {
        //start the HomePage Activity
        finish();
    }
}