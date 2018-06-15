package com.example.bbvacontrol.uranitexpert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mButton;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoginProgress;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.Login_email_editText);
        password = findViewById(R.id.Login_password_editText);
        mButton = findViewById(R.id.Login_button);

        mLoginProgress = new ProgressDialog(this);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_account = email.getText().toString();
                String password_account = password.getText().toString();
                if(!TextUtils.isEmpty(email_account) || !TextUtils.isEmpty(password_account)){

                    mLoginProgress.setTitle("Loggin In");
                    mLoginProgress.setMessage("Please wait while checking your information");
                    mLoginProgress.setCanceledOnTouchOutside(false);
                    mLoginProgress.show();

                    singInUser(email_account, password_account);
                }
            }
        });
    }

    private void singInUser(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            mLoginProgress.dismiss();

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            mLoginProgress.hide();
                            Toast.makeText(LoginActivity.this, "Cannot Sing In. Please check user and password and try again. Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }
}
