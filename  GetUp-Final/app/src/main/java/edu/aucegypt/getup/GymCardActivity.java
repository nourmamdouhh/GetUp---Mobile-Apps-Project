package edu.aucegypt.getup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GymCardActivity extends AppCompatActivity {
    ImageView QRCode;
    TextView Name;
    DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gym_card);

        QRCode = findViewById(R.id.QRCode);
        Name = findViewById(R.id.Name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        MultiFormatWriter mWriter = new MultiFormatWriter();
        try {
            //BitMatrix class to encode entered text and set Width & Height
            BitMatrix mMatrix = mWriter.encode(uid, BarcodeFormat.QR_CODE, 400,400);
            BarcodeEncoder mEncoder = new BarcodeEncoder();
            Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
            QRCode.setImageBitmap(mBitmap);//Setting generated QR code to imageView
        } catch (WriterException e) {
            e.printStackTrace();
        }

        dbRef = FirebaseDatabase.getInstance().getReference("Users");
        dbRef.child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
              if (task.isSuccessful()){
                  if(task.getResult().exists()){
                      Toast.makeText(GymCardActivity.this, "Loading..", Toast.LENGTH_SHORT).show();
                      DataSnapshot dataSnapshot = task.getResult();
                      Name.setText(String.valueOf(dataSnapshot.child("Name").getValue()));
                  }
                  else {
                      Name.setTextSize(20);
                      Name.setText("Contact us at info@getup.com to retrieve your details");

                      Toast.makeText(GymCardActivity.this, "No user", Toast.LENGTH_SHORT).show();
                  }
              } else
                  Toast.makeText(GymCardActivity.this, "Failed to read data", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void onHomeClick(View view) {
        //start the HomePage Activity
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide backward
    }
}