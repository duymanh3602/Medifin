package com.btl.medifin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.medifin.activity.Bill;
import com.btl.medifin.R;
import com.btl.medifin.model.MedBill;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.LichSuViewHolder>{
    private Context context;
    private List<MedBill> medBillList;

    public HistoryAdapter(Context context, List<MedBill> medBillList) {
        this.context = context;
        this.medBillList = medBillList;
    }

    @NonNull
    @Override
    public LichSuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lich_su_kham, parent, false);
        return new LichSuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichSuViewHolder holder, int position) {
        MedBill obj = medBillList.get(position);
        holder.tvTen.setText(obj.getTenBs());
        holder.tvTime.setText(obj.getDate() + " lúc " + obj.getTime());
        holder.tvStatus.setText(obj.getStatus());
        holder.id.setText(obj.getId());
        holder.name.setText(obj.getTenBn());
        if(obj.getStatus().equalsIgnoreCase("Đang chờ")){
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        if(obj.getStatus().equalsIgnoreCase("Đang khám")){
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.yellow));
        }

        holder.itemView.setOnClickListener(v -> {
            if(obj.getStatus().equalsIgnoreCase("Hoàn Thành")){
                Intent intent = new Intent(context, Bill.class);
                intent.putExtra("IDPK", obj.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return medBillList.size();
    }

    public static class LichSuViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTen, tvTime, tvStatus, name, id;
        public LichSuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvStatus_lichSuKham);
            tvTime = itemView.findViewById(R.id.tv_time_lichSuKham);
            tvTen = itemView.findViewById(R.id.tv_ten_lichSuKham);
            id = itemView.findViewById(R.id.id_pk);
            name = itemView.findViewById(R.id.bn_pk);
        }
    }
}
