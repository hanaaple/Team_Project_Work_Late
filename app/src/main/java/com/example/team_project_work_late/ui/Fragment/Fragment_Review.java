package com.example.team_project_work_late.ui.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.adapter.ReviewAdapter;
import com.example.team_project_work_late.model.ReviewItem;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Fragment_Review extends Fragment {

    private RecyclerView mRView;
    private Button mBtn_write;
    private ReviewAdapter mRAdapter;
    private ArrayList<ReviewItem> mRList;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
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
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        String itemName = getArguments().getString("ItemName");
        LoadReview(itemName, view);


        mBtn_write.setOnClickListener(v -> {
            mRef.child("Review").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Boolean isAlreadyReviewed = false;
                    for (DataSnapshot data : task.getResult().child(itemName).getChildren()) {
                        if (data.getKey().equals(mAuth.getUid())) {
                            Toast.makeText(getContext(), "이미 리뷰를 하였습니다.", Toast.LENGTH_SHORT).show();
                            isAlreadyReviewed = true;
                            break;
                        }
                    }
                    if (!isAlreadyReviewed) {
                        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext(), R.style.TransparentBottomSheetDialogTheme);
                        bottomSheetDialog.setContentView(R.layout.dialog_review);
                        bottomSheetDialog.show();
                        ReviewItem reviewItem = new ReviewItem();
                        RatingBar ratingBar = bottomSheetDialog.findViewById(R.id.write_ratingbar);
                        ratingBar.setOnRatingBarChangeListener((RatingBar ratingBar1, float rating, boolean fromUser) -> {
                            reviewItem.setRating((int) rating);
                        });
                        Button cancelButton = bottomSheetDialog.findViewById(R.id.cancelButton);
                        cancelButton.setOnClickListener(v1 -> {
                            bottomSheetDialog.dismiss();
                        });
                        Button saveButton = bottomSheetDialog.findViewById(R.id.SaveButton);
                        saveButton.setOnClickListener(v1 -> {
                            EditText editText = bottomSheetDialog.findViewById(R.id.InputText);
                            if (mAuth != null) reviewItem.setUID(mAuth.getUid());
                            reviewItem.setUserName(mAuth.getCurrentUser().getDisplayName());
                            reviewItem.setContents(editText.getText().toString());
                            reviewItem.setPhotoURL(mAuth.getCurrentUser().getPhotoUrl().toString());
                            if (mAuth == null) {
                                Log.e("파이어베이스", "연동 오류");
                            } else {
                                mRef.child("Review").child(itemName).child(mAuth.getUid()).child("닉네임").setValue(reviewItem.getUserName());
                                mRef.child("Review").child(itemName).child(mAuth.getUid()).child("내용").setValue(reviewItem.getContents());
                                mRef.child("Review").child(itemName).child(mAuth.getUid()).child("평점").setValue(reviewItem.getRating());
                                mRef.child("Review").child(itemName).child(mAuth.getUid()).child("사진 URL").setValue(reviewItem.getPhotoURL());
                                mRAdapter.addItem(reviewItem);
                                mRView.smoothScrollToPosition(0);
                                bottomSheetDialog.dismiss();
                            }
                        });
                    }
                }
            });
        });
        return view;
    }

    private void LoadReview(String itemName, View view) {
        if (mRAdapter == null) {
            mRList = new ArrayList<ReviewItem>();
            mRAdapter = new ReviewAdapter(mRList, view, itemName);
            mRView.setHasFixedSize(true);
            mRView.setAdapter(mRAdapter);
        }
        mRef.child("Review").get().addOnCompleteListener(
                task -> {
                    if (task.isSuccessful()) {
                        mRAdapter.InitialCount();
                        for (DataSnapshot data : task.getResult().child(itemName).getChildren()) {
                            ReviewItem reviewItem = new ReviewItem();
                            reviewItem.setUID(data.getKey());
                            reviewItem.setUserName(data.child("닉네임").getValue().toString());
                            reviewItem.setContents(data.child("내용").getValue().toString());
                            reviewItem.setRating(Integer.parseInt(data.child("평점").getValue().toString()));
                            reviewItem.setPhotoURL(data.child("사진 URL").getValue().toString());
                            mRAdapter.addItem(reviewItem);
                            mRView.smoothScrollToPosition(0);
                        }
                    }
                });
    }
}