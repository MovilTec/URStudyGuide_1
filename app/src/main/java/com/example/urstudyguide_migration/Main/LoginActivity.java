package com.example.urstudyguide_migration.Main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.urstudyguide_migration.Common.Models.Users;
import com.example.urstudyguide_migration.Common.User;
import com.example.urstudyguide_migration.MainActivity;
import com.example.urstudyguide_migration.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button mButton, ForgotPasswordButton;
    private EditText email, password;
    private FirebaseAuth mAuth;
    private ProgressDialog mLoginProgress, mRessetPassProgress;
    private DatabaseReference deviceToken_Reference;

    Users users = new Users();

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mToolbar = findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.Login_email_editText);
        password = findViewById(R.id.Login_password_editText);
        mButton = findViewById(R.id.Login_button);
        ForgotPasswordButton = findViewById(R.id.login_ResetPassword_button);

        mLoginProgress = new ProgressDialog(this);
        mRessetPassProgress = new ProgressDialog(this);

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

        ForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_account = email.getText().toString();
                if(!TextUtils.isEmpty(email_account)){

                    mRessetPassProgress.setTitle("Reset Password");
                    mRessetPassProgress.setMessage("Please wait while sending reset email password");
                    mRessetPassProgress.setCanceledOnTouchOutside(false);
                    mRessetPassProgress.show();

                    forgotPass(email_account);

                }else{
                    mRessetPassProgress.hide();
                    Toast.makeText(LoginActivity.this, "Please enter the forgotten password email address", Toast.LENGTH_LONG).show();
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
                                //Capturando el TokenID del dipositivo desde el cual se registro la cuenta
                                String deviceToken = FirebaseInstanceId.getInstance().getToken();
                                deviceToken_Reference = FirebaseDatabase.getInstance().getReference().child("Users").child(users.getUserID()).child("device_token");
                                deviceToken_Reference.setValue(deviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            //Saving the userID
                                            User.getInstance().setUserID(getBaseContext(), users.getUserID());
                                            // Sending to the mainActivity
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else{
                                            Exception e = task.getException();
                                            mLoginProgress.hide();
                                            Toast.makeText(LoginActivity.this, "Cannot Sing In due to the following conditions: " + e.getMessage(),
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Exception e = task.getException();
                                // If sign in fails, display a message to the user.
                                mLoginProgress.hide();
                                Toast.makeText(LoginActivity.this, "Cannot Sing In due to the following conditions: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
    }

    private void forgotPass(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mRessetPassProgress.dismiss();
                            Toast.makeText(LoginActivity.this, "Reset Email sent SUCESSFULLY!!. Please check your email",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Exception ResetPassError = task.getException();
                            mRessetPassProgress.hide();
                            Toast.makeText(LoginActivity.this, ResetPassError.toString(),
                                    Toast.LENGTH_SHORT).show();
                            System.out.println("******** ERROR while trying to send reset password email code: " + ResetPassError.toString());
                        }
                    }
                });
    }
}
