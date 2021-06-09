package com.example.volley_superhero_recyvlerview;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LieuxAdapter extends RecyclerView.Adapter<LieuxAdapter.LieuxViewHolder> {


    private List<Lieux> lieuxList;
    private Context context;

    private static int currentPosition = 20;

    public LieuxAdapter(List<Lieux> lieuxList, Context context) {
        this.lieuxList = lieuxList;
        this.context = context;
    }

    @Override
    public LieuxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_layout_lieu, parent, false);
        return new LieuxViewHolder(v);
    }



    @Override
    public void onBindViewHolder(final LieuxViewHolder holder, final int position) {
        Lieux lieux = lieuxList.get(position);
        holder.textViewName.setText(lieux.getDatasetid());
        holder.textViewRealName.setText(lieux.getRecordid());
        holder.textViewTeam.setText(lieux.getNom_tournage());
        holder.textViewFirstAppearance.setText(lieux.getCoord_x());
        holder.textViewCreatedBy.setText(lieux.getCoord_y());

        // holder.linearLayout.setVisibility(View.GONE);



        // Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

        //toggling visibility
        holder.linearLayout.setVisibility(View.VISIBLE);

        //if the position is equals to the item position which is to be expanded
       /* if (currentPosition == position) {
            //creating an animation
            Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down);

            //toggling visibility
             holder.linearLayout.setVisibility(View.VISIBLE);

            //adding sliding effect
            //holder.linearLayout.startAnimation(slideDown);
        }
        else{holder.linearLayout.setVisibility(View.GONE);
        }*/

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                currentPosition = position;

                //reloding the list
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lieuxList.size();
    }

    class LieuxViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewRealName, textViewTeam, textViewFirstAppearance,
                textViewCreatedBy, textViewPublisher, textViewBio;
        ImageView imageView;
        LinearLayout linearLayout;

        LieuxViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewRealName = (TextView) itemView.findViewById(R.id.textViewRealName);
            textViewTeam = (TextView) itemView.findViewById(R.id.textViewTeam);
            textViewFirstAppearance = (TextView) itemView.findViewById(R.id.textViewFirstAppearance);
            textViewCreatedBy = (TextView) itemView.findViewById(R.id.textViewCreatedBy);


            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }
}