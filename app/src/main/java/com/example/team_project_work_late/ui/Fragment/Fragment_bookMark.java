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
import com.example.team_project_work_late.application.BMDBHelper;
import com.example.team_project_work_late.application.DpstryDBHelper;
import com.example.team_project_work_late.application.LendDBHelper;
import com.example.team_project_work_late.model.BcyclDpstryData_responseBody_items;
import com.example.team_project_work_late.model.BcyclLendData_responseBody_items;
import com.example.team_project_work_late.model.BookMarkItem;

import java.util.ArrayList;
import java.util.List;

public class Fragment_bookMark extends Fragment {

    private RecyclerView mRv_bookMark;
    private ArrayList<BookMarkItem> mBMList;
    private BookMarkAdapter mAdapter;
    private BMDBHelper mBMDBHelper;
    private List<BookMarkItem> addItem;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_mark, container, false);
        mBMDBHelper = new BMDBHelper(getContext());
        mBMList = new ArrayList();
        mRv_bookMark = view.findViewById(R.id.rv_bookMark);
        addItem = new ArrayList<>();

        Bundle bundle = getArguments();
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
        mBMList = mBMDBHelper.getBookMarkList();
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
                mBMDBHelper.insertBookMark(bookMarkItem.getBcyclLendNm(),bookMarkItem.getBcyclLendSe(),bookMarkItem.getLnmadr(),bookMarkItem.getLnmadr(),
                        bookMarkItem.getLatitude(),bookMarkItem.getLongitude(),bookMarkItem.getOperOpenHm(),bookMarkItem.getOperCloseHm(),bookMarkItem.getRstde(),
                        bookMarkItem.getChrgeSe(),bookMarkItem.getBcyclUseCharge(),bookMarkItem.getAirInjectorYn(),bookMarkItem.getRepairStandY(),bookMarkItem.getPhoneNumber());
                mRv_bookMark.smoothScrollToPosition(0);
            }
        }
    }

}