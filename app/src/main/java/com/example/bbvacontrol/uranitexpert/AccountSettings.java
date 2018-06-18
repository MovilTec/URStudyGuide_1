package com.example.bbvacontrol.uranitexpert;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountSettings extends AppCompatActivity {

    private Toolbar mToolBar;
    private TextView UserNickName_TextView;
    private TextView UserStatus_TextView;
    private CircleImageView UserAvatar;

    private static final int GALLERY_PICK = 1;
    private AlertDialog dialog;
    private ProgressDialog mProgressDialog;
    //Almacenamiento Firebase
    private StorageReference mImageStorage;

    Users users = new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        mToolBar = findViewById(R.id.accountSettings_toolbar);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //Inicializando el almacenamiento en Firebase
        mImageStorage = FirebaseStorage.getInstance().getReference();

        UserNickName_TextView = findViewById(R.id.accountName_textView);
        UserStatus_TextView = findViewById(R.id.accountStatus_textView);
        UserAvatar = findViewById(R.id.accountImage_CircleImageView);

        users.getCurrentUserInfo(UserNickName_TextView, UserStatus_TextView, UserAvatar);

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

//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .start(AccountSettings.this);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_PICK && resultCode == RESULT_OK){

            mProgressDialog = new ProgressDialog(AccountSettings.this);
            mProgressDialog.setTitle("Uploading image...");
            mProgressDialog.setMessage("Please wait while the image is uploading");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();

            Uri imageUrl = data.getData();

            CropImage.activity(imageUrl)
                    .setAspectRatio(1, 1)
                    .start(AccountSettings.this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){

            final CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK){
                Uri resultUri = result.getUri();

                StorageReference filepath = mImageStorage.child(users.getUserID()).child("profile_images").child("profile_image.jpg");

                filepath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            //String downloadURL = task.getResult().getDownloadUrl().toString();
                            mImageStorage.child(users.getUserID()).child("profile_images").child("profile_image.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String downloadUri = uri.toString();
                                    users.setUserNewImage(downloadUri, mProgressDialog, AccountSettings.this);
//                                    mProgressDialog.dismiss();
                                }
                            });
                        } else {
                            Exception error = result.getError();
                            Toast.makeText(AccountSettings.this, (CharSequence) error, Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    }
                });

            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
                Toast.makeText(this, (CharSequence) error, Toast.LENGTH_SHORT).show();
            }

        }

    }
}
