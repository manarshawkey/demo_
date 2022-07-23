package com.example.android.aroundegypt;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.aroundegypt.Data.AppExecutors;
import com.example.android.aroundegypt.Data.Database.AppDatabase;
import com.example.android.aroundegypt.Data.Database.ExperienceEntry;
import com.example.android.aroundegypt.Data.Database.LikedExperienceEntry;
import com.example.android.aroundegypt.Data.Network.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {

    public interface ListItemClickListener{
        void onListItemClick(ExperienceEntry clickedExperience);
    }

    private static final String LOG_TAG = ExperienceAdapter.class.getSimpleName();
    private List<ExperienceEntry> experiences;
    private final ListItemClickListener onClickListener;
    private Context context;
    WeakReference<MainActivity> mainActivityWeakReference;

    public void setMainActivityWeakReference(MainActivity mainActivity){
        mainActivityWeakReference = new WeakReference<>(mainActivity);
    }
    public void setContext(Context context){ this.context = context;}

    public ExperienceAdapter(ListItemClickListener onListItemClickListener,
                             MainActivity mainActivity){

        this.onClickListener = onListItemClickListener;
        mainActivityWeakReference = new WeakReference<>(mainActivity);
    }
    public void setExperiencesList(List<ExperienceEntry> experiences){
        this.experiences = experiences;
    }
    @NonNull
    @Override
    public ExperienceAdapter.ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int listItemLayoutId = R.layout.experience_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(listItemLayoutId, parent, false);
        return new ExperienceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceAdapter.ExperienceViewHolder holder, int position) {
        ExperienceEntry currentExperience = experiences.get(position);
        holder.experienceName.setText(currentExperience.getTitle());
        holder.likesNo.setText(String.valueOf(currentExperience.getLikes_no()));
        holder.viewsNo.setText(String.valueOf(currentExperience.getViews_no()));
        Picasso.with(context).load(currentExperience.getCover_photo_url())
                .into(holder.experiencePhoto);

        if(currentExperience.getRecommended() == MainActivity.EXPERIENCE_TYPE_RECOMMENDED)
            holder.recommendedLayout.setVisibility(View.VISIBLE);
        else
            holder.recommendedLayout.setVisibility(View.GONE);

        setCorrectLikeImage(holder.likeImage, currentExperience);
    }

    private void setCorrectLikeImage(ImageView likeImageView, ExperienceEntry current) {

        if(current.getLikedStatus())
            likeImageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_liked_heart));
        else
            likeImageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_heart));
    }

    @Override
    public int getItemCount() {
        return experiences.size();
    }

    class ExperienceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView experienceName;
        TextView likesNo;
        TextView viewsNo;
        ImageView experiencePhoto;
        ImageView likeImage;
        ConstraintLayout recommendedLayout;

        MutableLiveData<Integer> likesCount = new MutableLiveData<>();

        public ExperienceViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            initViews(itemView);

            likesCount.observe(mainActivityWeakReference.get(), integer -> {
                likeImage.setImageDrawable(AppCompatResources.getDrawable(context,
                        R.drawable.ic_liked_heart));
                likesNo.setText(String.valueOf(integer));
                ExperienceEntry updated = experiences.get(getAbsoluteAdapterPosition());
                updated.setLikedStatus(true);
                updated.setLikes_no(integer);
                experiences.set(getAbsoluteAdapterPosition(), updated);
            });

            likeImage.setOnClickListener(view -> {

                if(experiences.get(getAbsoluteAdapterPosition()).getLikedStatus()){
                    Toast.makeText(context, "Can't unlike an experience :)", Toast.LENGTH_SHORT).show();
                    return;
                }
                String currentExperienceId = experiences.get(getAbsoluteAdapterPosition()).getId();

                //send a Post request to like the experience
                //fetch the updated Experience details from the backend, update
                //the local DB, and trigger the LikeClickedListener with the new data
                AppExecutors.getInstance().getNetworkIO().execute(() -> {
                    try {
                        int responseCode = NetworkUtils.likeAnExperience(currentExperienceId);
                        if(responseCode == 200) {
                            ExperienceEntry updatedExperience =
                                    NetworkUtils.getSingleExperience(context, currentExperienceId);
                            updatedExperience.setLikedStatus(true);
                            AppDatabase db = AppDatabase.getInstance(context);
                            db.likedExperienceDAO().insert(new LikedExperienceEntry(updatedExperience.getId()));
                            db.experienceDAO().insert(updatedExperience);
                            likesCount.postValue(updatedExperience.getLikes_no());
                        }else{
                            Log.d(LOG_TAG, "like an Experience post status code: " + responseCode);
                        }
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                });
            });
        }

        private void initViews(@NonNull View itemView) {
            experienceName = itemView.findViewById(R.id.cardView_experienceName);
            experienceName.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            likesNo = itemView.findViewById(R.id.tv_cardView_likesNo);
            viewsNo = itemView.findViewById(R.id.tv_cardView_viewsNo);
            experiencePhoto = itemView.findViewById(R.id.cardViewImg_experiencePhoto);
            recommendedLayout = itemView.findViewById(R.id.layout_recommended);
            likeImage = itemView.findViewById(R.id.imageView_heart);
        }

        @Override
         public void onClick(View view) {
             ExperienceEntry entry = experiences.get(getAbsoluteAdapterPosition());
             onClickListener.onListItemClick(entry);
         }
    }
}
