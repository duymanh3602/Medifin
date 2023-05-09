package com.btl.medifin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.btl.medifin.R;
import com.btl.medifin.model.Hospital;

import java.util.List;

public class HospitalAdapter extends BaseAdapter {

    private Context context;
    private List<Hospital> hospitals;

    public HospitalAdapter(Context context, List<Hospital> hospitals) {
        this.context = context;
        this.hospitals = hospitals;
    }

    @Override
    public int getCount() {
        return hospitals.size();
    }

    @Override
    public Object getItem(int i) {
        return hospitals.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        HospitalAdapter.HospitalViewHolder viewHolder;
        if(view == null){
            viewHolder = new HospitalAdapter.HospitalViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_setting, viewGroup, false);
            viewHolder.image = view.findViewById(R.id.image_setting);
            viewHolder.title = view.findViewById(R.id.title_setting);
            view.setTag(viewHolder);
        } else {
            viewHolder = (HospitalAdapter.HospitalViewHolder) view.getTag();
        }
        Hospital obj = hospitals.get(i);
        viewHolder.image.setImageResource(obj.getImg());
        viewHolder.title.setText(obj.getName());
        return view;
    }

    private static class HospitalViewHolder{
        public ImageView image;
        public TextView title;
    }

}
