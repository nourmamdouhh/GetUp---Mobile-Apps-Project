package edu.aucegypt.getup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.shape.ShapePath;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomePageActivity extends AppCompatActivity {
    ArrayList<String> options = new ArrayList<String>();

    TextView capacity, peak;
    ProgressBar capBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        capacity = findViewById(R.id.capacity);
        peak = findViewById(R.id.peak);
        capBar = findViewById(R.id.capBar);

        checkGym(capacity, peak, capBar);

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
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Slide forward
                        break;
                    }
                    case "Subscriptions":
                    {
                        Intent intent = new Intent(HomePageActivity.this, SubscriptionActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Slide forward
                        break;
                    }
                    case "Gym Card":
                    {
                        Intent intent = new Intent(HomePageActivity.this, GymCardActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Slide forward
                        break;
                    }
                    case "Workout Guides":
                    {
                        Intent intent = new Intent(HomePageActivity.this, WorkoutMainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Slide forward
                        break;
                    }
                    case "Nutrition and Wellness":
                    {
                        Intent intent = new Intent(HomePageActivity.this, NutritionActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Slide forward
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
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide backward

    }

    public void checkGym(TextView capacity, TextView peak, ProgressBar capBar){
        DatabaseReference dbRef;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        dbRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(HomePageActivity.this, "Loading..", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();

                       switch(String.valueOf(dataSnapshot.child("Gym").getValue())){
                           case "Gold's Gym":
                               capacity.setText("Gold's Gym Capacity");
                               peak.setText("5 PM to 6 PM GMT +2");
                               capBar.setProgress(40);
                               break;
                           case "Ignite":
                               capacity.setText("Ignite Capacity");
                               peak.setText("4:30 PM to 5:30 PM GMT +2");
                               capBar.setProgress(65);
                               break;
                           case "Titans":
                               capacity.setText("Titan's Capacity");
                               peak.setText("3 PM to 4 PM GMT +2");
                               capBar.setProgress(80);
                               break;
                           default:
                               capacity.setTextSize(20);
                               capacity.setText("No gym info available at the moment");
                               peak.setText("None");
                               capBar.setProgress(0);
                               break;

                       }

                    }
                    else {
                        capacity.setTextSize(20);
                        capacity.setText("No gym info available at the moment");
                        peak.setText("None");
                        capBar.setProgress(0);
                        Toast.makeText(HomePageActivity.this, "No user", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(HomePageActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}