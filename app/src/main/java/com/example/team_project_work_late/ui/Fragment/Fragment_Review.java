package com.example.team_project_work_late.ui.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.adapter.ReviewAdapter;
import com.example.team_project_work_late.model.ReviewItem;
import com.example.team_project_work_late.ui.Activity.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class Fragment_Review extends Fragment {

    private RecyclerView mRView;
    private Button mBtn_write;
    private ReviewAdapter mRAdapter;
    private ArrayList<ReviewItem> mRList;
    private DatabaseReference mRef;
    FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        mBtn_write = (Button)view.findViewById(R.id.write_review);
        mRView = (RecyclerView) view.findViewById(R.id.review_list);
        if (mAuth == null) {
            mRef = FirebaseDatabase.getInstance().getReference();
            mAuth = FirebaseAuth.getInstance();
        }
        LoadReview();
        mBtn_write.setOnClickListener(v -> {
            ReviewItem reviewItem = new ReviewItem();
            if(mAuth != null){ reviewItem.setUID(mAuth.getUid()); }
            reviewItem.setUserName("123");
            reviewItem.setRating(5);
            reviewItem.setContents("좋아용");


            if (mAuth == null) {
                Log.e("파이어베이스", "연동 안됨");
                //Toast.makeText()
            } else {
                //데이터베이스 상에서 유저의 이름은 모른다.
                mRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1").child("내용").setValue(reviewItem.getContents());
                mRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1").child("평점").setValue(reviewItem.getRating());
            }

            mRAdapter.addItem(reviewItem);
            mRView.smoothScrollToPosition(0);
            });
        return view;
    }

    public void LoadReview(){
        if(mRAdapter == null){
            mRList = new ArrayList<ReviewItem>();
            mRAdapter = new ReviewAdapter(mRList);
            mRView.setHasFixedSize(true);
            mRView.setAdapter(mRAdapter);
        }
        mRef.child("Review").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    //모든 리뷰에 접근
                    for(DataSnapshot data : task.getResult().getChildren()){
                        for(DataSnapshot temp : data.child("Archive").getChildren()) {
                            if (!temp.getValue().toString().equals("false")) {
                                ReviewItem reviewItem = new ReviewItem();
                                reviewItem.setUID(data.getKey());
                                reviewItem.setUserName(reviewItem.getUID());
                                reviewItem.setContents(temp.child("내용").getValue().toString());
                                reviewItem.setRating(Integer.parseInt(temp.child("평점").getValue().toString()));
                                mRAdapter.addItem(reviewItem);
                                mRView.smoothScrollToPosition(0);
                            }
                        }
                        for(DataSnapshot temp : data.child("Lend").getChildren()){

                        }
                    }
                }
            }
        });
        //mRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1")
    }
}
