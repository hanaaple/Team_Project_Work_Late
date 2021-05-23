package com.example.team_project_work_late.ui.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Rating;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.adapter.ReviewAdapter;
import com.example.team_project_work_late.model.ReviewItem;
import com.example.team_project_work_late.ui.Activity.LoginActivity;
import com.example.team_project_work_late.ui.Activity.MainActivity;
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
    private FirebaseAuth mAuth;
    ConstraintLayout dialogView;
    Button logoButton;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        mBtn_write = (Button) view.findViewById(R.id.write_review);
        mRView = (RecyclerView) view.findViewById(R.id.review_list);
        logoButton = view.findViewById(R.id.logoButton);
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

//mRef.child("Review").child("Archive").child("번호1").child(mAuth.getUid()).false or 내용
        mBtn_write.setOnClickListener(v -> {
            mRef.child("Review").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Boolean isAlreadyReviewed = false;
                    Loop1:
                    for (DataSnapshot data : task.getResult().child("Archive").getChildren()) {
                        //if()
                        for (DataSnapshot userData : data.getChildren()) {
                            if (userData.getKey().equals(mAuth.getUid())) {
                                Toast.makeText(getContext(), "이미 리뷰 하심", Toast.LENGTH_SHORT).show();
                                isAlreadyReviewed = true;
                                break Loop1;
                            }
                        }
                    }
                    if (!isAlreadyReviewed) {
                        dialogView = (ConstraintLayout) View.inflate(getContext(), R.layout.dialog_review, null);

                        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                        dialogBuilder.setView(dialogView);
                        AlertDialog alertDialog = dialogBuilder.show();
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        ReviewItem reviewItem = new ReviewItem();
                        RatingBar ratingBar = dialogView.findViewById(R.id.write_ratingbar);
                        ratingBar.setOnRatingBarChangeListener((RatingBar ratingBar1, float rating, boolean fromUser) -> {
                             reviewItem.setRating(rating);
                        });
                        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
                        cancelButton.setOnClickListener(v1 -> {
                            alertDialog.dismiss();
                            dialogView.removeView(dialogView);
                        });
                        Button saveButton = dialogView.findViewById(R.id.SaveButton);
                        saveButton.setOnClickListener(v1 -> {
                            //내용, 평점 찾고 파이어베이스에 입력 및 추가
                            EditText editText = dialogView.findViewById(R.id.InputText);
                            Toast.makeText(getContext(), editText.getText().toString(), Toast.LENGTH_SHORT).show();
                            if (mAuth != null) {
                                reviewItem.setUID(mAuth.getUid());
                            }
                            reviewItem.setUserName(mAuth.getCurrentUser().getDisplayName());
//                            reviewItem.setRating(5);
                            reviewItem.setContents(editText.getText().toString());


                            if (mAuth == null) {
                                Log.e("파이어베이스", "연동 안됨");
                                //Toast.makeText()
                            } else {
                                mRef.child("Review").child("Archive").child("번호1").child(mAuth.getUid()).child("닉네임").setValue(reviewItem.getUserName());
                                mRef.child("Review").child("Archive").child("번호1").child(mAuth.getUid()).child("내용").setValue(reviewItem.getContents());
                                mRef.child("Review").child("Archive").child("번호1").child(mAuth.getUid()).child("평점").setValue(reviewItem.getRating());
                            }

                            mRAdapter.addItem(reviewItem);
                            mRView.smoothScrollToPosition(0);


                            alertDialog.dismiss();
                            dialogView.removeView(dialogView);
                        });
                    }
                }
            });
        });
        LoadReview();
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
                    for(DataSnapshot data : task.getResult().child("Archive").getChildren()){
                        for(DataSnapshot userData : data.getChildren()){
                            if(!userData.getValue().toString().equals("false")){
                                ReviewItem reviewItem = new ReviewItem();
                                reviewItem.setUID(userData.getKey());
//                                reviewItem.setUID(mAuth.getUid());
                                reviewItem.setUserName(userData.child("닉네임").getValue().toString());
                                reviewItem.setContents(userData.child("내용").getValue().toString());
                                reviewItem.setRating(Float.parseFloat(userData.child("평점").getValue().toString()));
                                mRAdapter.addItem(reviewItem);
                                mRView.smoothScrollToPosition(0);
                            }
                        }
                    }
                }
            }
        });
        //mRef.child("Review").child(mAuth.getUid()).child("Archive").child("번호1")
        //mRef.child("Review").child("Archive").child("번호1").child(mAuth.getUid()).false or 내용
    }
}
