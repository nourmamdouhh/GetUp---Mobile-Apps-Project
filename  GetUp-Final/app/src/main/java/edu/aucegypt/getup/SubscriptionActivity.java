package edu.aucegypt.getup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubscriptionActivity extends AppCompatActivity {

    TextView StartDate, EndDate, Type, tSessions, inBody, invitations, freezeDays;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_subscription);

        StartDate = findViewById(R.id.StartDate);
        EndDate = findViewById(R.id.EndDate);
        Type = findViewById(R.id.Type);
        tSessions = findViewById(R.id.tSessions);
        inBody = findViewById(R.id.inBody);
        invitations = findViewById(R.id.invitations);
        freezeDays = findViewById(R.id.freezeDays);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        dbRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if(task.getResult().exists()){
                        Toast.makeText(SubscriptionActivity.this, "Loading..", Toast.LENGTH_SHORT).show();
                        DataSnapshot dataSnapshot = task.getResult();
                        StartDate.setText("Start Date: "+ String.valueOf(dataSnapshot.child("Start").getValue()));
                        EndDate.setText("End Date: "+ String.valueOf(dataSnapshot.child("End").getValue()));
                        Type.setText("Type: "+ String.valueOf(dataSnapshot.child("Type").getValue()));
                        tSessions.setText("Trainer Sessions: "+ String.valueOf(dataSnapshot.child("Sessions").getValue()));
                        inBody.setText("In-body Remaining: "+ String.valueOf(dataSnapshot.child("InBody").getValue()));
                        invitations.setText("Invitations Remaining: "+ String.valueOf(dataSnapshot.child("Invitations").getValue()));
                        freezeDays.setText("Freeze Days Remaining: "+ String.valueOf(dataSnapshot.child("Freeze").getValue()));
                    }
                    else {
                        StartDate.setText("");
                        EndDate.setText("");
                        Type.setText("");
                        tSessions.setTextSize(20);
                        tSessions.setText("Contact us at info@getup.com to retrieve your details");
                        inBody.setText("");
                        invitations.setText("");
                        freezeDays.setText("");

                        Toast.makeText(SubscriptionActivity.this, "No user", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(SubscriptionActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void onHomeClick(View view) {
        //start the HomePage Activity
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide backward
    }
}