package com.example.team_project_work_late.ui.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.adapter.BookMarkAdapter;
import com.example.team_project_work_late.application.DBHelper;
import com.example.team_project_work_late.model.BookMarkItem;
import com.example.team_project_work_late.ui.Activity.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_bookMark extends Fragment {

    private RecyclerView mRv_bookMark;
    private FloatingActionButton mBtn_write;
    private ArrayList<BookMarkItem> mBMList;
    private BookMarkAdapter mAdapter;
    private DBHelper mDBHelper;
    private static int index = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_mark, container, false);
        mDBHelper = new DBHelper(getContext());
        mBMList = new ArrayList();
        mRv_bookMark = view.findViewById(R.id.rv_bookMark);
        mBtn_write = view.findViewById(R.id.btn_write);
        loadRecentDB(container);

        mBtn_write.setOnClickListener(v->{
            BookMarkItem bookMarkItem = new BookMarkItem();
            bookMarkItem.setBcyclLendNm(LoginActivity.data.getResponse().getBody().getItems().get(index).getBcyclLendNm());
            bookMarkItem.setBcyclLendSe(LoginActivity.data.getResponse().getBody().getItems().get(index).getBcyclLendSe());
            bookMarkItem.setRdnmadr(LoginActivity.data.getResponse().getBody().getItems().get(index).getRdnmadr());
            bookMarkItem.setLnmadr(LoginActivity.data.getResponse().getBody().getItems().get(index).getLnmadr());
            bookMarkItem.setLatitude(LoginActivity.data.getResponse().getBody().getItems().get(index).getLatitude());
            bookMarkItem.setLongitude(LoginActivity.data.getResponse().getBody().getItems().get(index).getLongitude());
            bookMarkItem.setOperOpenHm(LoginActivity.data.getResponse().getBody().getItems().get(index).getOperOpenHm());
            bookMarkItem.setOperCloseHm(LoginActivity.data.getResponse().getBody().getItems().get(index).getOperCloseHm());
            bookMarkItem.setRstde(LoginActivity.data.getResponse().getBody().getItems().get(index).getRstde());
            bookMarkItem.setChrgeSe(LoginActivity.data.getResponse().getBody().getItems().get(index).getChrgeSe());
            bookMarkItem.setBcyclUseCharge(LoginActivity.data.getResponse().getBody().getItems().get(index).getBcyclUseCharge());
            bookMarkItem.setAirInjectorYn(LoginActivity.data.getResponse().getBody().getItems().get(index).getAirInjectorYn());
            bookMarkItem.setRepairStandY(LoginActivity.data.getResponse().getBody().getItems().get(index).getRepairStandY());
            bookMarkItem.setPhoneNumber(LoginActivity.data.getResponse().getBody().getItems().get(index).getPhoneNumber());

            mDBHelper.insertBookMark(bookMarkItem.getBcyclLendNm(),bookMarkItem.getBcyclLendSe(),bookMarkItem.getLnmadr(),bookMarkItem.getLnmadr(),
                    bookMarkItem.getLatitude(),bookMarkItem.getLongitude(),bookMarkItem.getOperOpenHm(),bookMarkItem.getOperCloseHm(),bookMarkItem.getRstde(),
                    bookMarkItem.getChrgeSe(),bookMarkItem.getBcyclUseCharge(),bookMarkItem.getAirInjectorYn(),bookMarkItem.getRepairStandY(),bookMarkItem.getPhoneNumber());
            mAdapter.addItem(bookMarkItem);

            index += 1;
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRv_bookMark.setAdapter(mAdapter);
    }

    private void loadRecentDB(ViewGroup container){
        mBMList = mDBHelper.getBookMarkList();
        if (mAdapter == null){
            mAdapter = new BookMarkAdapter(mBMList,container.getContext());
            mRv_bookMark.setHasFixedSize(true);
            mRv_bookMark.setAdapter(mAdapter);
        }
    }

}