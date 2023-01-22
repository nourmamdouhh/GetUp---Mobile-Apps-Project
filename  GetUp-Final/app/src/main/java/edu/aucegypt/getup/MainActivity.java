package edu.aucegypt.getup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText SignInEmail, SignInPassword;
    Button SignInButton;
    TextView SignUpHere;
    FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SignInEmail = findViewById(R.id.SignInEmail);
        SignInPassword = findViewById(R.id.SignInPassword);
        SignInButton = findViewById(R.id.SignInButton);
        SignUpHere = findViewById(R.id.SignUpHere);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        fbAuth = FirebaseAuth.getInstance();

        SignUpHere.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Slide forward
        });
    }

    public void onSignInClick(View view) {
        String email = SignInEmail.getText().toString();
        String password = SignInPassword.getText().toString();

        if (TextUtils.isEmpty(email)){
            SignInEmail.setError("Email field cannot be empty.");
            SignInEmail.requestFocus();
        }else if (TextUtils.isEmpty(password)){
            SignInPassword.setError("Password field cannot be empty.");
            SignInPassword.requestFocus();
        }else{
            fbAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Signing in...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                    }else{
                        Toast.makeText(MainActivity.this, "Sign in Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void onSignUpClick(View view) {
        //start the Signup Activity
        Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}

