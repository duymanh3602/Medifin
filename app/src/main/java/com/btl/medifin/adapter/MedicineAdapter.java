package com.btl.medifin.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.medifin.R;
import com.btl.medifin.model.Medicine;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>{
    private Context context;
    private List<Medicine> medicineList;

    public MedicineAdapter(Context context, List<Medicine> medicineList) {
        this.context = context;
        this.medicineList = medicineList;
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicine, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        //holder.img.setImageResource();
        //holder.img.setImageURI(Uri.parse("https://raw.githubusercontent.com/duymanh3602/Medifin/master/app/src/main/res/assets/img/" + medicineList.get(position).getImg()));
        //holder.img.setImageBitmap(getBitmapFromURL("https://raw.githubusercontent.com/duymanh3602/Medifin/master/app/src/main/res/assets/img/" + medicineList.get(position).getImg()));
        holder.name.setText("Thuốc: " + medicineList.get(position).getName());
        holder.dose.setText("Liều: " + medicineList.get(position).getDose());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, com.btl.medifin.activity.Medicine.class);
            intent.putExtra("ID", medicineList.get(position).getName().toString());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return medicineList.size();
    }

    public static class MedicineViewHolder extends RecyclerView.ViewHolder {
        private TextView name, dose;
        private ImageView img;

        public MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgMedicine);
            name = itemView.findViewById(R.id.name);
            dose = itemView.findViewById(R.id.dose);
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);

            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            Log.d("Medicine", e.toString());
            return null;
        }
    }
}
