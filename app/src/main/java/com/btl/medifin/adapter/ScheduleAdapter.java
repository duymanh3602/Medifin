package com.btl.medifin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.medifin.R;
import com.btl.medifin.model.PhieuKham;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.LichKhamViewHolder>{
    private List<PhieuKham> mLists;
    private Context context;

    public ScheduleAdapter(Context context, List<PhieuKham> mLists) {
        this.mLists = mLists;
        this.context = context;
    }

    @NonNull
    @Override
    public LichKhamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phieu_kham, parent, false);
        return new LichKhamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichKhamViewHolder holder, int position) {
        holder.tvMaPhieu.setText(mLists.get(position).getId());
        holder.tvTenBn.setText(mLists.get(position).getTenBn());
        holder.tvTrangThai.setText(mLists.get(position).getStatus());
        holder.tvThoiGian.setText(mLists.get(position).getTime() + " ngày " + mLists.get(position).getDate());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    public static class LichKhamViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMaPhieu;
        private TextView tvTenBn;
        private TextView tvTrangThai;
        private TextView tvThoiGian;

        public LichKhamViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaPhieu = itemView.findViewById(R.id.tvMaPhieuKham);
            tvTenBn = itemView.findViewById(R.id.tvTenBn_PhieuKham);
            tvTrangThai = itemView.findViewById(R.id.tvTrangThai_PhieuKham);
            tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
        }
    }
}
