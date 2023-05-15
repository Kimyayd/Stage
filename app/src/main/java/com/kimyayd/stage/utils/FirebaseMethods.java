package com.kimyayd.stage.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kimyayd.stage.FilePaths;
import com.kimyayd.stage.principal.MainActivity;
import com.kimyayd.stage.models.Event;
import com.kimyayd.stage.models.Organisation;
import com.kimyayd.stage.models.Participant;
import com.kimyayd.stage.models.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class FirebaseMethods {
    private static final String TAG = "FirebaseMethods";

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private StorageReference mStorageReference;
    private String userID;

    //vars
    private Context mContext;
    private double mPhotoUploadProgress = 0;
    public FirebaseMethods(Context context) {
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mContext = context;

        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
    }
    public void registerNewEmail(final String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) {
                            Toast.makeText(mContext, "Authentification failed",
                                    Toast.LENGTH_SHORT).show();


                        }
                        else if(task.isSuccessful()){
                            //send verificaton email
                            sendVerificationEmail();

                            userID = mAuth.getCurrentUser().getUid();
                            Log.d(TAG, "onComplete: Authstate changed: " + userID);
                        }

                    }
                });
    }
    public void addNewUser(String email, String fullname,String type){

        User user = new User( userID,  email,  fullname,getTimestamp(), type);

        myRef.child("users")
                .child(userID)
                .setValue(user);
        /*
        String newPostKey1 = myRef.child("categories").push().getKey();
        String newPostKey2 = myRef.child("categories").push().getKey();
        String newPostKey3 = myRef.child("categories").push().getKey();
        String newPostKey4 = myRef.child("categories").push().getKey();
        String newPostKey5 = myRef.child("categories").push().getKey();
        String newPostKey6 = myRef.child("categories").push().getKey();
        String newPostKey7 = myRef.child("categories").push().getKey();
        String newPostKey8 = myRef.child("categories").push().getKey();
        String newPostKey9 = myRef.child("categories").push().getKey();
        String newPostKey10 = myRef.child("categories").push().getKey();
        String newPostKey11 = myRef.child("categories").push().getKey();
        Category category1 = new Category( newPostKey1,  "type_user","","participant");
        Category category2 = new Category( newPostKey2,  "type_user",  "","organisation");
        Category category3 = new Category( newPostKey3,  "evaluation",  "","0");
        Category category4 = new Category( newPostKey4,  "evaluation",  "","1");
        Category category5 = new Category( newPostKey5,  "evaluation",  "","2");
        Category category6 = new Category( newPostKey6,  "evaluation",  "","3");
        Category category7 = new Category( newPostKey7,  "evaluation",  "","4");
        Category category8 = new Category( newPostKey8,  "evaluation",  "","5");
        Category category9 = new Category( newPostKey9,  "type_file",  "","audio");
        Category category10 = new Category( newPostKey10,  "type_file",  "","photo");
        Category category11 = new Category( newPostKey11,  "type_file",  "","video");

            myRef.child("categories")
                    .child(newPostKey1)
                    .setValue(category1);
        myRef.child("categories")
                .child(newPostKey2)
                .setValue(category2); myRef.child("categories")
                .child(newPostKey3)
                .setValue(category3); myRef.child("categories")
                .child(newPostKey4)
                .setValue(category4); myRef.child("categories")
                .child(newPostKey5)
                .setValue(category5); myRef.child("categories")
                .child(newPostKey6)
                .setValue(category6); myRef.child("categories")
                .child(newPostKey7)
                .setValue(category7); myRef.child("categories")
                .child(newPostKey8)
                .setValue(category8); myRef.child("categories")
                .child(newPostKey9)
                .setValue(category9); myRef.child("categories")
                .child(newPostKey10)
                .setValue(category10); myRef.child("categories")
                .child(newPostKey11)
                .setValue(category11); myRef.child("categories");
*/

        List<Organisation> organisations = null;
        Participant participant = new Participant(email,fullname,"unspecified","unspecified","unspecified","default",organisations);

        myRef.child("participants")
                .child(userID)
                .setValue(participant);
    }
    private String getTimestamp(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.FRENCH);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        return sdf.format(new Date());
    }
    public void addNewUserCompany(String email, String fullname,  String type){

        User user = new User( userID,  email,  fullname,getTimestamp() , type);

        myRef.child("users")
                .child(userID)
                .setValue(user);
        List<Participant> participants = null;
        Organisation organisation = new Organisation(userID,email,fullname,"","",0,"","","","",participants);

        myRef.child("organisations")
                .child(userID)
                .setValue(organisation);

    }
    public void sendVerificationEmail(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(mContext, "Check your email box", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(mContext, "Couldn't send verification email.", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }

//    public void uploadNewPhoto(String photoType,String title,String place, String time, String date , String about ,final int count, final String imgUrl,
//                               Bitmap bm) {
//        Log.d(TAG, "uploadNewPhoto: attempting to uplaod new photo.");
//
//        FilePaths filePaths = new FilePaths();
//        //case1) new photo
//        if (photoType.equals("new_photo")) {
//            Log.d(TAG, "uploadNewPhoto: uploading NEW photo.");
//            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            StorageReference storageReference = mStorageReference
//                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/photo" + (count + 1));
//            if(bm==null){
//                bm = ImageManager.getBitmap(imgUrl);
//            }
//
//            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);
//
//            UploadTask uploadTask = null;
//            uploadTask = storageReference.putBytes(bytes);
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();
//                    if (taskSnapshot.getMetadata() != null) {
//                        if (taskSnapshot.getMetadata().getReference() != null) {
//                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
//                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    Toast.makeText(mContext, "photo upload success", Toast.LENGTH_SHORT).show();
//                                    addPhotoToDatabase(caption, uri.toString());
//
//
//                                    //createNewPost(imageUrl);
//                                }
//                            });
//                        }
//                    }
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d(TAG, "onFailure: Photo upload failed.");
//                    Toast.makeText(mContext, "Create event failed ", Toast.LENGTH_SHORT).show();
//                    mContext.startActivity(new Intent(mContext, MainActivity.class));
//
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = 100 + taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();
//                    if (progress - 15 > mPhotoUploadProgress) {
//                        Toast.makeText(mContext, "Create event progress", Toast.LENGTH_SHORT).show();
//                        mPhotoUploadProgress = progress;
//                        mContext.startActivity(new Intent(mContext, MainActivity.class));
//
//                    }
//                }
//            });
//
//
//        }
//        else if(photoType.equals("profile_photo")){
//            Log.d(TAG,"uploadNewPhoto: uploading new PROFILE photo");
//            String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//            StorageReference storageReference = mStorageReference
//                    .child(filePaths.FIREBASE_IMAGE_STORAGE + "/" + user_id + "/profile_photo");
//
//            if(bm==null){
//                bm = ImageManager.getBitmap(imgUrl);
//            }
//
//            byte[] bytes = ImageManager.getBytesFromBitmap(bm, 100);
//
//            UploadTask uploadTask = null;
//            uploadTask = storageReference.putBytes(bytes);
//
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                    Uri firebaseUrl = taskSnapshot.getDownloadUrl();
//                    if (taskSnapshot.getMetadata() != null) {
//                        if (taskSnapshot.getMetadata().getReference() != null) {
//                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
//                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    Toast.makeText(mContext, "photo upload success", Toast.LENGTH_SHORT).show();
//                                    setProfilePhoto(uri.toString());
////                                    ((AccountSettingsActivity)mContext).setViewPager(
////                                            ((AccountSettingsActivity)mContext).pagerAdapter
////                                                    .getFragmentNumber(mContext.getString(R.string.edit_profile_fragment))
////                                    );
//
//                                }
//                            });
//                        }
//                    }
//
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Log.d(TAG, "onFailure: Photo upload failed.");
//                    Toast.makeText(mContext, "Photo upload failed ", Toast.LENGTH_SHORT).show();
//                    mContext.startActivity(new Intent(mContext, MainActivity.class));
//
//                }
//            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                    double progress = 100 + taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount();
//                    if (progress - 15 > mPhotoUploadProgress) {
//                        Toast.makeText(mContext, "Photo upload progress", Toast.LENGTH_SHORT).show();
//                        mPhotoUploadProgress = progress;
//                        mContext.startActivity(new Intent(mContext, MainActivity.class));
//                    }
//                }
//            });
//
//        }
//    }
//    private void createEvent(String url,String title,String place, String time, String date , String about){
//        Log.d(TAG, "addPhotoToDatabase: adding photo to database.");
//
//        String newPostKey = myRef.child("user_posts").push().getKey();
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("caption", caption);
//        hashMap.put("date_created", getTimestamp());
//        hashMap.put("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
//        hashMap.put("post_id", newPostKey);
//        hashMap.put("post_path", url);
//        hashMap.put("post_type", "1");
//
//        Event event= new Event(newPostKey,title,about,date,place,url,null,null,null,null);
//        myRef.child("user_events")
//                .child(userID)
//                .setValue(event);
//        myRef.child("events")
//                .child(userID)
//                .setValue(event);
//        userID=FirebaseAuth.getInstance().getCurrentUser()
//                .getUid();
//
//        myRef.child("user_photos")
//                .child(userID)
//                .child("photo")
//                .child(newPostKey)
//                .setValue(hashMap);
//        myRef.child("user_posts")
//                .child(userID)
//                .child("post")
//                .child(newPostKey)
//                .setValue(hashMap);
//    }
  private void addPhotoToDatabase(String caption, String url){
    Log.d(TAG, "addPhotoToDatabase: adding photo to database.");

        String newPostKey = myRef.child("user_posts").push().getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("caption", caption);
        hashMap.put("date_created", getTimestamp());
        hashMap.put("user_id", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("post_id", newPostKey);
        hashMap.put("post_path", url);
        hashMap.put("post_type", "1");
        userID=FirebaseAuth.getInstance().getCurrentUser()
                .getUid();

        myRef.child("user_photos")
                .child(userID)
                .child("photo")
                .child(newPostKey)
                .setValue(hashMap);
        myRef.child("user_posts")
                .child(userID)
                .child("post")
                .child(newPostKey)
                .setValue(hashMap);
    }
    private void setProfilePhoto(String url){
        Log.d(TAG, "setProfilePhoto: setting new profile image: " + url);

        myRef.child("user")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("profile_photo")
                .setValue(url);
    }


}
