package edu.aucegypt.getup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

public class RedeemActivity extends AppCompatActivity {

    ArrayList<String> strings = new ArrayList<String>();
    ArrayList<Integer> imgs = new ArrayList<Integer>();
    ArrayList<Integer> cost = new ArrayList<Integer>();
    Integer points;
    DatabaseReference dbRef;
    TextView pts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        pts = findViewById(R.id.rpoints);

//        points = 1676;


        // Fetch Points
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        dbRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(RedeemActivity.this, "Loading..", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        pts.setText("Points: " + String.valueOf(dataSnapshot.child("Points").getValue()));
                        points = Integer.valueOf(String.valueOf(dataSnapshot.child("Points").getValue()));

                    }
                    else {
                        pts.setTextSize(20);
                        pts.setText("Contact us at info@getup.com to retrieve your details");

                        Toast.makeText(RedeemActivity.this, "No user", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(RedeemActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
            }
        });

        imgs.add(R.drawable.energy);
        imgs.add(R.drawable.crossfit);
        imgs.add(R.drawable.decathlon);
        imgs.add(R.drawable.nike);


        strings.add("Free Energy Drink - 1000 Points");
        strings.add("Free CrossFit Class - 1500 Points");
        strings.add("10% off at Decathlon - 1000 Points");
        strings.add("10% off at Nike - 2000 Points");

        cost.add(1000);
        cost.add(1500);
         GridAdapter adapter = new GridAdapter(this, imgs, strings);
        GridView grid = findViewById(R.id.grid);
        grid.setAdapter(adapter);

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(cost.get(i) <= points) {

                    String msg = new String();
                    msg = "Please show this message " +
                            "at the counter to receive a " + strings.get(i);

                    AlertDialog.Builder builder = new AlertDialog.Builder(RedeemActivity.this);
                    builder.setMessage(msg);
                    builder.setTitle("Thanks for being a GetUp user");
                    builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {}
                    });

                    builder.create();

                    builder.show();

                    points -= cost.get(i);
                    // Update DB
                    dbRef.child(uid+"/Points").setValue(points);
                    pts.setText("Points: " + points.toString());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Insufficient Points", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void onHomeClick(View view) {
        //start the HomePage Activity
        Intent intent = new Intent(RedeemActivity.this, HomePageActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide backward
    }
}