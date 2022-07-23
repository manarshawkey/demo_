package com.example.android.aroundegypt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.aroundegypt.Data.AppExecutors;
import com.example.android.aroundegypt.Data.Database.AppDatabase;
import com.example.android.aroundegypt.Data.Database.ExperienceEntry;
import com.example.android.aroundegypt.Data.Database.LikedExperienceEntry;
import com.example.android.aroundegypt.Data.Network.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class ExperienceDetailActivity extends AppCompatActivity {

    TextView mTextView_experienceName;
    TextView mTextView_experienceLocation;
    TextView mTextView_experienceDetailedDesc;
    ImageView imageView_experiencePhoto;
    ImageView mImageView_like;
    TextView mTextView_likesNo;
    TextView mTextView_viewsNo;

    MutableLiveData<Integer> likesCount = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experience_detail);

        ExperienceEntry currentExperience = (ExperienceEntry) getIntent()
                .getSerializableExtra(MainActivity.SERIALIZABLE_EXPERIENCE_ENTRY);

        populateViews(currentExperience);
    }

    private void populateViews(ExperienceEntry currentExperience) {
        mTextView_experienceName = findViewById(R.id.textView_experienceName);
        mTextView_experienceDetailedDesc = findViewById(R.id.textView_descriptionDetails);
        mTextView_likesNo = findViewById(R.id.detail_textView_likesNo);
        mTextView_experienceLocation = findViewById(R.id.textView_location);
        mTextView_viewsNo = findViewById(R.id.detail_cardView_viewsNo);
        mImageView_like = findViewById(R.id.detail_imageView_like);


        mTextView_experienceName.setText(currentExperience.getTitle());
        mTextView_experienceDetailedDesc.setText(currentExperience.getDescription());
        mTextView_likesNo.setText(String.valueOf(currentExperience.getLikes_no()));
        mTextView_experienceLocation.setText(currentExperience.getCity() + ", " + "Egypt");
        imageView_experiencePhoto = findViewById(R.id.imageView_experiencePhoto);
        mTextView_viewsNo.setText(String.valueOf(currentExperience.getViews_no()));

        if(currentExperience.getLikedStatus()){
            mImageView_like.setImageDrawable(AppCompatResources.getDrawable(
                    this, R.drawable.ic_liked_heart));
        }else{
            mImageView_like.setImageDrawable(AppCompatResources.getDrawable(this,
                    R.drawable.ic_heart));
        }

        likesCount = new MutableLiveData<>();

        likesCount.observe(this, integer -> {
            Toast.makeText(ExperienceDetailActivity.this, "live data changed", Toast.LENGTH_SHORT).show();
            mTextView_likesNo.setText(String.valueOf(integer));
            mImageView_like.setImageDrawable(AppCompatResources.getDrawable(
                    ExperienceDetailActivity.this, R.drawable.ic_liked_heart));
        });

        configureLikeActionOnLikePressed(currentExperience);

        Picasso.with(this).load(currentExperience.getCover_photo_url())
                .into(imageView_experiencePhoto);
    }

    private void configureLikeActionOnLikePressed(ExperienceEntry currentExperience) {
        mImageView_like.setOnClickListener(view -> {

            AppExecutors.getInstance().getNetworkIO().execute(() -> {
                try {
                    AppDatabase db = AppDatabase.getInstance(ExperienceDetailActivity.this);
                    List<LikedExperienceEntry> likedExperiences = db.likedExperienceDAO().getLikedExperiences();
                    if(!currentExperienceIsLiked(likedExperiences, currentExperience.getId())){
                        int responseCode = NetworkUtils.likeAnExperience(currentExperience.getId());
                        Log.d("response", responseCode + " ");
                        if(responseCode == 200) {
                            ExperienceEntry updatedExperience = NetworkUtils.getSingleExperience(
                                    ExperienceDetailActivity.this, currentExperience.getId()
                            );
                            db.experienceDAO().insert(updatedExperience);
                            db.likedExperienceDAO().insert(new LikedExperienceEntry(currentExperience.getId()));
                            int likes = db.experienceDAO().getSingleExperience(currentExperience.getId()).getLikes_no();
                            likesCount.postValue(likes);
                            currentExperience.setLikes_no(likes);
                            currentExperience.setLikedStatus(true);
                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private boolean currentExperienceIsLiked(List<LikedExperienceEntry> likedExperiences, String id) {
        for(LikedExperienceEntry likedExperience : likedExperiences){
            if(likedExperience.getExperienceID().equals(id))
                return true;
        }
        return false;
    }
}