package edu.aucegypt.getup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class WorkoutMainActivity extends AppCompatActivity {
    ArrayList<String> strings = new ArrayList<String>();
    ArrayList<Integer> imgs = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_main);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        imgs.add(R.drawable.abdominal);
        imgs.add(R.drawable.biceps);
        imgs.add(R.drawable.brachioradialis);
        imgs.add(R.drawable.deltoid);
        imgs.add(R.drawable.externaloblique);
        imgs.add(R.drawable.fingerextensors);
        imgs.add(R.drawable.fingerflexors);
        imgs.add(R.drawable.calfs);
        imgs.add(R.drawable.gluteusmaximus);
        imgs.add(R.drawable.gluteusmedius);
        imgs.add(R.drawable.hamstrings);
        imgs.add(R.drawable.reardelts);
        imgs.add(R.drawable.lats);
        imgs.add(R.drawable.pecmajor);
        imgs.add(R.drawable.sartorius);
        imgs.add(R.drawable.outercalf);
        imgs.add(R.drawable.serratusanterior);
        imgs.add(R.drawable.tibialisanterior);
        imgs.add(R.drawable.traps);
        imgs.add(R.drawable.triceps);
        imgs.add(R.drawable.quadriceps);
        imgs.add(R.drawable.abductors);

        strings.addAll(Arrays.asList(getResources().getStringArray(R.array.muscles)));

        GridAdapter adapter = new GridAdapter(this, imgs, strings);
        GridView grid = findViewById(R.id.musclegrid);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(WorkoutMainActivity.this, "Loading data...", Toast.LENGTH_SHORT).show();
                String muscle = strings.get(position);
                Intent intent = new Intent(WorkoutMainActivity.this, WorkoutDataActivity.class);
                intent.putExtra("muscle", muscle.toLowerCase());
                startActivity(intent);
            }
        });
    }
    public void onHomeClick(View view) {
        //start the HomePage Activity
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide backward

    }
}