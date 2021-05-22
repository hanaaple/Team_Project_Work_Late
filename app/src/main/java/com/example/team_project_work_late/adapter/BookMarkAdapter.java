package com.example.team_project_work_late.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.team_project_work_late.R;
import com.example.team_project_work_late.application.DBHelper;
import com.example.team_project_work_late.model.BookMarkItem;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ViewHolder>{

    private ArrayList<BookMarkItem> mBMList;
    private Context mContext;
    private DBHelper mDBHelper;

    public BookMarkAdapter(ArrayList<BookMarkItem> mBMList, Context mContext) {
        this.mBMList = mBMList;
        this.mContext = mContext;
        mDBHelper = new DBHelper(mContext);
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
        private Button btn_select;

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

                BottomSheetDialog dialog = new BottomSheetDialog(mContext,R.style.TransparentBottomSheetDialogTheme);
                dialog.setContentView(R.layout.dialog_bookmark);
                TextView tv_dialog_bcyclLendNm = dialog.findViewById(R.id.tv_dialog_bcyclLendNm);
                tv_dialog_bcyclLendNm.setText(bookMarkItem.getBcyclLendNm());
                TextView tv_dialog_lnmadr = dialog.findViewById(R.id.tv_dialog_lnmadr);
                tv_dialog_lnmadr.setText(bookMarkItem.getLnmadr());
                TextView tv_dialog_operHm = dialog.findViewById(R.id.tv_dialog_operHm_input);
                tv_dialog_operHm.setText(bookMarkItem.getOperOpenHm()+"~"+bookMarkItem.getOperCloseHm());
                TextView tv_dialog_rstde = dialog.findViewById(R.id.tv_dialog_rstde);
                tv_dialog_rstde.setText(bookMarkItem.getRstde());
                TextView tv_dialog_bcyclUseCharge = dialog.findViewById(R.id.tv_dialog_bcyclUseCharge);
                tv_dialog_bcyclUseCharge.setText(bookMarkItem.getBcyclUseCharge());
                CheckBox cb_dialog_airInjectorYN = dialog.findViewById(R.id.cb_dialog_airInjector);
                if (bookMarkItem.getAirInjectorYn().toUpperCase().equals("Y")){
                    cb_dialog_airInjectorYN.setChecked(true);
                }

                CheckBox cb_dialog_repairStandYN = dialog.findViewById(R.id.cb_dialog_repairStand);
                if (bookMarkItem.getRepairStandY().toUpperCase().equals("Y")){
                    cb_dialog_repairStandYN.setChecked(true);
                }
                TextView tv_dialog_phoneNumber = dialog.findViewById(R.id.tv_dialog_phoneNumber_input);
                tv_dialog_phoneNumber.setText(bookMarkItem.getPhoneNumber());

                MapView mapView = new MapView(mContext);
                ViewGroup mapViewContainer = dialog.findViewById(R.id.dialog_map_view);
                mapViewContainer.addView(mapView);
                mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(bookMarkItem.getLatitude()), Double.parseDouble(bookMarkItem.getLongitude())),true);
                mapView.setZoomLevel(2, true);
                mapView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return true;
                    }
                });
                MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(Double.parseDouble(bookMarkItem.getLatitude()), Double.parseDouble(bookMarkItem.getLongitude()));
                MapPOIItem customMarker = new MapPOIItem();
                customMarker.setItemName(bookMarkItem.getBcyclLendNm());
                customMarker.setTag(0);
                customMarker.setMapPoint(MARKER_POINT);
                customMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                customMarker.setCustomImageAutoscale(false); //기기 해상도에 상관없이 동일한 크키로 표시한다. true는 이미지 크기를 해상도에 맞게 조절함.
                customMarker.setCustomImageAnchor(0.5f, 1.0f); //마커 이미지 기준점
                customMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                mapView.addPOIItem(customMarker);

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
