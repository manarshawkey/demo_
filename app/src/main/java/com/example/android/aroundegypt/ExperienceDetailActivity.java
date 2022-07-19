package com.example.android.aroundegypt;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.aroundegypt.Data.Database.ExperienceEntry;
import com.squareup.picasso.Picasso;

public class ExperienceDetailActivity extends AppCompatActivity {

    TextView mTextView_experienceName;
    TextView mTextView_experienceLocation;
    TextView mTextView_experienceDetailedDesc;
    ImageView imageView_experiencePhoto;
    TextView mTextView_likesNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_detail);

        ExperienceEntry currentExperience = (ExperienceEntry) getIntent()
                .getSerializableExtra(MainActivity.SERIALIZABLE_EXPERIENCE_ENTRY);

        mTextView_experienceName = findViewById(R.id.textView_experienceName);
        mTextView_experienceDetailedDesc = findViewById(R.id.textView_descriptionDetails);
        mTextView_likesNo = findViewById(R.id.textView_likesNo);


        mTextView_experienceName.setText(currentExperience.getTitle());
        mTextView_experienceDetailedDesc.setText(currentExperience.getDescription());
        mTextView_likesNo.setText(String.valueOf(currentExperience.getLikes_no()));

        imageView_experiencePhoto = findViewById(R.id.imageView_experiencePhoto);

        Picasso.with(this).load(currentExperience.getCover_photo_url())
                .into(imageView_experiencePhoto);
    }
}