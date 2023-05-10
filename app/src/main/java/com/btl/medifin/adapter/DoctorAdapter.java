package com.btl.medifin.adapter;

import static java.lang.Math.round;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.medifin.activity.Booking;
import com.btl.medifin.R;
import com.btl.medifin.model.MedBill;
import com.btl.medifin.model.Medicine;
import com.btl.medifin.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DatLichViewHolder>{
    private List<MedBill> medBills = new ArrayList<>();
    private List<Users> mList;
    private DatabaseReference databaseReference;
    private double rate = 0;

    public DoctorAdapter(List<Users> mList) {
        this.mList = mList;
    }

    @NonNull
    @Override
    public DatLichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bac_si, parent, false);
        return new DatLichViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DatLichViewHolder holder, int position) {


            holder.name.setText("Bác sĩ: " + mList.get(position).getFullName());
            holder.specialized.setText("Chuyên khoa: " + mList.get(position).getSpecialized());
            getRateBsi(mList.get(position).getUserName(), holder);
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), Booking.class);
                intent.putExtra("IDBS", mList.get(position).getUserName());
                intent.putExtra("TENBS", mList.get(position).getFullName());
                v.getContext().startActivity(intent);
            });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class DatLichViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView specialized;
        private TextView rating;
        private ProgressBar ratebar;

        public DatLichViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvNameBs_DatLich);
            specialized = itemView.findViewById(R.id.tvSpecializedBs_DatLich);
            rating = itemView.findViewById(R.id.doc_rating);
            ratebar = itemView.findViewById(R.id.rate_bar);

        }
    }

    private void getRateBsi(String idBs, @NonNull DatLichViewHolder holder) {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("History");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MedBill medBill = new MedBill();
                medBills.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    medBill = ds.getValue(MedBill.class);
                    if (medBill.getIdBs().equals(idBs)) {
                        if (medBill.getStatus().equals("Hoàn thành")) {
                            medBills.add(medBill);
                        }
                    }
                }
                holder.rating.setText("(" + String.format("%.1f", calRate(medBills)) + "/5.0)");
                holder.ratebar.setProgress((int)calRate(medBills) * 20);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private double calRate(List<MedBill> medBills) {

        double rate = 0;
        for (int i = 0; i < medBills.size(); i++) {
            rate += medBills.get(i).getRate();
        }

        if (medBills.size() == 0) {
            return 5;
        } else {
            return (rate / (double) (medBills.size()));
        }
    }


}
