package com.btl.medifin.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.medifin.activity.Medicine;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
    public MedicineAdapter(Medicine medicine, List<com.btl.medifin.model.Medicine> medicineList) {
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
