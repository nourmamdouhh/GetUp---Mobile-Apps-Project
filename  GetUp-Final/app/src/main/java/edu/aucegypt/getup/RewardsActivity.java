package edu.aucegypt.getup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RewardsActivity extends AppCompatActivity {

    ArrayList<String> strings = new ArrayList<String>();
    ArrayList<Integer> imgs = new ArrayList<Integer>();
    ArrayList<Integer> cost = new ArrayList<Integer>();
    Integer points;
    DatabaseReference dbRef;
    TextView pts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pts = findViewById(R.id.points);

        //Fetch points
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        dbRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(RewardsActivity.this, "\"Loading..\"", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        pts.setText("Points: " + String.valueOf(dataSnapshot.child("Points").getValue()));
                        Log.d("Hereeeee", String.valueOf(dataSnapshot.child("Points").getValue()));
                        points = Integer.valueOf(String.valueOf(dataSnapshot.child("Points").getValue()));
                    }
                    else {
                        pts.setTextSize(20);
                        pts.setText("Contact us at info@getup.com to retrieve your details");

                        Toast.makeText(RewardsActivity.this, "No user", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(RewardsActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
            }
        });


        imgs.add(R.drawable.workoutvideo);
        imgs.add(R.drawable.booksession);
        imgs.add(R.drawable.friend);
        imgs.add(R.drawable.streak);

        strings.add("Follow a Workout Video - 300 Points");
        strings.add("Book a Workout Session - 400 Points");
        strings.add("Invite a Friend - 300 Points");
        strings.add("5 Day Streak - 500 Points");

        cost.add(300);
        cost.add(400);
        cost.add(300);
        cost.add(500);

        GridAdapter adapter = new GridAdapter(this, imgs, strings);
        GridView grid = findViewById(R.id.grid);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0) {
                    String webpage = new String();
                    webpage = "https://www.youtube.com/watch?v=UBMk30rjy0o";
                    Uri link = Uri.parse(webpage);
                    startActivity(new Intent(Intent.ACTION_VIEW, link));
                    points += cost.get(i);
                    // Update db
                    dbRef.child(uid+"/Points").setValue(points);
                    pts.setText("Points: " + points.toString());

                    Toast.makeText(getApplicationContext(), "Points added :) " ,Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Clicked " + strings.get(i), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onHomeClick(View view) {
        //start the HomePage Activity
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide backward
    }

    public void onRedeemClick(View view) {
        //start the HomePage Activity
        Intent intent = new Intent(RewardsActivity.this, RedeemActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Slide forward
    }
}