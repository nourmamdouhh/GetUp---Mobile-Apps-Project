package edu.aucegypt.getup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Username = findViewById(R.id.username);
        Password = findViewById(R.id.password);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    public void onLoginClick(View view) {
        String username = Username.getText().toString();
        String password = Password.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username and password are required", Toast.LENGTH_SHORT).show();
        } else {
            // Perform login here, for example by calling a login API
            // If the login is successful, navigate to the next activity
            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show();
            // Go to Homepage
            Intent intent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(intent);
        }
    }


    public void onSignUpClick(View view) {
        //start the Signup Activity
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}

