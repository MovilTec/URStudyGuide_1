package com.example.bbvacontrol.uranitexpert;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.Toast;

public class AccountSettings extends AppCompatActivity {

    private Toolbar mToolBar;
    private TextView UserNickName_TextView;
    private TextView UserStatus_TextView;
    private static final int GALLERY_PICK = 1;
    private AlertDialog dialog;

    Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        mToolBar = findViewById(R.id.accountSettings_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        UserNickName_TextView = findViewById(R.id.accountName_textView);
        UserStatus_TextView = findViewById(R.id.accountStatus_textView);

        users.getCurrentUserInfo(UserNickName_TextView, UserStatus_TextView);

        Button changeImageButton = findViewById(R.id.accountSettings_changeImage_button);
        Button changeStatusButton = findViewById(R.id.accountSettings_changeStatus_button);

        changeStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder mBuilder =  new AlertDialog.Builder(AccountSettings.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_change_status, null);
                final TextInputEditText mNewStatus = mView.findViewById(R.id.ChangeStatus_newStatus_TextInputLayout);
                Button mChangeStatusButton = mView.findViewById(R.id.ChangeStatus_changeStatus_button);

                mChangeStatusButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!TextUtils.isEmpty(mNewStatus.getText())){
                            //Change the Status on FireBase!!
                            String newStatus = mNewStatus.getText().toString();
                            users.setUserNewStatus(newStatus);
                            dialog.dismiss();
                        }else{
                            Toast.makeText(AccountSettings.this, "New status haven't been entered!", Toast.LENGTH_SHORT);
                        }
                    }
                });
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });

        changeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryIntent, "SELECT IMAGE"), GALLERY_PICK);

            }
        });

    }
}
