package com.example.team_project_work_late.ui.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.adapter.BookMarkAdapter;
import com.example.team_project_work_late.application.DBHelper;
import com.example.team_project_work_late.model.BcyclDpstryData;
import com.example.team_project_work_late.model.BcyclDpstryData_responseBody_items;
import com.example.team_project_work_late.model.BcyclLendData;
import com.example.team_project_work_late.model.BcyclLendData_responseBody_items;
import com.example.team_project_work_late.model.BookMarkItem;
import com.example.team_project_work_late.ui.Activity.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Fragment_bookMark extends Fragment {

    private List<BcyclLendData_responseBody_items> bcyclLendData;     // 대여소 파싱용 데아터
    private List<BcyclDpstryData_responseBody_items> bcyclDpstryData; // 보관소 파싱용 데이터
    private RecyclerView mRv_bookMark;
    private ArrayList<BookMarkItem> mBMList;
    private BookMarkAdapter mAdapter;
    private DBHelper mDBHelper;
    private List<BookMarkItem> addItem;

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
        addItem = new ArrayList<>();

        Bundle bundle = getArguments();
        bcyclLendData = (List<BcyclLendData_responseBody_items>) bundle.getSerializable("bcyclLendData");
        bcyclDpstryData = (List<BcyclDpstryData_responseBody_items>) bundle.getSerializable("bcyclDpstryData");
        addItem = (List<BookMarkItem>) bundle.getSerializable("addItem");
        loadRecentDB(container);
        addDB();

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

    private void addDB(){
        if (!addItem.isEmpty()) {
            for (BookMarkItem bookMarkItem : addItem){
                mAdapter.addItem(bookMarkItem);
                mDBHelper.insertBookMark(bookMarkItem.getBcyclLendNm(),bookMarkItem.getBcyclLendSe(),bookMarkItem.getLnmadr(),bookMarkItem.getLnmadr(),
                        bookMarkItem.getLatitude(),bookMarkItem.getLongitude(),bookMarkItem.getOperOpenHm(),bookMarkItem.getOperCloseHm(),bookMarkItem.getRstde(),
                        bookMarkItem.getChrgeSe(),bookMarkItem.getBcyclUseCharge(),bookMarkItem.getAirInjectorYn(),bookMarkItem.getRepairStandY(),bookMarkItem.getPhoneNumber());
                mRv_bookMark.smoothScrollToPosition(0);
            }
        }
    }

}