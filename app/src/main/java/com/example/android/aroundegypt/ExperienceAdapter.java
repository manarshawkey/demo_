package com.example.android.aroundegypt;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.aroundegypt.Data.Database.ExperienceEntry;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.ExperienceViewHolder> {


    public interface ListItemClickListener{
        void onListItemClick(ExperienceEntry clickedExperience);
    }

    private static final String  LOG_TAG = ExperienceAdapter.class.getSimpleName();
    private List<ExperienceEntry> experiences;
    private final ListItemClickListener onClickListener;
    private Context context;

    public void setContext(Context context){ this.context = context;}
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
        //int listItemLayoutId = R.layout.experience_list_item;
        int listItemLayoutId = R.layout.experience_list_item_cardview; //added

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
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return experiences.size();
    }

    class ExperienceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView experienceName;
        TextView likesNo;
        TextView viewsNo;
        ImageView imageView;
        public ExperienceViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            experienceName = itemView.findViewById(R.id.cardView_experienceName);
            experienceName.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
            likesNo = itemView.findViewById(R.id.tv_cardView_likesNo);
            //viewsNo = itemView.findViewById(R.id.textView_viewsNo);

            viewsNo = itemView.findViewById(R.id.tv_cardView_viewsNo);
            imageView = itemView.findViewById(R.id.cardViewImg_experiencePhoto);

        }


         @Override
         public void onClick(View view) {
             ExperienceEntry entry = experiences.get(getAbsoluteAdapterPosition());
             Log.d(LOG_TAG, "onViewClick");
             onClickListener.onListItemClick(entry);
         }
     }


}
