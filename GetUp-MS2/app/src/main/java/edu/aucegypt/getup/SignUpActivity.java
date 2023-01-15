package edu.aucegypt.getup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void onSignUpClick(View view) {
        //start the HomePage Activity
        Intent intent = new Intent(SignUpActivity.this, HomePageActivity.class);
        startActivity(intent);
    }
}