package com.example.urstudyguide_migration.Common.Models;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.urstudyguide_migration.Social.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.annotations.NonNull;

public class Users {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser mCurrentUser;
    private DatabaseReference mFriendRequestedDatabase, QuestionerRequestedUser, notifications_Reference;
    private final DatabaseReference mRoofRef = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference Users_RelationShips_Reference = FirebaseDatabase.getInstance().getReference().child("Users_Relationships");

    public void registerNewUser(String user_name){
        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = current_user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);


        HashMap<String, String> userMap = new HashMap();
        userMap.put("name", user_name);
        userMap.put("status", "Hi there!, I'm using URStudyGuide App.");
        userMap.put("image", "default");
        userMap.put("thumb_image", "default");

        mDatabase.setValue(userMap);

    }

    public void getUserImage(String user_ID, final CircleImageView user_image){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_ID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userImage = dataSnapshot.child("thumb_image").getValue().toString();
                if(!userImage.equals("default")) {
                    Picasso.get().load(userImage).into(user_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getProfileUserImageLarge(String userID, final ImageView user_Image){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String userImage = dataSnapshot.child("image").getValue().toString();
                if(!userImage.equals("default")) {
                    try {
                        Picasso.get().load(userImage).into(user_Image);
                    }catch(Exception e){
                        System.out.println("*********Image loading error!! " + e.getMessage());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public String getName(String user){
//        String name;
//        DatabaseReference mUserReference;
//        mUserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(user).child("name");
//        mUserReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//               String rname = dataSnapshot.getValue().toString();
//               name = rname;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        };
//        return "";
//    }

    public void getUserName(String user_ID, final TextView userNameTextView){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_ID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String UserName = dataSnapshot.child("name").getValue().toString();
                userNameTextView.setText(UserName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setBarUserTitle(String user_ID, final Toolbar mToolbar){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_ID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String UserName = dataSnapshot.child("name").getValue().toString();
                mToolbar.setTitle(UserName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void getUsersStatus(String user_ID, final TextView userStatusTextView){
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_ID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String UserStatus = dataSnapshot.child("status").getValue().toString();
                userStatusTextView.setText(UserStatus);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public String getUserID(){
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_userID = mCurrentUser.getUid();
        return current_userID;
    }

    public void setUserNewStatus(String newStatus){
        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_user = mCurrentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user);
        mDatabase.child("status").setValue(newStatus);

    }

    public void setUserNewImage(String newImage, final ProgressDialog mProgressDialog, final Context context, final StorageReference thumbNailImage_reference){

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserID());
        mDatabase.child("image").setValue(newImage).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //mProgressDialog.dismiss();
                    setNewUser_thumbnailImageURL(mProgressDialog, context, thumbNailImage_reference);
                    Toast.makeText(context, "Sucess.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setNewUser_thumbnailImageURL(final ProgressDialog mProgressDialog, final Context context, StorageReference thumb_storage_reference){

        thumb_storage_reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(getUserID());
                String downladURL = uri.toString();
                mDatabase.child("thumb_image").setValue(downladURL).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            mProgressDialog.dismiss();
                            Toast.makeText(context, "Sucess.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    public void getCurrentUserInfo(final TextView user_nickName_TextView, final TextView user_status_TextView, final CircleImageView user_avatar_image){

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String current_user = mCurrentUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_user);
        //Habilitando las funciones Fuera de Linea
        mDatabase.keepSynced(true);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String user_nickName = dataSnapshot.child("name").getValue().toString();
                final String user_image = dataSnapshot.child("image").getValue().toString();
                String user_status = dataSnapshot.child("status").getValue().toString();
                String user_thumb_image = dataSnapshot.child("thumb_image").getValue().toString();

                user_nickName_TextView.setText(user_nickName);
                user_status_TextView.setText(user_status);

                if(!user_image.equals("default")) {
                    Picasso.get().load(user_image)
                            .networkPolicy(NetworkPolicy.OFFLINE)
                            .into(user_avatar_image, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError(Exception e) {
                                 Picasso.get().load(user_image).into(user_avatar_image);
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    //Metodo para manejar todas las solicitudes de amistad dependiendo del estado
    public void sendFriendRequest(int friend_request_state, final Context context, final DatabaseReference QuestionerRequesterDatabase, final ProgressDialog mProgressDialog, final String requestedUserID, final Button FriendReqButton){
        //case 0: not friends
        //case 1: friend request sent
        //case 2: already friends
        //case 3: friend request recived
        final ProfileActivity userProfile = new ProfileActivity();
        switch(friend_request_state){
            case 0:
                //Solicitando información del usuariosolicitante y cargandola dentro del Arreglo de información
                notifications_Reference = FirebaseDatabase.getInstance().getReference().child("Users_notifications");
//                setUserHashMap();
                //Configurando nodo del usuario solicitador (Preguntador)
                QuestionerRequesterDatabase.child(getUserID()).child(requestedUserID).child("request_status").setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            //Configurando el estatus de la solicitd de amistad al usuario solocitado (SOLICITUD ENVIADA)
                            QuestionerRequesterDatabase.child(requestedUserID).child(getUserID()).child("request_status").setValue(3).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //Enviando los datos a la Base de Datos
                                        QuestionerRequestedUser = FirebaseDatabase.getInstance().getReference().child("Requested_Users").child(requestedUserID).child(getUserID());
                                        QuestionerRequestedUser.setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){

                                                    HashMap<String, String> notificationData = new HashMap();
                                                    notificationData.put("from", getUserID());
                                                    notificationData.put("type", "request");
                                                    notifications_Reference.child(requestedUserID).push().setValue(notificationData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                mProgressDialog.dismiss();
                                                                Toast.makeText(context, "Request has been sent Succesfully!", Toast.LENGTH_SHORT).show();
                                                                FriendReqButton.setText("Cancel Request");
                                                                FriendReqButton.setBackgroundColor(Color.RED);
                                                                userProfile.changeRequestState(1);
                                                                FriendReqButton.setEnabled(true);
                                                            }else{
                                                                Exception e = task.getException();
                                                                System.out.println("************** ERROR when capture Requester info HashMap Step***** Error: " + e);
                                                                Toast.makeText(context, "Error in HashMap Notification Step " + e.toString(), Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                                }else{
                                                    //Error al capturar los datos del usuario solicitante
                                                    Exception RequestedUserError = task.getException();
                                                    System.out.println("************** ERROR when capture Requester info ***** Error: " + RequestedUserError);
                                                    Toast.makeText(context, RequestedUserError.toString(), Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }else{
                                        mProgressDialog.hide();
                                        Exception error = task.getException();
                                        Toast.makeText(context, "Error durante el envio de la solicitud " + error, Toast.LENGTH_LONG).show();
                                        System.out.println("*********** Questioner Request ERROR: *** " + error);
                                    }
                                }
                            });
                        }else{
                            mProgressDialog.dismiss();
                             Exception error = task.getException();
                             Toast.makeText(context, "Error durante el envio de la solicitud " + error, Toast.LENGTH_LONG).show();
                            System.out.println("*********** Questioner Request ERROR: *** " + error);
                        }
                    }
                });
                break;
            case 1:
                QuestionerRequesterDatabase.child(getUserID()).child(requestedUserID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        QuestionerRequestedUser = FirebaseDatabase.getInstance().getReference().child("Requested_Users").child(requestedUserID).child(getUserID());
                        mFriendRequestedDatabase.child(requestedUserID).child(getUserID()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                QuestionerRequestedUser.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Request succesfully canceled!", Toast.LENGTH_SHORT).show();
                                        FriendReqButton.setText("Send Request");
                                        FriendReqButton.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                                        userProfile.changeRequestState(0);
                                        FriendReqButton.setEnabled(true);
                                    }
                                });
                            }
                        });
                    }
                });
                break;
            case 2:
                Toast.makeText(context,"Has intentado borrar a un amigo!", Toast.LENGTH_LONG).show();
                QuestionerRequesterDatabase.child(getUserID()).child(requestedUserID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            QuestionerRequesterDatabase.child(requestedUserID).child(getUserID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        //Removiendo la referencia del usuario de la tabla de "Amigos" usuario raíz - usuaro referencia
                                        Users_RelationShips_Reference.child(getUserID()).child(requestedUserID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    //Removiendo la referencia del usuario de la tabla de "Amigos" usuaro referencia - usuario raíz
                                                    Users_RelationShips_Reference.child(requestedUserID).child(getUserID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(context, "User succesfully REMOVED from Relationships!", Toast.LENGTH_SHORT).show();
                                                                FriendReqButton.setText("Send Request");
                                                                FriendReqButton.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                                                                userProfile.changeRequestState(0);
                                                                FriendReqButton.setEnabled(true);
                                                            }else{
                                                                Exception EliminateFriendERROR = task.getException();
                                                                System.out.println("********** Han error has ocurred while eliminating friend request status (4° STEP). ERROR CODE: "+ EliminateFriendERROR);
                                                            }
                                                        }
                                                    });
                                                }else{
                                                    Exception EliminateFriendERROR = task.getException();
                                                    System.out.println("********** Han error has ocurred while eliminating friend request status (3° STEP). ERROR CODE: "+ EliminateFriendERROR);
                                                }
                                            }
                                        });
                                    }else{
                                        Exception EliminateFriendERROR = task.getException();
                                        System.out.println("********** Han error has ocurred while eliminating friend request status (2° STEP). ERROR CODE: "+ EliminateFriendERROR);
                                    }
                                }
                            });
                        }else{
                            Exception EliminateFriendERROR = task.getException();
                            System.out.println("********** Han error has ocurred while eliminating friend request status (1° STEP). ERROR CODE: "+ EliminateFriendERROR);
                        }
                    }
                });
                break;
            case 3:
                eliminateUserRequest(getUserID(),requestedUserID, context);
                FriendReqButton.setText("Send Request");
                FriendReqButton.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                userProfile.changeRequestState(0);
                FriendReqButton.setEnabled(true);
                break;
        }
    }

    public void getFriendRequestStatus(final String requestedFriend_ID, final Button friendReqButton, final Button messageButton, final Context context){
        final ProfileActivity userProfile = new ProfileActivity();
        mFriendRequestedDatabase = FirebaseDatabase.getInstance().getReference().child("Users_requests").child(getUserID());
        mFriendRequestedDatabase.keepSynced(true);
        mFriendRequestedDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(requestedFriend_ID)) {
                    try{
                        String current_Status = dataSnapshot.child(requestedFriend_ID).child("request_status").getValue().toString();
                        int status = Integer.parseInt(current_Status);
                        Toast.makeText(context, "Estado actual de la solicitud = " + current_Status, Toast.LENGTH_LONG).show();
                        userProfile.changeRequestState(status);
                        switch(status){
                            case 0:
                                friendReqButton.setText("send request");
                                friendReqButton.setBackgroundColor(Color.parseColor("#ff33b5e5"));
                                break;
                            case 1:
                                friendReqButton.setText("cancel request");
                                friendReqButton.setBackgroundColor(Color.RED);
                                break;
                            case 2:
                                friendReqButton.setText("Eliminate User");
                                friendReqButton.setBackgroundColor(Color.RED);
                                messageButton.setVisibility(View.VISIBLE);
                                break;
                            case 3:
                                friendReqButton.setText("Decline Request");
                                friendReqButton.setBackgroundColor(Color.RED);
                                break;
                            default:
                                Toast.makeText(context, "Un error ha ocurrido al intentar acceder al estado actual de la solicitud. Se ha accedido al estado por defecto", Toast.LENGTH_LONG).show();
                                System.out.println("*******Un error ha ocurrido al intentar acceder al estado actual de la solicitud ERROR: ");
                        }
                    }catch(NullPointerException nullException){
                        userProfile.changeRequestState(0);
                    }catch(Exception e){
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG ).show();
                        System.out.println("***** ERROR when retriving Friend Request Status: " + e.getMessage());
                    }
                }else{
                    System.out.println("********Friend Request Status Unable to get DatabaseReference");
                    userProfile.changeRequestState(0);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void eliminateUserRequest(String requestedUser, String requesterUser, final Context context){

        Map requestMap = new HashMap();
        requestMap.put("Users_requests/" + requestedUser + "/" + requesterUser, null);
        requestMap.put("Users_requests/" + requesterUser + "/" + requestedUser, null);
        requestMap.put("Requested_Users/" + requestedUser + "/" + requesterUser, null);
        mRoofRef.updateChildren(requestMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError != null){
                    Exception e = databaseError.toException();
                    Toast.makeText(context, "Se ha producido un error al momento de procesar la declinación de la solicitud, ERROR CODE: " + e, Toast.LENGTH_SHORT).show();
                    System.out.println("**********Se ha producido un error al momento de procesar la declinación de la solicitud, ERROR CODE: " + e);
                }
            }
        });
    }

    public void acceptUserRequest(final String requesterUserID, final Context context){
        final DatabaseReference requesterUserInfo_Reference;
        final DatabaseReference requestStatus_Reference;
        final DatabaseReference usersRelationships_Reference;
        final DatabaseReference requestStatus_accept_Reference;

        requesterUserInfo_Reference = FirebaseDatabase.getInstance().getReference().child("Requested_Users").child(getUserID()).child(requesterUserID);
        requestStatus_Reference = FirebaseDatabase.getInstance().getReference().child("Users_requests").child(requesterUserID).child(getUserID()).child("request_status");
        requestStatus_accept_Reference = FirebaseDatabase.getInstance().getReference().child("Users_requests").child(getUserID()).child(requesterUserID).child("request_status");
        usersRelationships_Reference = FirebaseDatabase.getInstance().getReference().child("Users_Relationships");

        //Removiendo el usuario solocitante de la Tabla de Usuarios Solicitantes
        requesterUserInfo_Reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    //Cambiando el estado de la solicitud de "amistad" a Amigos
                    requestStatus_Reference.setValue(2).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                //Cambiando el estado de la solicitud de "amistad" a Amigos del usuario solicitado
                                requestStatus_accept_Reference.setValue(2).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        System.out.println("Se ha cambiado el estado de la solicitud de manera EXITOSA");
                                        usersRelationships_Reference.child(getUserID()).child(requesterUserID).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                    usersRelationships_Reference.child(requesterUserID).child(getUserID()).setValue(true).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if(task.isSuccessful()){
                                                                Toast.makeText(context, "The Relationship between the requested user and you was stablish SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                                                            }else{
                                                                Exception UserRelationshipERROR = task.getException();
                                                                System.out.println("*****************************¨*Han errro has ocurred during the cration of the REQUESTED user node creation. ERROR CODE: " + UserRelationshipERROR);
                                                            }
                                                        }
                                                    });
                                                }else{
                                                    Exception UserRelationshipERROR = task.getException();
                                                    System.out.println("*****************************¨*Han errro has ocurred during the cration of the REQUESTER user node creation. ERROR CODE: " + UserRelationshipERROR);
                                                }
                                            }
                                        });
                                    }
                                });
                            }else{
                                Exception ChangeStatusError = task.getException();
                                System.out.println("Se ha producido un error durante el cambio de estado en la solicitud. ERROR CODE: " + ChangeStatusError);
                            }
                        }
                    });
                }else{
                    Exception AcceptUserRequestERROR = task.getException();
                    System.out.println("" + AcceptUserRequestERROR);
                    Toast.makeText(context, "Se ha producido un error al aceptar la solcitud de amistad. Procesos de borrado del Muro petitorio. ERROR code: " + AcceptUserRequestERROR, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
