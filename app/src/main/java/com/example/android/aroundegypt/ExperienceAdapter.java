package com.example.android.aroundegypt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.aroundegypt.Data.ExperienceEntry;

import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {


    private List<ExperienceEntry> experiences;

    public void setExperiencesList(List<ExperienceEntry> experiences){
        this.experiences = experiences;
    }
    @NonNull
    @Override
    public ExperienceAdapter.ExperienceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int listItemLayoutId = R.layout.experience_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachToParentImmediately = false;
        View view = inflater.inflate(listItemLayoutId, parent, attachToParentImmediately);


        return new ExperienceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceAdapter.ExperienceViewHolder holder, int position) {
        ExperienceEntry currentExperience = experiences.get(position);
        holder.experienceName.setText(currentExperience.getTitle());
        holder.likesNo.setText(String.valueOf(currentExperience.getLikes_no()));
        //holder.viewsNo.setText(String.valueOf(currentExperience.getViews_no()));
    }

   // @Override
    //public void onBindViewHolder(@NonNull ExperienceAdapter.ExperienceViewHolder holder, int position, @NonNull List<Object> payloads) {
      //  super.onBindViewHolder(holder, position, payloads);
    //}

    @Override
    public int getItemCount() {
        return experiences.size();
    }
     static class ExperienceViewHolder extends RecyclerView.ViewHolder{

        TextView experienceName;
        TextView likesNo;
        TextView viewsNo;
        public ExperienceViewHolder(@NonNull View itemView) {
            super(itemView);
            experienceName = itemView.findViewById(R.id.textView_experienceName);
            likesNo = itemView.findViewById(R.id.textView_likesNo);
            //viewsNo = itemView.findViewById(R.id.textView_viewsNo);
        }
    }
}
