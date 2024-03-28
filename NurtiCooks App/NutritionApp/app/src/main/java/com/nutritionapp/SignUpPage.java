package com.nutritionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpPage extends AppCompatActivity {
    private EditText fullNameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;
    private ImageButton nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        // hide top bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        fullNameEditText = findViewById(R.id.fullname);
        emailEditText = findViewById(R.id.emailInput);
        passwordEditText = findViewById(R.id.passwordInput);
        passwordConfirmEditText = findViewById(R.id.passwordInputConfirm);
        nextButton = findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = fullNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String passwordConfirm = passwordConfirmEditText.getText().toString();

                if (isValidCredentials(fullName, email, password, passwordConfirm)) {
                    // if signup success
                    Toast.makeText(SignUpPage.this, "Signup success", Toast.LENGTH_SHORT).show();
                    // Move to the next screen
                    Intent intent = new Intent(SignUpPage.this, SignUpPage1.class);
                    startActivity(intent);
                } else {
                    // if signup fails
                    Toast.makeText(SignUpPage.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidCredentials(String fullName, String email, String password, String passwordConfirm) {
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() || !password.equals(passwordConfirm)) {
            return true;//////// for testing only !!! i put both as true to go to next page
        } else {
            return true;
        }
    }

}
