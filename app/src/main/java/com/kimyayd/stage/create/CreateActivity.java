package com.kimyayd.stage.create;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kimyayd.stage.AddActivity;
import com.kimyayd.stage.R;
import com.kimyayd.stage.utils.FirebaseMethods;
import com.kimyayd.stage.utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class CreateActivity extends AppCompatActivity {
    private ImageView image;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private int imageCount = 1;
    private FirebaseMethods mFirebaseMethods;
    private Intent intent;
    private Context mContext;

    private AppCompatButton add_photo , create_event;
    private EditText title , about , place , time ,date ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_create);
        mContext = CreateActivity.this;
        mFirebaseMethods = new FirebaseMethods(CreateActivity.this);
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(CreateActivity.this));
        Intent intent1 = getIntent();
        String eventName=intent1.getStringExtra("event_name");
        setImage();
        setupFirebaseAuth();

        image=findViewById(R.id.image);
        add_photo=findViewById(R.id.add_photo);
        create_event=findViewById(R.id.create_event);
        title=findViewById(R.id.event_title);
        place=findViewById(R.id.event_place);
        time=findViewById(R.id.event_time);
        date=findViewById(R.id.event_date);
        about=findViewById(R.id.event_about);
        title.setText(eventName);
        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });
        create_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String event_title = title.getText().toString();
                String event_place = place.getText().toString();
                String event_time = time.getText().toString();
                String event_date = date.getText().toString();
                String event_about = about.getText().toString();
                if(checkInputs(event_title,event_place,event_time,event_date,event_about)){
                    if(verifyImage()){

                    }else{
                        Toast.makeText(mContext, "Select an image first!", Toast.LENGTH_SHORT).show();
                    }
                }
                if(intent.hasExtra("selected_image")){

                    String imgUrl = intent.getStringExtra("selected_image");
                    mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo), caption, imageCount, imgUrl,null);

                }
                else if(intent.hasExtra("selected_bitmap")){
                    Bitmap bitmap =intent.getParcelableExtra("selected_bitmap");
                    mFirebaseMethods.uploadNewPhoto(getString(R.string.new_photo), caption, imageCount, null,bitmap);
                }
            }
        });
    }
    private boolean checkInputs(String event_tile, String event_place, String event_time,String event_date, String event_about){

        if(event_tile.equals("") || event_place.equals("") || event_time.equals("")||event_date.equals("") || event_about.equals("")){
            Toast.makeText(mContext, "All fields must be filled out.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void setImage(){
        Intent intent = getIntent();
        ImageView image = findViewById(R.id.image);

        if(intent.hasExtra("selected_image")){
            String imgUrl = intent.getStringExtra("selected_image");
            String mAppend = "file:/";
            UniversalImageLoader.setImage(imgUrl, image, null, mAppend);
        }
        else if(intent.hasExtra("selected_bitmap")){
            Bitmap bitmap = intent.getParcelableExtra("selected_bitmap");
            image.setImageBitmap(bitmap);
        }
    }
    private boolean verifyImage(){
        Intent intent = getIntent();
        if(intent.hasExtra("selected_image")){
            String imgUrl = intent.getStringExtra("selected_image");
            if(imgUrl.equals("")){
                return false;
            }
        }
        else if(intent.hasExtra("selected_bitmap")){
            Bitmap bitmap = intent.getParcelableExtra("selected_bitmap");
           if(bitmap == null) {
               return false;
           }
        }
        return true;
    }
    private void setupFirebaseAuth(){
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
