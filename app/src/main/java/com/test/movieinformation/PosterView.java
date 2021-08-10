package com.test.movieinformation;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PosterView extends AppCompatActivity {
    //initialize all variables.
    private ImageView IV_poster;
    private Button Btn_posterSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster_view);
        //assign all variables
        IV_poster = findViewById(R.id.IV_poster);
        Btn_posterSave = findViewById(R.id.Btn_posterSave);
        //get poster url and title
        String posterURL = getIntent().getStringExtra("PosterImage");
        String posterTitle = getIntent().getStringExtra("Title");
        /*
        Now, load the image into imageView from url using
        Glide library.
         */
        Glide.with(getApplicationContext())
                .load(posterURL)
                .into(IV_poster);
        //save button click listener
        Btn_posterSave.setOnClickListener(new View.OnClickListener() {
            /**
             * it is used to generate the bitmap from imageView and save the poster into internal storage/gallery.
             * @param v
             */
            @Override
            public void onClick(View v) {
                //save Poster into gallery
                Bitmap bitmap = ((BitmapDrawable) IV_poster.getDrawable()).getBitmap();
                MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, posterTitle, "Title: " + posterTitle);
                //update to user by toast
                Toast.makeText(PosterView.this, "Saved successfully.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}