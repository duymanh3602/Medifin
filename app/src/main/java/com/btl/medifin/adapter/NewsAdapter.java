package com.btl.medifin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.medifin.R;
import com.btl.medifin.activity.News;
import com.btl.medifin.model.Notification;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NoticeViewHolder>{

    private Context context;
    private List<Notification> news;

    public NewsAdapter(Context context, List<Notification> news) {
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public NewsAdapter.NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new NewsAdapter.NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NoticeViewHolder holder, int position) {
        //holder.img.set
        int resourcesCode = News.getInden(news.get(position).getImg());
        holder.img.setImageResource(resourcesCode);
        holder.title.setText(news.get(position).getTitle());
        holder.date.setText(news.get(position).getDate());
    }

    public static class NoticeViewHolder extends RecyclerView.ViewHolder {
        private TextView title, date;
        private ImageView img;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.noticeImg);
            title = itemView.findViewById(R.id.titleNotice);
            date = itemView.findViewById(R.id.dateNotice);
        }
    }

    @Override
    public int getItemCount() {
        return news.size();
    }
}
