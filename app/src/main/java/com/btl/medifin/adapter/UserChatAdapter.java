package com.btl.medifin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.btl.medifin.activity.Message;
import com.btl.medifin.R;
import com.btl.medifin.model.Users;

import java.util.List;

public class UserChatAdapter extends RecyclerView.Adapter<UserChatAdapter.UserChatViewHolder>{
    private Context context;
    private List<Users> usersList;

    public UserChatAdapter(Context context, List<Users> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public UserChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_chat, parent, false);
        return new UserChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserChatViewHolder holder, int position) {
        holder.tvId.setText("ID: " + usersList.get(position).getUserName());
        holder.tvTen.setText(usersList.get(position).getFullName());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, Message.class);
            intent.putExtra("RECEIVER", usersList.get(position).getUserName());
            intent.putExtra("RECEIVERNAME", usersList.get(position).getFullName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class UserChatViewHolder extends RecyclerView.ViewHolder {
        private TextView tvId, tvTen;

        public UserChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tvIdTaiKhoan_itemUser);
            tvTen = itemView.findViewById(R.id.tvTenTaiKhoan_itemUser);
        }
    }
}
