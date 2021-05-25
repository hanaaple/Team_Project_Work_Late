package com.example.team_project_work_late.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.model.ReviewItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private ArrayList<ReviewItem> mRlist;

    private String bcyclLendNm;
    private int fiveCount;
    private int fourCount;
    private int threeCount;
    private int twoCount;
    private int oneCount;
    private int count;
    private View review_view;
    Bitmap bitmap;

    public ReviewAdapter(ArrayList<ReviewItem> mRlist, View review_view, String bcyclLendNm) {
        this.mRlist = mRlist;
        this.review_view = review_view;
        this.bcyclLendNm = bcyclLendNm;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list, parent, false);
        return new ReviewAdapter.ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, int position) {
        holder.review_uid.setText(mRlist.get(position).getUserName());
        holder.review_contents.setText(mRlist.get(position).getContents());
        holder.ratingBar.setRating(mRlist.get(position).getRating());

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    //현재로그인한 사용자 정보를 통해 PhotoUrl 가져오기
                    URL url = new URL(mRlist.get(position).getPhotoURL());
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (MalformedURLException ee) {
                    ee.printStackTrace();
                } catch (IOException e) {
                }
            }
        };
        mThread.start();
        try {
            mThread.join();
            holder.userImage.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mRlist.size();
    }

    public void addItem(ReviewItem reviewItem) {
        AddCount(reviewItem.getRating());
        mRlist.add(0, reviewItem);
        UpdateRate(review_view);
        notifyItemInserted(0);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView review_uid;
        private TextView review_contents;
        private RatingBar ratingBar;
        private CircleImageView userImage;

        public ViewHolder(View view) {
            super(view);
            review_uid = (TextView) view.findViewById(R.id.review_uid);
            review_contents = (TextView) view.findViewById(R.id.review_contents);
            ratingBar = view.findViewById(R.id.ratingBar);
            userImage = view.findViewById(R.id.userImage);
            //사진, 아이디, 별점, 내용
            view.setOnClickListener(v -> {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                if (mAuth == null) {
                    Log.e("파이어베이스", "연동안됨");
                } else {
                    //자신의 리뷰만 삭제 가능하도록
                    if (mRlist.get(getAdapterPosition()).getUID().equals(mAuth.getUid())) {
                        RemoveCount(mRlist.get(getAdapterPosition()).getRating());
                        UpdateRate(review_view);
                        mRef.child("Review").child(bcyclLendNm).child(mAuth.getUid()).setValue(null);
                        mRlist.remove(getAdapterPosition());
                        notifyItemRemoved(getAdapterPosition());
                    }
                }
            });
        }
    }


    public void InitialCount() {
        fiveCount = 0;
        fourCount = 0;
        threeCount = 0;
        twoCount = 0;
        oneCount = 0;
        count = 0;
    }

    public void UpdateRate(View view) {
        RatingBar ratingbar_average = view.findViewById(R.id.ratingBar_average);
        TextView textView_average = view.findViewById(R.id.average);
        ProgressBar five = view.findViewById(R.id.progressBar5);
        ProgressBar four = view.findViewById(R.id.progressBar4);
        ProgressBar three = view.findViewById(R.id.progressBar3);
        ProgressBar two = view.findViewById(R.id.progressBar2);
        ProgressBar one = view.findViewById(R.id.progressBar1);
        float average = 0;
        if (count != 0)
            average = (fiveCount * 5 + fourCount * 4 + threeCount * 3 + twoCount * 2 + oneCount) / (float) count;
        textView_average.setText(Float.toString(average));
        ratingbar_average.setRating(average);
        five.setMax(count);
        four.setMax(count);
        three.setMax(count);
        two.setMax(count);
        one.setMax(count);
        Thread thread = new Thread(new Runnable() {
            public void run() {
                five.setProgress(fiveCount);
                four.setProgress(fourCount);
                three.setProgress(threeCount);
                two.setProgress(twoCount);
                one.setProgress(oneCount);
            }
        });
        thread.start();
    }

    public void AddCount(int c) {
        count++;
        switch (c) {
            case 5:
                fiveCount++;
                break;
            case 4:
                fourCount++;
                break;
            case 3:
                threeCount++;
                break;
            case 2:
                twoCount++;
                break;
            case 1:
                oneCount++;
                break;
        }
    }

    public void RemoveCount(int c) {
        count--;
        switch (c) {
            case 5:
                fiveCount--;
                break;
            case 4:
                fourCount--;
                break;
            case 3:
                threeCount--;
                break;
            case 2:
                twoCount--;
                break;
            case 1:
                oneCount--;
                break;
        }
    }
}