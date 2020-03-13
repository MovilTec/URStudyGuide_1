package com.example.urstudyguide_migration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.urstudyguide_migration.Common.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import io.reactivex.annotations.NonNull;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout nickName;
    private EditText email;
    private EditText password;
    private Button createAccount;
    private ProgressDialog mRegProgress;
    private Toolbar mToolbar;
    private FirebaseAuth mAuth;
    private DatabaseReference deviceToken_Reference;

    Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create an Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Campos de Registro de Usuario
        nickName = findViewById(R.id.reg_userNickName_textInputLayout);
        email = (EditText) findViewById(R.id.reg_email);
        password = (EditText) findViewById(R.id.reg_password);
        createAccount = (Button) findViewById(R.id.createAccountButton);

        mRegProgress = new ProgressDialog(this);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nickName_account = nickName.getEditText().getText().toString();
                String email_account = email.getText().toString();
                String password_account = password.getText().toString();
                if(!TextUtils.isEmpty(email_account) || !TextUtils.isEmpty(password_account) || TextUtils.isEmpty(nickName.getEditText().getText())) {
                    mRegProgress.setTitle("Registering new User");
                    mRegProgress.setMessage("Please wait while it is creating your account");
                    mRegProgress.setCanceledOnTouchOutside(false);
                    mRegProgress.show();

                    register_user(email_account, password_account, nickName_account);
                }else{
                    Toast.makeText(RegisterActivity.this, "Registration failed. Please fill all the fields on the registration",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void register_user(String email, String password, final String nickName){

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            users.registerNewUser(nickName);

                            mRegProgress.dismiss();
                            //Capturando el TokenID del dipositivo desde el cual se registro la cuenta
                            String deviceToken = FirebaseInstanceId.getInstance().getToken();
                            deviceToken_Reference = FirebaseDatabase.getInstance().getReference().child("Users").child(users.getUserID()).child("device_token");
                            deviceToken_Reference.setValue(deviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        //updateUI(user);
                                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                        startActivity(mainIntent);
                                        finish();
                                    }else{
                                        mRegProgress.hide();
                                        Exception e = task.getException();
                                        System.out.println("Error when register user's device Token in the database. ERROR CODE: " + e);
                                        Toast.makeText(RegisterActivity.this, "Error when register user's device Token in the database. ERROR CODE: " + e, Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {

                            mRegProgress.hide();
                            Exception error = task.getException();
                            String passwordError = error.toString();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, passwordError,
                                    Toast.LENGTH_LONG).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser user){
        if(user == null){
            //TODO:- somthing related to the user
        }
    }

}
