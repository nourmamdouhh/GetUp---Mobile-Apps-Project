package edu.aucegypt.getup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity<Firebase> extends AppCompatActivity {
    EditText SignUpEmail, SignUpPassword, SignUpConfirmPass;
    Button SignUpButton;
    TextView SignInHere;
    FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        SignUpButton = findViewById(R.id.SignUpButton);
        SignUpEmail = findViewById(R.id.SignUpEmail);
        SignUpPassword = findViewById(R.id.SignUpPassword);
        SignUpConfirmPass = findViewById(R.id.SignUpConfirmPass);
        SignInHere = findViewById(R.id.SignInHere);

        fbAuth = FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        SignInHere.setOnClickListener(view ->{
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); // Slide back
        });}

    public void onSignUpClick(View view) {

        // Create User
        String email = SignUpEmail.getText().toString();
        String password = SignUpPassword.getText().toString();
        String confirmPass = SignUpConfirmPass.getText().toString();

        boolean valid = validatePassword(email, password, confirmPass);
        if (valid){
            fbAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Signed up successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(SignUpActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    public boolean validatePassword(String email, String password, String confirmPass){
        boolean valid = true;

        if (TextUtils.isEmpty(email)){
            SignUpEmail.setError("Email field cannot be empty.");
            SignUpEmail.requestFocus();
            valid = false;
        }
        else if (TextUtils.isEmpty(password)){
            SignUpPassword.setError("Password field cannot be empty.");
            SignUpPassword.requestFocus();
            valid = false;
        }
        else if (TextUtils.isEmpty(confirmPass)){
            SignUpConfirmPass.setError("Password field cannot be empty.");
            SignUpConfirmPass.requestFocus();
            valid = false;
        }
        else if (!password.equals(confirmPass)){
            SignUpPassword.setError("Passwords are not equal.");
            SignUpConfirmPass.setError("Passwords are not equal.");
            SignUpPassword.requestFocus();
            SignUpConfirmPass.requestFocus();
            valid = false;
        }
        else valid = true;

        return valid;
    }
}