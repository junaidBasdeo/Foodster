package com.example.junaid.foodster;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.junaid.foodster.Globals.ApplicationGlobals;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junaid on 11/8/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<ReviewItems> reviewItems;
    private Context context;
    ArrayList<Business> businesses = new ArrayList<>();
    private ApplicationGlobals ag = ApplicationGlobals.getInstance();



    public ReviewAdapter(List<ReviewItems> reviewItems, Context context) {
        this.reviewItems = reviewItems;
        this.context = context;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_detail,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        ReviewItems reviewItem = reviewItems.get(position);
        holder.reviewername.setText(reviewItem.getReviewNameData().getName());
        holder.reviewtext.setText(reviewItem.getReviewText());
        holder.reviewdate.setText(reviewItem.getReviewDate());
        holder.rating.setText("User Stars : " + reviewItem.getRating());




    }

    @Override
    public int getItemCount() {
        return reviewItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{

        public TextView reviewtext;
        public TextView reviewdate;
        public TextView reviewername;
        public TextView rating;

        public ViewHolder(View itemView) {
            super(itemView);

            reviewtext = itemView.findViewById(R.id.reviewtext);
            reviewdate= itemView.findViewById(R.id.reviewdate);
            reviewername = itemView.findViewById(R.id.reviewername);
            rating= itemView.findViewById(R.id.rating);

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            Business currentBusinessToReview = ag.getbusinesses().get(ag.getbusinessClicked());


            Intent i = new Intent(context, LocalReviewActivity.class);
            context.startActivity(i);

        }

        @Override
        public boolean onLongClick(View v) {
            return true;
        }

    }
}
