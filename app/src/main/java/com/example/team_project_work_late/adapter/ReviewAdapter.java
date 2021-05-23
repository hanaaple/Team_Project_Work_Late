package com.example.team_project_work_late.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.model.ReviewItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    private ArrayList<ReviewItem> mRlist;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;

    public ReviewAdapter(ArrayList<ReviewItem> mRlist){
        this.mRlist = mRlist;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list,parent,false);
        return new ReviewAdapter.ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        holder.review_uid.setText(mRlist.get(position).getUserName());
        holder.review_contents.setText(mRlist.get(position).getContents());
        holder.ratingBar.setRating(mRlist.get(position).getRating());
        //holder.review_uid.setText(mRlist.get(position).getRating());
        //평점에 따라 별 개수 다르게
    }

    @Override
    public int getItemCount(){ return mRlist.size(); }

    public void addItem(ReviewItem reviewItem) {
        mRlist.add(0, reviewItem);
        notifyItemInserted(0);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView review_uid;
        private TextView review_contents;
        private RatingBar ratingBar;

        public ViewHolder(View view) {
            super(view);
            review_uid = (TextView) view.findViewById(R.id.review_uid);
            review_contents = (TextView) view.findViewById(R.id.review_contents);
            ratingBar = view.findViewById(R.id.ratingBar);
            //사진, 아이디, 별점, 내용
            view.setOnClickListener(v -> {
                mAuth = FirebaseAuth.getInstance();
                mRef = FirebaseDatabase.getInstance().getReference();
                if (mAuth == null) {
                    Log.e("파이어베이스", "연동안됨");
                } else {
                    //자신의 리뷰만 삭제 가능하도록
                    if (mRlist.get(getAdapterPosition()).getUID().equals(mAuth.getUid())) {
                        mRef.child("Review").child("Archive").child("번호1").child(mAuth.getUid()).setValue(null);

                        mRlist.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                }
            });
        }
    }
}
