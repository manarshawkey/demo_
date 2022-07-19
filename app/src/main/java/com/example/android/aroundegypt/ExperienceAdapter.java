package com.example.android.aroundegypt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.aroundegypt.Data.Database.ExperienceEntry;

import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {


    public interface ListItemClickListener{
        void onListItemClick(ExperienceEntry clickedExperience);
    }

    private static final String  LOG_TAG = ExperienceAdapter.class.getSimpleName();
    private List<ExperienceEntry> experiences;
    private final ListItemClickListener onClickListener;


    public ExperienceAdapter(ListItemClickListener onListItemClickListener){

        this.onClickListener = onListItemClickListener;
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
    }

    @Override
    public int getItemCount() {
        return experiences.size();
    }

    class ExperienceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView experienceName;
        TextView likesNo;
        TextView viewsNo;
        public ExperienceViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            experienceName = itemView.findViewById(R.id.textView_experienceName);
            likesNo = itemView.findViewById(R.id.textView_likesNo);
            viewsNo = itemView.findViewById(R.id.textView_viewsNo);
        }


         @Override
         public void onClick(View view) {
             ExperienceEntry entry = experiences.get(getAbsoluteAdapterPosition());
             Log.d(LOG_TAG, "onViewClick");
             onClickListener.onListItemClick(entry);
         }
     }


}
