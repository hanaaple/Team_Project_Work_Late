package com.example.team_project_work_late.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.application.DBHelper;
import com.example.team_project_work_late.model.BookMarkItem;

import java.util.ArrayList;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ViewHolder>{

    private ArrayList<BookMarkItem> mBMList;
    private Context mContext;
    private DBHelper mDBHelper;

    public BookMarkAdapter(ArrayList<BookMarkItem> mBMList, Context mContext) {
        this.mBMList = mBMList;
        this.mContext = mContext;
        this.mDBHelper = new DBHelper(mContext);
    }

    @NonNull
    @Override
    public BookMarkAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new ViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(@NonNull BookMarkAdapter.ViewHolder holder, int position) {
        holder.tv_bcyclLendNm.setText(mBMList.get(position).getBcyclLendNm());
        holder.tv_rdnmadr.setText(mBMList.get(position).getRdnmadr());
        holder.tv_bcyclLendSe.setText(mBMList.get(position).getBcyclLendSe());
        holder.tv_operHm.setText(mBMList.get(position).getOperOpenHm()+"~"+mBMList.get(position).getOperCloseHm());
        holder.tv_rstde.setText(mBMList.get(position).getRstde());
    }

    @Override
    public int getItemCount() {
        return mBMList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_bcyclLendNm;
        private TextView tv_rdnmadr;
        private TextView tv_bcyclLendSe;
        private TextView tv_operHm;
        private TextView tv_rstde;
        private ImageButton btn_select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_bcyclLendNm = itemView.findViewById(R.id.tv_bcyclLendNm);
            tv_rdnmadr = itemView.findViewById(R.id.tv_rdnmadr);
            tv_bcyclLendSe = itemView.findViewById(R.id.tv_bcyclLendSe);
            tv_operHm = itemView.findViewById(R.id.tv_operHm);
            tv_rstde = itemView.findViewById(R.id.tv_rstde);
            btn_select = itemView.findViewById(R.id.btn_select);

            itemView.setOnClickListener(v -> {
                int curPos = getAdapterPosition();
                BookMarkItem bookMarkItem = mBMList.get(curPos);

                Dialog dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog);
                dialog.setContentView(R.layout.dialog_bookmark);
                TextView tv_dialog_bcyclLendNm = dialog.findViewById(R.id.tv_dialog_bcyclLendNm);
                tv_dialog_bcyclLendNm.setText(bookMarkItem.getBcyclLendNm());
                TextView tv_dialog_lnmadr = dialog.findViewById(R.id.tv_dialog_lnmadr);
                tv_dialog_lnmadr.setText(bookMarkItem.getLnmadr());
                TextView tv_dialog_operHm = dialog.findViewById(R.id.tv_dialog_operHm);
                tv_dialog_operHm.setText(bookMarkItem.getOperOpenHm()+"~"+bookMarkItem.getOperCloseHm());
                TextView tv_dialog_rstde = dialog.findViewById(R.id.tv_dialog_rstde);
                tv_dialog_rstde.setText(bookMarkItem.getRstde());
                TextView tv_dialog_chrgeSe = dialog.findViewById(R.id.tv_dialog_chrgeSe);
                if (bookMarkItem.getRstde().compareTo(bookMarkItem.getChrgeSe()) != 0){
                    tv_dialog_chrgeSe.setText(bookMarkItem.getChrgeSe());
                }else{
                    tv_dialog_chrgeSe.setText("");
                }
                TextView tv_dialog_bcyclUseCharge = dialog.findViewById(R.id.tv_dialog_bcyclUseCharge);
                tv_dialog_bcyclUseCharge.setText(bookMarkItem.getBcyclUseCharge());
                TextView tv_dialog_airInjectorYN = dialog.findViewById(R.id.tv_dialog_airInjectorYN);
                tv_dialog_airInjectorYN.setText(bookMarkItem.getAirInjectorYn());
                TextView tv_dialog_repairStandYN = dialog.findViewById(R.id.tv_dialog_repairStandYN);
                tv_dialog_repairStandYN.setText(bookMarkItem.getRepairStandY());
                TextView tv_dialog_phoneNumber = dialog.findViewById(R.id.tv_dialog_phoneNumber);
                tv_dialog_phoneNumber.setText(bookMarkItem.getPhoneNumber());

                dialog.show();
            });

            btn_select.setOnClickListener(v->{
                String bcyclLendNm = tv_bcyclLendNm.getText().toString();
                mDBHelper.deleteRental(bcyclLendNm);
                mBMList.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            });
        }
    }

    public void addItem(BookMarkItem bookMarkItem){
        mBMList.add(0,bookMarkItem);
        notifyItemInserted(0);
    }
}
